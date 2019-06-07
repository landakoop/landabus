package landakoop.landabus.mainapp.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import landakoop.landabus.mainapp.dao.OrdutegiaDao;
import landakoop.landabus.mainapp.model.Ordutegia;

@CrossOrigin()
@RestController
@RequestMapping("/api/ordutegia")
public class OrdutegiaController {
	@Autowired
	Logger logger;
	@Autowired
	OrdutegiaDao ordutegiaDao;
	
	@PutMapping("/")
	public Long sortuOrdutegia(@Valid Ordutegia ordutegia, HttpServletResponse response) {
		Ordutegia ordutegia2 = ordutegiaDao.save(ordutegia);
		if(ordutegia2 != null) {
			logger.info("Ordutegia ondo gorde da.");
			response.setStatus(HttpServletResponse.SC_CREATED);
			return ordutegia2.getId();
		}else {
			logger.error("Ordutegia ezin izan da gorde");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
	}
}
