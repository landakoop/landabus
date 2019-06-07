package landakoop.landabus.mainapp.model;

import java.util.List;

public interface BilaketaEmaitza {
	/*List<Integer> linea;
	List<Integer> eskaerak;
	Integer irteeraOrdua;*/
	
	List<Long> getLinea();
	List<Long> getEskaerak();
	Integer getIrteeraOrdua();
	
	/*public List<Integer> getLinea() {
		return linea;
	}
	public void setLinea(List<Integer> linea) {
		this.linea = linea;
	}
	public List<Integer> getEskaerak() {
		return eskaerak;
	}
	public void setEskaerak(List<Integer> eskaerak) {
		this.eskaerak = eskaerak;
	}
	public Integer getIrteeraOrdua() {
		return irteeraOrdua;
	}
	public void setIrteeraOrdua(Integer irteeraOrdua) {
		this.irteeraOrdua = irteeraOrdua;
	}
	*/
	
}
