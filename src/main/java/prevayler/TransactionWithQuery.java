package prevayler;

import java.io.Serializable;


public interface TransactionWithQuery extends Serializable {

	public PrevaylerData executeAndQuery(
			final PrevaylerData prevalentSystem)
			throws Exception;

}
