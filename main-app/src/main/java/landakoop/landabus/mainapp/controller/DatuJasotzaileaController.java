package landakoop.landabus.mainapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import landakoop.landabus.mainapp.dao.AutobusGeldialdiaDao;
import landakoop.landabus.mainapp.model.AutobusGeldialdia;

@RestController
@RequestMapping("/api/datujasotzailea")
public class DatuJasotzaileaController {
	@Autowired
	AutobusGeldialdiaDao autobusGeldialdiaDao;	
	
	@PostMapping(path = "postFromJson", consumes = "application/json")
	public void postFromJson(@RequestBody AutobusGeldialdia autobusGeldialdia) {
		System.out.println(autobusGeldialdia);
		autobusGeldialdiaDao.save(autobusGeldialdia);	
	}
	
	@PostMapping(path = "postFromXml", consumes = "application/xml")
	public void postFromXml(@RequestBody AutobusGeldialdia autobusGeldialdia) {
		System.out.println(autobusGeldialdia);
		//autobusGeldialdiaDao.save(autobusGeldialdia);	
	}
}
