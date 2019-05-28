package landakoop.beans;

public class Kontsulta {
	Long chatId;
	String irteera;
	String helmuga;
	String irtOrduaMin;
	String helOrduaMax;
	
	public Kontsulta() {}
	
	public Kontsulta(String irteera, String helmuga, String irtOrduaMin, String helOrduaMax, Long chatId) {
		this.irteera = irteera;
		this.helmuga = helmuga;
		this.irtOrduaMin = irtOrduaMin;
		this.helOrduaMax = helOrduaMax;
	}
	
	public String getIrteera() {
		return irteera;
	}
	public void setIrteera(String irteera) {
		this.irteera = irteera;
	}
	public String getHelmuga() {
		return helmuga;
	}
	public void setHelmuga(String helmuga) {
		this.helmuga = helmuga;
	}
	public String getIrteeraOrduaMin() {
		return irtOrduaMin;
	}
	public void setIrteeraOrduaMin(String irtOrduaMin) {
		this.irtOrduaMin = irtOrduaMin;
	}
	public String getHelmugaOrduaMax() {
		return helOrduaMax;
	}
	public void setHelmugaOrduaMax(String helOrduaMax) {
		this.helOrduaMax = helOrduaMax;
	}
	public Long getChatId() {
		return chatId;
	}
	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}

	@Override
	public String toString() {
		String kontsulta;
		kontsulta = "Irteera: "+getIrteera()+" ------> "
				+"Irtetzeko ordu minimoa: "+getIrteeraOrduaMin()+"\n"
				+"Helmuga: "+getHelmuga()+" ------> "
				+"Heltzeko ordu maximoa: "+getHelmugaOrduaMax();
		return kontsulta;
	}
	
}
