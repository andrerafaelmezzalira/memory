package memory.prevayler;

import java.io.Serializable;


public interface Transaction extends Serializable {

	public void executeOn(
			StringBuilder prevalentSystem) throws Exception;

}
