/**
 * 
 */
package org.topicquests.os.asr;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.topicquests.backside.kafka.consumer.api.IMessageConsumerListener;
import org.topicquests.ks.kafka.KafkaHandler;
import org.topicquests.ks.kafka.KafkaProducer;
import org.topicquests.os.asr.linkgrammar.interpreter.LinkGrammarInterpreter;

/**
 * @author jackpark
 *
 */
public class SentenceProcessor implements IMessageConsumerListener {
	private Sentence2AgentEnvironment environment;
	private LinkGrammarInterpreter interpreter;
	private KafkaHandler handler;

	/**
	 * 
	 */
	public SentenceProcessor(Sentence2AgentEnvironment env) {
		environment =  env;
		interpreter = environment.getLGEnvironment().getInterpreter();

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
		// TODO Auto-generated method stub
		return false;
	}

}
