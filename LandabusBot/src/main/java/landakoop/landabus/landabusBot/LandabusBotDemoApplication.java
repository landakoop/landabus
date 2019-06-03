package landakoop.landabus.landabusBot;

import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LandabusBotDemoApplication {
    @Bean
    MessageListenerAdapter listenerAdapter(KontsultaIgorlea igorlea) {
        return new MessageListenerAdapter(igorlea, "receiveMessage");
    }
    
	public static void main(String[] args) {
		SpringApplication.run(LandabusBotDemoApplication.class, args);
	}

}
