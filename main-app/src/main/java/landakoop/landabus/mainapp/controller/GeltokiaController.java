package landakoop.landabus.mainapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import landakoop.landabus.mainapp.dao.GeltokiaDao;
import landakoop.landabus.mainapp.model.Geltokia;

@Controller
@RequestMapping("/api/geltokia")
public class GeltokiaController {
	@Autowired
	GeltokiaDao geltokiaDao;
	
	@GetMapping("list")
	public @ResponseBody List<Geltokia> getOrderList(){
		return (List<Geltokia>) geltokiaDao.findAll();
	}
	
}
