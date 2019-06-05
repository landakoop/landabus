package landakoop.landabus.mainapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import landakoop.landabus.mainapp.dao.IbilbideaDao;
import landakoop.landabus.mainapp.model.rest.ErabileraRest;
import landakoop.landabus.mainapp.model.rest.IbilbideaRest;

@CrossOrigin(origins="http://localhost:8081")
@RestController
@RequestMapping("/api/ibilbidea")
public class IbilbideaController {
	@Autowired
	IbilbideaDao ibilbideaDao;
	
	@GetMapping("list")
	public List<IbilbideaRest> getOrderList(){
		List<IbilbideaRest> ibilbideak =  (List<IbilbideaRest>) ibilbideaDao.getIbilbideak();
		return ibilbideak;
	}
	
	@GetMapping("historikoa")
	public List<ErabileraRest> getHistorikoa(){
		List<ErabileraRest> ibilbideak =  ibilbideaDao.getErabilera();
		return ibilbideak;
	}
	
	
}
