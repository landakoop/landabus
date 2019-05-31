package landakoop.landabus.datujasotzailea;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {
	List<AutobusGeldialdia> listaAutobusGeldialdia;
	Random random;
    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
        listaAutobusGeldialdia = new ArrayList<>();
        random = new Random();
    }
    
    public void createRandom() {
    	for (int i=0;i<10;i++) {
    		int ibilbide = 0;
    		for (int j=0;j<20;j++) {    			
    			if ((j%5)==0) ibilbide++;
        		Date data = new Date(2019-1900, 4, i+1,
        				random.nextInt(12), random.nextInt(60));
        		AutobusGeldialdia autobusGeldialdia = new AutobusGeldialdia(random.nextInt(4)+1, ibilbide,
        				random.nextInt(5)+1, data.getTime(), random.nextBoolean());
        		listaAutobusGeldialdia.add(autobusGeldialdia);
        	}
    	}    	
    }

    @Override
    public void run(String... args) throws Exception {
    	createRandom();
        System.out.println("Sending message...");
        for (AutobusGeldialdia a : listaAutobusGeldialdia)
        	rabbitTemplate.convertAndSend(DatuJasotzaileaApplication.fanoutExchangeName, "", a.toString());
    }

}