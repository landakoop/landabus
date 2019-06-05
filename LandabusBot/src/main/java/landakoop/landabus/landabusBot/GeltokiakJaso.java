package landakoop.landabus.landabusBot;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import landakoop.landabus.beans.Geltokia;

public class GeltokiakJaso {
	@Value("${mainapp.protocol}"+"://"+"${mainapp.host}"+":"+"${mainapp.port}") 
	String url;
	
	List<Geltokia> lista;
	
	public GeltokiakJaso() {
		lista = new ArrayList<>();
	}
	
	public List<Geltokia> geltokiakJaso() {
		try {
			lista = eskaeraEgin();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	private List<Geltokia>  eskaeraEgin() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		URI uri = new URI("http://main-app:8080/api/geltokia/list");
		//URI uri = new URI(url+"/api/geltokia/list");
		//System.out.println(uri.toString());		
		return Arrays.asList(restTemplate.getForObject(uri, Geltokia[].class));
	}

	public List<Geltokia> getLista() {
		return lista;
	}
}
