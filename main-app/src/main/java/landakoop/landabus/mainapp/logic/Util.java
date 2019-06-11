package landakoop.landabus.mainapp.logic;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import landakoop.landabus.mainapp.dao.GeltokiaDao;
import landakoop.landabus.mainapp.dao.IbilbideaDao;
import landakoop.landabus.mainapp.dao.LineaDao;
import landakoop.landabus.mainapp.dao.OrdutegiaDao;
import landakoop.landabus.mainapp.model.Geltokia;
import landakoop.landabus.mainapp.model.Linea;
import landakoop.landabus.mainapp.model.rest.GeltokiaRest;

@Component
public class Util {
	@Autowired
	LineaDao lineaDao;
	
	@Autowired
	GeltokiaDao geltokiaDao;
	
	@Autowired
	IbilbideaDao ibilbideaDao;
	
	@Autowired
	OrdutegiaDao ordutegiaDao;
	
	public Map<String,String> getDenbora(long ibilbideaID){
		Map<String,String> mapa = new LinkedHashMap<>();
		int ordua;
		Optional<Linea> linea = lineaDao.findById(ibilbideaID);
		if(linea.isPresent()) {
			List<Geltokia> geltokiak = (List<Geltokia>) linea.get().getGeltokiak();
			ordua = ordutegiaDao.getOrdutegiak(ibilbideaID).get(0).getIrteeraOrdua();
			mapa.put(geltokiak.get(0).getIzena(),intOrduaToString(ordua));
			int denbora = 0;
			for(int i = 1; i < geltokiak.size();i++) {
				denbora = lineaDao.getDistantzia(ibilbideaID,geltokiak.get(i-1).getId(),geltokiak.get(i).getId());
				ordua = ordua + denbora;
				mapa.put(geltokiak.get(i).getIzena(),intOrduaToString(ordua));
			}
		}
		return mapa;
	}
	
	public Map<String,String> getLinea(long ibilbideaID){
		Map<String,String> mapa = new LinkedHashMap<>();
		List<GeltokiaRest> listGeltokiak = new ArrayList<>();
		System.out.println(geltokiaDao);
		listGeltokiak = geltokiaDao.getGeltokiak(ibilbideaID);
		for(GeltokiaRest geltokia:listGeltokiak) {
			mapa.put(intOrduaToString(geltokia.getOrdua()),geltokia.getIzena());
			System.out.println(geltokia.getOrdua()+":"+geltokia.getIzena());
		}

		return mapa;
	}
	
	
	private String intOrduaToString(int minututan) {
		String strMinutuak = new String();
		String strOrdua = new String();
		int ordua = minututan/60;
		int minutua = minututan%60;
		if(ordua < 10) strOrdua = ""+0+ordua;
		else strOrdua = ""+ordua;
		if(minutua < 10) strMinutuak = ""+0+minutua;
		else strMinutuak = ""+minutua;
		return strOrdua+":"+strMinutuak;
	}
}
