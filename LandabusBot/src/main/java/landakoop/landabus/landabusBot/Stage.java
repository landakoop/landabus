package landakoop.landabus.landabusBot;

public enum Stage {
	HASIERA,ESKATU,EGUTEGIA,GELTOKIA_IRTEERA,GELTOKIA_HELMUGA,ORDUA_IRTEERA,ORDUA_HELMUGA,KONFIRMAZIOA,AMAIERA;
	
	public Stage aurrekoa() {
		return values()[ordinal() - 2];
	}
	
	public Stage hurrengoa() {
		return values()[ordinal() + 1];
	}
}
