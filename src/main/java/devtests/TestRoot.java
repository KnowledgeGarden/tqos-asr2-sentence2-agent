/**
 * 
 */
package devtests;

import org.topicquests.os.asr.Sentence2AgentEnvironment;
import org.topicquests.os.asr.SentenceProcessor;
import org.topicquests.os.asr.linkgrammar.LinkGrammarAgent;
import org.topicquests.os.asr.linkgrammar.interpreter.LinkGrammarInterpreter;

/**
 * @author jackpark
 *
 */
public class TestRoot {
	protected Sentence2AgentEnvironment environment;
	protected LinkGrammarAgent agent;
	protected SentenceProcessor processor;

	/**
	 * 
	 */
	public TestRoot() {
		environment = new Sentence2AgentEnvironment();
		agent = environment.getLGEnvironment().getAgent();
		processor = environment.getSentenceProcessor();

	}

}
