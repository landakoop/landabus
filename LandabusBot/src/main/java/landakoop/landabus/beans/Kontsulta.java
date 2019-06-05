package landakoop.landabus.beans;

import java.util.Date;

import org.apache.commons.collections4.BidiMap;

import landakoop.landabus.landabusBot.LandabusBot;

public class Kontsulta {
	int stage;
	Long chatId;
	Geltokia irteera;
	Geltokia helmuga;
	int irteeraOrdua;
	int helmugaOrdua;
	Date data;
	
	public Kontsulta() {}
	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public int getStage() {
		return stage;
	}
	public void setStage(int stage) {
		this.stage = stage;
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
	public void setIrteeraOrdua(int irteeraOrdua) {
		this.irteeraOrdua = irteeraOrdua;
	}
	public int getHelmugaOrdua() {
		return helmugaOrdua;
	}
	public void setHelmugaOrdua(int helmugaOrdua) {
		this.helmugaOrdua = helmugaOrdua;
	}
	public Long getChatId() {
		return chatId;
	}
	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public String toString() {
		LandabusBot bot = new LandabusBot();
		BidiMap<String,Integer> map = bot.getOrduakMap();
		
		String kontsulta;
		kontsulta = "EGUNA: "+"<b>"+(getData().getYear()+1900)+"/"+getData().getMonth()+"/"+getData().getDate()+"</b>"+"\n"+"IRTEERA: "+"<b>"+getIrteera().getIzena()
				+"</b>"+" ------> "
				+"IRTEERA ORDU MINIMOA: "+"<b>"+map.getKey(getIrteeraOrdua())+"\n"+"</b>"
				+"HELMUGA: "+"<b>"+getHelmuga().getIzena()
				+"</b>"+" ------> "
				+"HELTZEKO ORDU MAXIMOA: "+"<b>"+map.getKey(getHelmugaOrdua())+"</b>";
		return kontsulta;
	}
}
