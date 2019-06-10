package landakoop.landabus.mainapp.model;

import java.util.List;
import java.util.Map;

public class Emaitza {
	List<Long> listId;
	Map<String,String> linea;
	
	public List<Long> getListId() {
		return listId;
	}
	public void setListId(List<Long> listId) {
		this.listId = listId;
	}
	public Map<String, String> getLinea() {
		return linea;
	}
	public void setLinea(Map<String, String> linea) {
		this.linea = linea;
	}
}
