/**
 * 
 */
package org.topicquests.os.asr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.topicquests.backside.kafka.consumer.api.IMessageConsumerListener;
import org.topicquests.ks.kafka.KafkaHandler;
import org.topicquests.ks.kafka.KafkaProducer;
import org.topicquests.os.asr.linkgrammar.LinkGrammarAgent;
import org.topicquests.os.asr.linkgrammar.interpreter.LinkGrammarInterpreter;
import org.topicquests.support.api.IResult;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

/**
 * @author jackpark
 *
 */
public class SentenceProcessor implements IMessageConsumerListener {
	private Sentence2AgentEnvironment environment;
	private LinkGrammarAgent agent;
	private KafkaHandler handler;

	/**
	 * 
	 */
	public SentenceProcessor(Sentence2AgentEnvironment env) {
		environment =  env;
		agent = environment.getLGEnvironment().getAgent();

	}
	
	protected void setKafkaHandler(KafkaHandler h) {
		handler = h;
	}

	/**
	 * <p>Just sit here and wait for a {@code record}</p>
	 * <p>When done, fire up the {@code KafkaProducer} by way of {@code KafkaHandler}
	 * 	 and ship the results.</p>
	 * @param record
	 * @return truth in terms of whether the record has been processed properly
	 */
	@Override
	public boolean acceptRecord(ConsumerRecord record) {
		String json = (String)record.value();
		environment.logDebug("SentenceProcessor.acceptRecord "+json);
		boolean result = processRecord(json);

		return result;	
	}
	
	/**
	 * Public for testing
	 * @param json
	 * @return
	 */
	public boolean processRecord(String json) {
		boolean result = true; // default
		try {
			JSONParser p = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
			JSONObject spacyParagraph = (JSONObject)p.parse(json);
			List<JSONObject> sentenceObjects = (List<JSONObject>)spacyParagraph.get("sentenceObjects");
			if (sentenceObjects != null && !sentenceObjects.isEmpty()) {
				List<JSONObject> resultObjects = new ArrayList<JSONObject>();
				JSONObject sentenceObject;
				Iterator<JSONObject> itr = sentenceObjects.iterator();
				while (itr.hasNext()) {
					//For each sentenceObject, pluck sentence, run LinkGrammar, merge
					sentenceObject = itr.next();
					processSentenceObject(sentenceObject, resultObjects);
				}
			}
			
		} catch (Exception e) {
			environment.logError(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}
	
	private void processSentenceObject(JSONObject sentenceObject, List<JSONObject> resultObjects) {
		String theSentence = sentenceObject.getAsString("text");
		environment.logDebug("SentenceProcessor.processSentenceObject-1 "+theSentence);
		IResult r = agent.processSentence(theSentence);
		environment.logDebug(r.getErrorString()+"/n"+r.getResultObject());
	}

}
