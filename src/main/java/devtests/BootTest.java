/**
 * 
 */
package devtests;

import org.topicquests.os.asr.Sentence2AgentEnvironment;

/**
 * @author jackpark
 *
 */
public class BootTest {
	private Sentence2AgentEnvironment environment;

	/**
	 * 
	 */
	public BootTest() {
		environment = new Sentence2AgentEnvironment();
		System.out.println("A "+environment.getProperties());
		
		environment.shutDown();
		System.exit(0);
	}

}
