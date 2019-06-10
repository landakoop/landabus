package froga;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<String, String> linea = new LinkedHashMap<String, String>();
		linea.put("05:45", "Arrasate");
		linea.put("06:00", "Bergara");
		linea.put("06:15", "Eskoriatza");
		System.out.println(lineaToString(linea));
	}
	
	public static String lineaToString(Map<String, String> lineaMap) {
		String str = new String();
		//String [] lineaStr = new String[lineaMap.size()];
		String linea = new String();
		
		for(Entry<String,String> entry : lineaMap.entrySet()) {
			linea = linea+"-->"+entry.getValue()+": "+entry.getKey()+"\n";

		}
		str = "Zure eskaera <b>onartua</b> izan da.\"\\n\""
				+"LINEA: \n"+linea;
		return str;
	}

}
