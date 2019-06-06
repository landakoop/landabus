package landakoop.landabus.mainapp.logic;

import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


public class Sender {
	@Autowired
	Logger logger;
	
	public void makeGet(String url,Map<String,?> params) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getForObject(url, Integer.class,params);
			logger.info("Eskaera egin da, url={} params = {}",url,params);
		}catch(RestClientException e) {
			logger.info(url);
			logger.info(params.toString());
			logger.error("Ezin izan da get eskaera egin, url={} params = {} except = {}",url,params,e.getClass());
		}
	}
}
