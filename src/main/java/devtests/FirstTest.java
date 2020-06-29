/**
 * 
 */
package devtests;

import org.topicquests.support.util.TextFileHandler;

/**
 * @author jackpark
 *
 */
public class FirstTest extends TestRoot {
	private final String PATH = "NB-1.json";
	
	/**
	 * 
	 */
	public FirstTest() {
		super();
		TextFileHandler h = new TextFileHandler();
		String json = h.readFile(PATH);
		boolean foo = processor.processRecord(json);
		System.out.println("A "+foo);
		
		environment.shutDown();
		System.exit(0);
	}

}
