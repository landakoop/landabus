package landakoop.landabus.mainapp.logic;

import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class Sender {
	@Autowired
	Logger logger;
	
	public Object makeGet(String url,Map<String,?> params) {
		Object obj = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			obj = restTemplate.getForObject(url, Object.class,params);
			logger.info("Eskaera egin da, url={} params = {}",url,params);
		}catch(RestClientException e) {
			logger.error("Ezin izan da get eskaera egin, url={} params = {} except = {}",url,params,e.getClass());
		}
		return obj;
	}
}
