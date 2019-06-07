package landakoop.landabus.landabusBot;

public enum Stage {
	HASIERA,KONTSULTATU,EGUTEGIA,GELTOKIA_IRTEERA,GELTOKIA_HELMUGA,ORDUA_IRTEERA,ORDUA_HELMUGA,KONFIRMAZIOA,AMAIERA;
	
	public Stage aurrekoa() {
		if (ordinal() == values().length - 2)
			return values()[ordinal() - 1];
		else
			return values()[ordinal() - 2];
	}
	
	public Stage hurrengoa() {
		return values()[ordinal() + 1];
	}
}
