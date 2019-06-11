package landakoop.landabus.landabusBot;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import landakoop.landabus.beans.Geltokia;

public class GeltokiakJaso {
	private static final Logger logger = LoggerFactory.getLogger(GeltokiakJaso.class);
	
	@Value("${mainapp.protocol}"+"://"+"${mainapp.host}"+":"+"${mainapp.port}") 
	String url;
	
	List<Geltokia> lista;
	
	public GeltokiakJaso() {
		lista = new ArrayList<>();
	}
	
	public List<Geltokia> geltokiakJaso() {
		boolean jaso = false;
		while(!jaso) {
			try {
				lista = eskaeraEgin();
				jaso = true;
			} catch (Exception e) {
				logger.error("Ezin izan da main-app etik geltoki zerrenda eskuratu. Excep= {} ", e.getClass());
				itxaron(10000);
			}
		}

		return lista;
	}
	
	public void itxaron(int milis) {
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
			logger.error("Itxarotea gelditu da");
		}
	}
	
	private List<Geltokia>  eskaeraEgin() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		URI uri = new URI(url +"/api/geltokia/list");
		return Arrays.asList(restTemplate.getForObject(uri, Geltokia[].class));
	}

	public List<Geltokia> getLista() {
		return lista;
	}
}
