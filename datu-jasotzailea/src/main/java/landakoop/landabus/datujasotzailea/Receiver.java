package landakoop.landabus.datujasotzailea;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Receiver {
	@Value("${COM_PROTOCOL}://${MAINAPP_HOST}:${MAINAPP_PORT}")
	String url;
	//List<AutobusGeldialdia> listaAutobusGeldialdia = new ArrayList<>();
	
	public void receiveMessage(String autobusGeldialdiaString) {
		AutobusGeldialdia autobusGeldialdia = createObject(autobusGeldialdiaString);
		System.out.println(autobusGeldialdia);
		try {
			sendPost(autobusGeldialdia);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public AutobusGeldialdia createObject(String proba) {
		String[] datuak = proba.split("[$]");		
		
		AutobusGeldialdia autobusGeldialdia = new AutobusGeldialdia(Integer.parseInt(datuak[0]),
					Integer.parseInt(datuak[1]), Integer.parseInt(datuak[2]), Long.parseLong(datuak[3]), true);
		
		//listaAutobusGeldialdia.add(autobusGeldialdia);
		
		return autobusGeldialdia;
	}	
	
	public void sendPost(AutobusGeldialdia autobusGeldialdia) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		URI uri = new URI(url+"/api/datujasotzailea/postFromJson");		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);			
			
		HttpEntity<AutobusGeldialdia> requestBody = new HttpEntity<>(autobusGeldialdia, headers);
		restTemplate.postForEntity(uri, requestBody, AutobusGeldialdia.class);		
	}
}
