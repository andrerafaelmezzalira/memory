package prevayler;

import java.io.IOException;
import java.io.Serializable;

public abstract class PrevaylerService  {

	private static Prevayler prevayler;

	protected PrevaylerService() throws ClassNotFoundException, IOException, Exception {
		prevayler = new Prevayler();
	}

	protected void add(Serializable serializable) throws Exception {
		prevayler.execute(new AddRepository(serializable));
	}

	protected PrevaylerData get() throws Exception {
		return prevayler.execute(new PrevaylerRepository());
	}
}
