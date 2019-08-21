package prevayler;

import java.io.Serializable;

public class AddRepository implements Transaction {

	private static final long serialVersionUID = 1L;

	private Serializable serializable;
	
	public AddRepository(Serializable serializable) {
		this.serializable = serializable;
	}
	
	@Override
	public void executeOn(PrevaylerData prevalentSystem) throws Exception {
		prevalentSystem.getData().add(serializable);
	}
}