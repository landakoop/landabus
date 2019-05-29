package landakoop.landabus.datujasotzailea;

import java.util.ArrayList;
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
    	for (int i=0;i<1;i++) {
    		AutobusGeldialdia autobusGeldialdia = new AutobusGeldialdia(1, 1,
    				1, 1479249799770L, random.nextBoolean());
    		listaAutobusGeldialdia.add(autobusGeldialdia);
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