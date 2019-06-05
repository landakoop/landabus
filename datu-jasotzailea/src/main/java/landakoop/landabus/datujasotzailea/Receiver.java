package landakoop.landabus.datujasotzailea;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.rabbitmq.client.Channel;

@Component
public class Receiver implements ChannelAwareMessageListener {
	@Autowired
	Logger logger;
	@Value("${COM_PROTOCOL}://${MAINAPP_HOST}:${MAINAPP_PORT}")
	String url;
	List<AutobusGeldialdia> listaAutobusGeldialdia = new ArrayList<>();
	int numReintentos = 0;
	
	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		String str = new String(message.getBody(), "UTF-8");
		String[] datuak = str.split("[$]");
		boolean valid = confirmMessage(message.getMessageProperties().getDeliveryTag(), channel, datuak);
		
		if (valid) {
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			AutobusGeldialdia autobusGeldialdia = createObject(datuak);
			sendPost(autobusGeldialdia);
		}
		else {
			if (numReintentos < 3) {
				numReintentos++;
				channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
			}else {
				numReintentos = 0;
				channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
			}
		}
	}
	
	public boolean confirmMessage(long deliveryTag, Channel channel, String[] datuak) throws IOException {
		boolean valid;
		long data = 0;
		String ekintza = "";
		
		try {
			ekintza = datuak[4];
			int erabiltzailea = Integer.parseInt(datuak[0]);
			int ibilbidea = Integer.parseInt(datuak[1]);
			int geltokia = Integer.parseInt(datuak[2]);
			data = Long.parseLong(datuak[3]);
			valid = true;
		}catch (NumberFormatException e) {
			valid = false;
			logger.error("Number Format Exception: " + e.getMessage());
		}catch (ArrayIndexOutOfBoundsException e) {
			valid = false;
			logger.error("Array tamaina okerra: " + e.getMessage());
		}
		
		if (!((ekintza.toLowerCase().equals("igo") || ekintza.toLowerCase().equals("jaitsi")))) {
			valid = false;
			logger.error("Ekintza okerra: " + ekintza);
		}
		
		if (data < 500) {
			valid = false;
			logger.error("Data okerra: " + new Date(data));
		}
		
		return valid;
	}
	
	public AutobusGeldialdia createObject(String[] datuak) {
		return new AutobusGeldialdia(Integer.parseInt(datuak[0]),
				Integer.parseInt(datuak[1]), Integer.parseInt(datuak[2]), Long.parseLong(datuak[3]), datuak[4]);
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
