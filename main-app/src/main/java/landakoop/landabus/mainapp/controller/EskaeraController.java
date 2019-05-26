package landakoop.landabus.mainapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import landakoop.landabus.mainapp.dao.EskaeraDao;
import landakoop.landabus.mainapp.model.Eskaera;

@RestController
@RequestMapping("/api/eskaera")
public class EskaeraController {
	@Autowired
	EskaeraDao eskaeraDao;
	
	@GetMapping("list")
	public List<Eskaera> getOrderList(){
		return (List<Eskaera>) eskaeraDao.findAll();
	}
	
	@PutMapping("/employees/{id}")
	public void sortuEskaera() {
		
	}
	
}
