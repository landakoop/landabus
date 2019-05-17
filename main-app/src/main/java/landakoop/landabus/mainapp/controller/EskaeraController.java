package landakoop.landabus.mainapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import landakoop.landabus.mainapp.dao.EskaeraDao;
import landakoop.landabus.mainapp.model.Eskaera;

@Controller
@RequestMapping("/api/eskaera")
public class EskaeraController {
	@Autowired
	EskaeraDao eskaeraDao;
	
	@GetMapping("list")
	public @ResponseBody List<Eskaera> getOrderList(){
		return (List<Eskaera>) eskaeraDao.findAll();
	}
}
