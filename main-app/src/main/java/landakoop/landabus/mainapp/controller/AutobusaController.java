package landakoop.landabus.mainapp.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import landakoop.landabus.mainapp.dao.OrdutegiaDao;

@RestController
@RequestMapping("/api/autobusa")
public class AutobusaController {
	@Autowired
	private Logger logger;
	@Autowired
	OrdutegiaDao ordutegiaDao;
	
	@GetMapping("eskuragarritasuna")
	public boolean getEskuragarritasuna(@RequestParam("irteeraOrdua") int irteeraOrdua,
			                            @RequestParam("helmugaOrdua") int helmugaOrdua) {	
		int autobusOkupatuak = ordutegiaDao.autobusOkupatuak(irteeraOrdua, helmugaOrdua);
		//logger.info("{} - {} tartean okupatutako autobusak:",irteeraOrdua,helmugaOrdua,autobusOkupatuak);
		return (autobusOkupatuak < 2);
	}
	
}
