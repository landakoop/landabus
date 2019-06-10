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
	
	public String makeGet(String url,Map<String,?> params) {
		String obj = null;
		try {
			url = url.concat("?");
			for(String s : params.keySet()) {
				if(!url.endsWith("?")) url = url.concat("&");
				url = url.concat(s + "={"+ s + "}");
			}
			
			RestTemplate restTemplate = new RestTemplate();
			obj = restTemplate.getForObject(url, String.class,params);
			
			//logger.info("Eskaera egin da, url={} params = {}",url,params);
		}catch(RestClientException e) {
			logger.error("Ezin izan da get eskaera egin, url={} params = {} except = {}",url,params,e.getClass());
		}
		return obj;
	}
}
