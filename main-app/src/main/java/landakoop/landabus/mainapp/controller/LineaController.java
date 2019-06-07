package landakoop.landabus.mainapp.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import landakoop.landabus.mainapp.dao.GeltokiaDao;
import landakoop.landabus.mainapp.dao.LineaDao;
import landakoop.landabus.mainapp.dao.OrdutegiaDao;
import landakoop.landabus.mainapp.model.Geltokia;
import landakoop.landabus.mainapp.model.Linea;
import landakoop.landabus.mainapp.model.rest.OrdutegiaRest;

@CrossOrigin()
@RestController
@RequestMapping("/api/linea")
public class LineaController {
	@Autowired
	Logger logger;
	@Autowired
	LineaDao lineaDao;
	@Autowired
	GeltokiaDao geltokiaDao;
	@Autowired
	OrdutegiaDao ordutegiaDao;
	
	@GetMapping("/")
	public List<Linea> getLineak(){
		List<Linea> lineak = (List<Linea>) lineaDao.findAll();
		return lineak;
	}
	
	@GetMapping("ordutegiak")
	public List<OrdutegiaRest> getOrdutegiak(@RequestParam long lineaID) {
		return ordutegiaDao.getOrdutegiak(lineaID);
	}
	
	@PutMapping("/")
	public Long sortuLinea(@Valid Linea linea, HttpServletResponse response) {
		Linea linea2 = lineaDao.save(linea);
		if(linea2 != null) {
			logger.info("{} linea ondo gorde da.",linea.getIzena());
			response.setStatus(HttpServletResponse.SC_CREATED);
			return linea2.getId();
		}else {
			logger.error("Linea ezin izan da gorde");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
	}
	
	@PostMapping("gehituGeltokiak")
	public void gehituGeltokiak(@RequestParam(name="lineaID", required=true) Long lineaID,
			                       @RequestParam(name="geltokiak", required=true) List<Long> geltokiak,
			                       HttpServletResponse response) {
		Optional<Linea> lineaF = lineaDao.findById(lineaID);
		if(lineaF.isPresent()) {
			Linea linea = lineaF.get();
			for(long geltokiaID : geltokiak) {
				Optional<Geltokia> geltokiaF = geltokiaDao.findById(geltokiaID);
				if(geltokiaF.isPresent()){
					Geltokia geltokia = geltokiaF.get();
					linea.addGeltokia(geltokia);
				}else {
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				}
			}
			lineaDao.save(linea);
		}else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}
	
	@DeleteMapping("/")
	public void ezabatuLinea(@RequestParam(name="lineaID",required=true) Long lineaID,HttpServletResponse response) {
		Optional<Linea> lineaF = lineaDao.findById(lineaID);
		if(lineaF.isPresent()) {
			lineaDao.deleteById(lineaID);
		}else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}
	
}
