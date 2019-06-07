package landakoop.landabus.mainapp.logic;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import landakoop.landabus.mainapp.dao.LineaDao;
import landakoop.landabus.mainapp.model.Geltokia;
import landakoop.landabus.mainapp.model.Linea;

@Component
public class Util {
	@Autowired
	LineaDao lineaDao;
	
	public Map<String,Integer> getDenbora(long lineaID){
		Map<String,Integer> mapa = new LinkedHashMap<>();
		Optional<Linea> linea = lineaDao.findById(lineaID);
		if(linea.isPresent()) {
			List<Geltokia> geltokiak = (List<Geltokia>) linea.get().getGeltokiak();
			mapa.put(geltokiak.get(0).getIzena(), 0);
			int denbora = 0;
			for(int i = 1; i < geltokiak.size();i++) {
				denbora += lineaDao.getDistantzia(lineaID,geltokiak.get(i-1).getId(),geltokiak.get(i).getId());
				mapa.put(geltokiak.get(i).getIzena(),denbora);
			}
		}
		return mapa;
	}
}
