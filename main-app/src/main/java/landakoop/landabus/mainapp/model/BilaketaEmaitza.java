package landakoop.landabus.mainapp.model;

import java.util.List;

public class BilaketaEmaitza {
	List<Long> linea;
	List<Long> eskaerak;
	Integer irteeraOrdua;
	
	/*List<Long> getLinea();
	List<Long> getEskaerak();
	Integer getIrteeraOrdua();*/
	
	public List<Long> getLinea() {
		return linea;
	}
	public void setLinea(List<Long> linea) {
		this.linea = linea;
	}
	public List<Long> getEskaerak() {
		return eskaerak;
	}
	public void setEskaerak(List<Long> eskaerak) {
		this.eskaerak = eskaerak;
	}
	public Integer getIrteeraOrdua() {
		return irteeraOrdua;
	}
	public void setIrteeraOrdua(Integer irteeraOrdua) {
		this.irteeraOrdua = irteeraOrdua;
	}
	
	
}
