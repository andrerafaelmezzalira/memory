package memory.prevayler;

import java.io.Serializable;


public interface TransactionWithQuery extends Serializable {

	public StringBuilder executeAndQuery(
			final StringBuilder prevalentSystem)
			throws Exception;

}
