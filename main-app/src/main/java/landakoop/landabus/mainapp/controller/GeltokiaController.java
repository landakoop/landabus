package landakoop.landabus.mainapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import landakoop.landabus.mainapp.dao.DistantziaDao;
import landakoop.landabus.mainapp.dao.GeltokiaDao;
import landakoop.landabus.mainapp.model.Distantzia;
import landakoop.landabus.mainapp.model.Geltokia;
import landakoop.landabus.mainapp.model.rest.GeltokiaRest;

@CrossOrigin(origins="http://localhost:8081")
@RestController
@RequestMapping("/api/geltokia")
public class GeltokiaController {
	@Autowired
	GeltokiaDao geltokiaDao;
	@Autowired
	DistantziaDao distantziaDao;
	
	@GetMapping("list")
	public List<Geltokia> getGeltokiZerrenda(){
		return (List<Geltokia>) geltokiaDao.findAll();
	}
	
	@GetMapping("list2")
	public List<GeltokiaRest> getGeltokiZerrendaLinea(@RequestParam long ibilbideaID){
		return (List<GeltokiaRest>) geltokiaDao.getGeltokiak(ibilbideaID);
	}
	
	@GetMapping("distantziak")
	public Integer[][] getDistantziak(){
		List<Distantzia> distantziak = (List<Distantzia>) distantziaDao.findAll();
		int geltokiKop = (int) geltokiaDao.count();
		Integer[][] matrix = new Integer[geltokiKop][geltokiKop];
		for(Distantzia d : distantziak) {
			int irteeraID = (int) d.getIrteera().getId() -1;
			int helmugaID = (int) d.getHelmuga().getId() -1;
			matrix[irteeraID][helmugaID] = d.getDenbora();
		}
		return matrix;
	}	
}
