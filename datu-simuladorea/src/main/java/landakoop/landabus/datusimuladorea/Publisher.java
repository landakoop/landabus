package landakoop.landabus.datusimuladorea;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Publisher {
	Logger logger = LoggerFactory.getLogger("Datu simuladorea");
	Properties p = new Properties();
	private static final String EXCHANGE_NAME = "spring-boot-exchange";
    ConnectionFactory factory;
    List<AutobusGeldialdia> listaAutobusGeldialdia;
    Random random;
    
	public Publisher() {
		try {
			p.load(new FileReader("rabbit_mq.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		factory = new ConnectionFactory();
		rabbitConfiguration();
		listaAutobusGeldialdia = new ArrayList<>();
		random = new Random();
	}
	
	public void rabbitConfiguration() {
		factory.setHost(p.getProperty("RABBITMQ_HOST"));
		factory.setPort(Integer.parseInt(p.getProperty("RABBITMQ_PORT")));
		factory.setUsername(p.getProperty("RABBITMQ_USER"));
		factory.setPassword(p.getProperty("RABBITMQ_PASSWORD"));
	}
	
	public void createRandom() {
    	for (int i=0;i<10;i++) {
    		int ibilbide = 2;
    		Date d=new Date();
    		for(int k=0;k<8;k++) {
    			listaAutobusGeldialdia.add(new AutobusGeldialdia(random.nextInt(4)+1, 1,
        				k+1, d.getTime(), true));
    			listaAutobusGeldialdia.add(new AutobusGeldialdia(random.nextInt(4)+1, 1,
        				k+1, d.getTime(), false));
    			listaAutobusGeldialdia.add(new AutobusGeldialdia(random.nextInt(4)+1, 2,
        				8-k, d.getTime(), true));
    			listaAutobusGeldialdia.add(new AutobusGeldialdia(random.nextInt(4)+1, 2,
        				8-k, d.getTime(), false));
    		}
    		for (int j=10;j<60;j++) {    			
    			if ((j%5)==0) ibilbide++;
        		Date data = new Date(2019-1900, 4, i+1,
        				random.nextInt(12), random.nextInt(60));
        		AutobusGeldialdia autobusGeldialdia = new AutobusGeldialdia(random.nextInt(4)+1, ibilbide,
        				random.nextInt(7)+1, data.getTime(), random.nextBoolean());
        		listaAutobusGeldialdia.add(autobusGeldialdia);
        	}
    	}
    }
	
	
	public void enviarMensaje() {
		try(Connection connection = factory.newConnection();
				Channel channel = connection.createChannel();){	
			int i = 0;
			boolean durable = true;
			channel.exchangeDeclare(EXCHANGE_NAME, "fanout", durable);			
			
			for (AutobusGeldialdia a : listaAutobusGeldialdia) {
				String message = a.toString() + "$" + i++;
				channel.basicPublish(EXCHANGE_NAME,"", null, message.getBytes());
				logger.info(" [x] Sent '" + message + "'");
			}
				
			String message = "1$1$1$123456789456$igoo";			
			channel.basicPublish(EXCHANGE_NAME,"", null, message.getBytes());
			logger.info(" [x] Sent '" + message + "'");
			
			message = "1$1$1$499";			
			channel.basicPublish(EXCHANGE_NAME,"", null, message.getBytes());
			logger.info(" [x] Sent '" + message + "'");
			
			logger.info(" [*] Channel closed");			
			
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		} 
	}
	
	public static void main(String[] args) {
		Publisher publisher = new Publisher();
		publisher.createRandom();
		publisher.enviarMensaje();
	}
    
    
}
