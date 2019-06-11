package landakoop.landabus.mainapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import landakoop.landabus.mainapp.dao.AutobusGeldialdiaDao;
import landakoop.landabus.mainapp.model.rest.AutobusGeldialdiaRest;
import landakoop.landabus.mainapp.model.rest.PredikzioaRest;

@CrossOrigin()
@RestController
@RequestMapping("/api/geldialdia")
public class GeldialdiaController {
	@Autowired
	AutobusGeldialdiaDao autobusGeldialdiaDao;
	
	@GetMapping("errealak")
	public List<AutobusGeldialdiaRest> getGeldialdiErrealak() {
		return autobusGeldialdiaDao.getGeldialdiErreala();	
	}
	
	@GetMapping("aurreikuspenak")
	public List<PredikzioaRest> getGeldialdiAurreikuspenak() {
		return autobusGeldialdiaDao.getGeldialdiAurreikuspena();	
	}
}
