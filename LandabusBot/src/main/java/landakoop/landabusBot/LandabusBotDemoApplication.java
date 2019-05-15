package landakoop.landabusBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootApplication
public class LandabusBotDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(LandabusBotDemoApplication.class, args);
		System.out.println("Kaixo");
		ApiContextInitializer.init();
		
		final TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		
		try {
			// Se registra el bot
			telegramBotsApi.registerBot(new EchoBot());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

}
