package prevayler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PrevaylerData implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Serializable> data = new ArrayList<>();

	public List<Serializable> getData() {
		return data;
	}
}