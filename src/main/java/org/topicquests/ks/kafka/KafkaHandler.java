/*
 * Copyright 2019 TopicQuests Foundation
 *  This source code is available under the terms of the Affero General Public License v3.
 *  Please see LICENSE.txt for full license terms, including the availability of proprietary exceptions.
 */
package org.topicquests.ks.kafka;

import org.topicquests.backside.kafka.consumer.StringConsumer;
import org.topicquests.backside.kafka.consumer.api.IMessageConsumerListener;
import org.topicquests.ks.kafka.KafkaProducer;
import org.topicquests.os.asr.Sentence2AgentEnvironment;

import net.minidev.json.JSONObject;

/**
 * @author jackpark
 * 
 */
public class KafkaHandler {
	private Sentence2AgentEnvironment environment;
	private StringConsumer consumer;
	private KafkaProducer producer;
	private final boolean isRewind;
	private final int pollSeconds = 2;
	private final String
		CONSUMER_TOPIC,
		PRODUCER_TOPIC,
		PRODUCER_KEY,
		AGENT_GROUP = "foo"; //"BiomedSentenceAgent";

	/**
	 * 
	 */
	public KafkaHandler(Sentence2AgentEnvironment env, IMessageConsumerListener listener) {
		environment = env;
		String rw = environment.getStringProperty("ConsumerRewind");
		isRewind = rw.equalsIgnoreCase("T");
		CONSUMER_TOPIC = (String)environment.getKafkaTopicProperties().get("SpacyOutput");
		PRODUCER_TOPIC = (String)environment.getKafkaTopicProperties().get("SentenceOutput");
		consumer = new StringConsumer(environment, AGENT_GROUP,
					CONSUMER_TOPIC, listener, isRewind, pollSeconds);
		producer = new KafkaProducer(environment, AGENT_GROUP);
		PRODUCER_KEY = AGENT_GROUP;
	}
	
	public void shipEvent(JSONObject event) {
		producer.sendMessage(PRODUCER_TOPIC, event.toJSONString(), PRODUCER_KEY, new Integer(0));
	}
	
	
	public void shutDown() {
		consumer.close();
	}

}
