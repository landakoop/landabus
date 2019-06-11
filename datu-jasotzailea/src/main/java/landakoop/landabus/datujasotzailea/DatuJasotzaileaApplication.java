package landakoop.landabus.datujasotzailea;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DatuJasotzaileaApplication {
	@Value("${EXCHANGE_NAME}")
	String EXCHANGE_NAME;

	@Value("${QUEUE_NAME}")
	String QUEUE_NAME;

    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }
    
    @Bean
    FanoutExchange fanaoutExchange() {
    	return new FanoutExchange(EXCHANGE_NAME);
    }
    @Bean
    Binding bindingFanout(Queue queue, FanoutExchange exchange) {
    	return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter, MessageListenerAdapter listenerAdapterProba) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setPrefetchCount(1);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "onMessage");
    }

	public static void main(String[] args) {
		SpringApplication.run(DatuJasotzaileaApplication.class, args);
	}
}
