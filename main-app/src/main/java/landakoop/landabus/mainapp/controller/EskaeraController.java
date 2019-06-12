package landakoop.landabus.mainapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import landakoop.landabus.mainapp.dao.ErabiltzaileaDao;
import landakoop.landabus.mainapp.dao.EskaeraDao;
import landakoop.landabus.mainapp.dao.GeltokiaDao;
import landakoop.landabus.mainapp.model.Erabiltzailea;
import landakoop.landabus.mainapp.model.Eskaera;
import landakoop.landabus.mainapp.model.Geltokia;


@RestController
@RequestMapping("/api/eskaera")
public class EskaeraController {
	@Autowired
	EskaeraDao eskaeraDao;
	
	@Autowired
	GeltokiaDao geltokiaDao;
	
	@Autowired
	ErabiltzaileaDao erabiltzaileaDao;
	
	@GetMapping("list")
	public  List<Eskaera> getOrderList(){
		return (List<Eskaera>) eskaeraDao.getEskaerak();
	}
	
	@PostMapping(path = "postFromJson", consumes = "application/json")
	public void postFromJson(@RequestBody Eskaera jasotakoEskaera){
		Optional<Geltokia> geltokiaA = geltokiaDao.findById(jasotakoEskaera.getIrteera().getId());
		Optional<Geltokia> geltokiaB = geltokiaDao.findById(jasotakoEskaera.getHelmuga().getId());
		jasotakoEskaera.setIrteera(geltokiaA.get());
		jasotakoEskaera.setHelmuga(geltokiaB.get());
		
		Erabiltzailea erabiltzailea = erabiltzaileaDao.findByTelegramID(String.valueOf(jasotakoEskaera.getChatId()));
		if(erabiltzailea == null) {
			erabiltzailea = new Erabiltzailea();
			erabiltzailea.setTelegramID(String.valueOf(jasotakoEskaera.getChatId()));
			System.out.println("Erabiltzaile berria");
		}
		jasotakoEskaera.setErabiltzailea(erabiltzailea);
		
		System.out.println(jasotakoEskaera);
		eskaeraDao.save(jasotakoEskaera);
	}
	
	@PostMapping(path = "postFromXml", consumes = "application/xml")
	public void postFromXml(@RequestBody Eskaera eskaera) {
		System.out.println(eskaera);
		//eskaeraDao.save(eskaera);	
	}
	
	@GetMapping("malgutasuna")
	public int getMalgutasuna() {
		Integer malgutasuna = eskaeraDao.getMalgutasuna();
		if(malgutasuna == null)
			malgutasuna = 0;
		return malgutasuna;
	}
	
	
}
