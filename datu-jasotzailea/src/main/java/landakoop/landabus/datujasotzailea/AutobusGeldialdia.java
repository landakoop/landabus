package landakoop.landabus.datujasotzailea;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AutobusGeldialdia{
	private int erabiltzailea;
	private int ibilbidea;
	private int geltokia;
	private long noiz;
	private String ekintza;
	
	public AutobusGeldialdia(int erabiltzailea, int ibilbidea, int geltokia, long noiz, boolean ekintza) {
		this.erabiltzailea = erabiltzailea;
		this.ibilbidea = ibilbidea;
		this.geltokia = geltokia;
		this.noiz = noiz;
		this.ekintza = ekintza?"igo":"jaitsi";
	}

	public int getErabiltzailea() {
		return erabiltzailea;
	}
	
	public void setErabiltzailea(int erabiltzailea) {
		this.erabiltzailea = erabiltzailea;
	}
	
	public int getIbilbidea() {
		return ibilbidea;
	}
	
	public void setIbilbidea(int ibilbidea) {
		this.ibilbidea = ibilbidea;
	}
	
	public int getGeltokia() {
		return geltokia;
	}
	
	public void setGeldialdia(int geltokia) {
		this.geltokia = geltokia;
	}
	
	public long getNoiz() {
		return noiz;
	}
	
	public void setNoiz(long noiz) {
		this.noiz = noiz;
	}

	public String getEkintza() {
		return ekintza;
	}

	public void setEkintza(String ekintza) {
		this.ekintza = ekintza;
	}

	@Override
	public String toString() {		
		return erabiltzailea + "$" + ibilbidea + "$" + geltokia + "$" + noiz + "$" + ekintza;
	}
}
