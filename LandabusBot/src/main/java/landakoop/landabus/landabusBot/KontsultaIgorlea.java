package landakoop.landabus.landabusBot;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import landakoop.landabus.beans.Kontsulta;

@Component
public class KontsultaIgorlea {
	@Value("${COM_PROTOCOL}://${MAINAPP_HOST}:${MAINAPP_PORT}") 
	String url;
	
	public boolean receiveMessage(Kontsulta kontsulta) {
		try {
			sendPost(kontsulta);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void sendPost(Kontsulta kontsulta) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		//URI uri = new URI(url+"/api/eskaera/postFromJson");		
		URI uri = new URI("http://main-app:8080/api/eskaera/postFromJson");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);			

		HttpEntity<Kontsulta> requestBody = new HttpEntity<>(kontsulta, headers);
		restTemplate.postForEntity(uri, requestBody, Kontsulta.class);		
	}

}
