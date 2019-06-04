package landakoop.landabus.mainapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import landakoop.landabus.mainapp.dao.ErabiltzaileaDao;
import landakoop.landabus.mainapp.dao.EskaeraDao;
import landakoop.landabus.mainapp.model.Erabiltzailea;
import landakoop.landabus.mainapp.model.Eskaera;


@RestController
@RequestMapping("/api/eskaera")
public class EskaeraController {
	@Autowired
	EskaeraDao eskaeraDao;
	
	@Autowired
	ErabiltzaileaDao erabiltzaileaDao;
	
	@GetMapping("list")
	public  List<Eskaera> getOrderList(){
		return (List<Eskaera>) eskaeraDao.findAll();
	}
	
	@PostMapping(path = "postFromJson", consumes = "application/json")
	public void postFromJson(@RequestBody Eskaera jasotakoEskaerak){
		Eskaera eskaera = new Eskaera();
		
		eskaera.setHelmuga(jasotakoEskaerak.getHelmuga());
		eskaera.setIrteera(jasotakoEskaerak.getIrteera());
		eskaera.setHelmugaOrdua(jasotakoEskaerak.getHelmugaOrdua());
		eskaera.setIrteeraOrdua(jasotakoEskaerak.getIrteeraOrdua());
		eskaera.setChatId(jasotakoEskaerak.getChatId());
		
		Erabiltzailea erabiltzailea = new Erabiltzailea();
		try {
			erabiltzailea = erabiltzaileaDao.findByTelegramID(String.valueOf(eskaera.getChatId()));
			eskaera.setErabiltzailea(erabiltzailea);
			System.out.println("ERABILTZAILEA EXISTITZEN ZEN");
		}catch(NullPointerException e) {
			erabiltzailea.setTelegramID(String.valueOf(jasotakoEskaerak.getChatId()));
			eskaera.setErabiltzailea(erabiltzailea);
			System.out.println("ERABILTZAILE BERRIA SORTUA: "+erabiltzailea.getTelegramID());
		}
	
		//eskaeraDao.save(eskaera);
		
	}
	
	@PostMapping(path = "postFromXml", consumes = "application/xml")
	public void postFromXml(@RequestBody Eskaera eskaera) {
		System.out.println(eskaera);
		//eskaeraDao.save(eskaera);	
	}
	
	
}
