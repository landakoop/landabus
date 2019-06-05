package landakoop.landabus.mainapp.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
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
	Logger logger;
	
	@Autowired
	AutobusGeldialdiaDao autobusGeldialdiaDao;	
	
	@PostMapping(path = "postFromJson", consumes = "application/json")
	public void postFromJson(@Valid @RequestBody AutobusGeldialdia autobusGeldialdia) {
		logger.info(autobusGeldialdia.toString());
		autobusGeldialdiaDao.save(autobusGeldialdia);	
	}
}
