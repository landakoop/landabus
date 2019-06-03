package landakoop.landabus.beans;

public class Kontsulta {
	Long chatId;
	Geltokia irteera;
	Geltokia helmuga;
	int irteeraOrdua;
	int helmugaOrdua;
	
	public Kontsulta() {}
	
	public Kontsulta(Geltokia irteera, Geltokia helmuga, String irteeraOrdua, String helmugaOrdua, Long chatId) {
		this.irteera = irteera;
		this.helmuga = helmuga;
		this.irteeraOrdua = strOrduaToInt(irteeraOrdua);
		this.helmugaOrdua = strOrduaToInt(helmugaOrdua);
	}
	
	public Geltokia getIrteera() {
		return irteera;
	}
	public void setIrteera(Geltokia irteera) {
		this.irteera = irteera;
	}
	public Geltokia getHelmuga() {
		return helmuga;
	}
	public void setHelmuga(Geltokia helmuga) {
		this.helmuga = helmuga;
	}
	public int getIrteeraOrdua() {
		return irteeraOrdua;
	}
	public void setIrteeraOrdua(String irteeraOrdua) {
		this.irteeraOrdua = strOrduaToInt(irteeraOrdua);
	}
	public int getHelmugaOrdua() {
		return helmugaOrdua;
	}
	public void setHelmugaOrdua(String helmugaOrdua) {
		this.helmugaOrdua = strOrduaToInt(helmugaOrdua);
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
		kontsulta = "Irteera: "+getIrteera().getIzena()+" ------> "
				+"Irtetzeko ordu minimoa: "+getIrteeraOrdua()+"\n"
				+"Helmuga: "+getHelmuga().getIzena()+" ------> "
				+"Heltzeko ordu maximoa: "+getHelmugaOrdua();
		return kontsulta;
	}
	
	public int strOrduaToInt(String orduaStr) {
		int orduaMinututan;
		int ordua, minutuak;
		String str[];
		str = orduaStr.split(":");
		
		ordua = Integer.parseUnsignedInt(str[0]);
		minutuak = Integer.parseUnsignedInt(str[1]);
		orduaMinututan = (ordua*60) + minutuak;
		
		return orduaMinututan;
	}
	
}
