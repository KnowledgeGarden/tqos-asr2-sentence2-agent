/**
 * 
 */
package devtests;

import org.topicquests.os.asr.Sentence2AgentEnvironment;
import org.topicquests.os.asr.linkgrammar.interpreter.LinkGrammarInterpreter;

/**
 * @author jackpark
 *
 */
public class TestRoot {
	protected Sentence2AgentEnvironment environment;
	protected LinkGrammarInterpreter interpreter;

	/**
	 * 
	 */
	public TestRoot() {
		environment = new Sentence2AgentEnvironment();
		interpreter = environment.getLGEnvironment().getInterpreter();

	}

}
