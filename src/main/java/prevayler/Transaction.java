package prevayler;

import java.io.Serializable;

public interface Transaction extends Serializable {

	public void executeOn(PrevaylerData prevalentSystem) throws Exception;

}
