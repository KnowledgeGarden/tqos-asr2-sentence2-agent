/**
 * 
 */
package org.topicquests.os.asr;

import java.util.Map;

import org.topicquests.ks.kafka.KafkaHandler;
import org.topicquests.os.asr.linkgrammar.LinkGrammarClientEnvironment;
import org.topicquests.os.asr.sg.SGEnvironment;
import org.topicquests.support.RootEnvironment;
import org.topicquests.support.config.Configurator;

/**
 * @author jackpark
 *
 */
public class Sentence2AgentEnvironment extends RootEnvironment {
	private LinkGrammarClientEnvironment lgEnvironment;
	private SGEnvironment sgEnvironment;

	private Map<String,Object>kafkaProps;
	private SentenceProcessor processor;
	private KafkaHandler consumer;
	private boolean isShutDown = false;

	/**
	 * 
	 */
	public Sentence2AgentEnvironment() {
		super("asr-props.xml", "logger.properties");
		lgEnvironment = new LinkGrammarClientEnvironment();
		kafkaProps = Configurator.getProperties("kafka-topics.xml");
		sgEnvironment = new SGEnvironment();
		processor = new SentenceProcessor(this);
		consumer = new KafkaHandler(this, processor);
		processor.setKafkaHandler(consumer);
		isShutDown = false;

		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			
			@Override
			public void run() {
				shutDown();
			}
		});

	}
	
	public SentenceProcessor getSentenceProcessor() {
		return processor;
	}

	public LinkGrammarClientEnvironment getLGEnvironment() {
		return lgEnvironment;
	}
	
	public Map<String, Object> getKafkaTopicProperties() {
		return kafkaProps;
	}

	
	@Override
	public void shutDown() {
		System.out.println("Sentence2AgentEnvironment shutDown "+isShutDown);
		if (!isShutDown) {
			lgEnvironment.shutDown();
			sgEnvironment.shutDown();
			isShutDown = true;
		}

	}

}
