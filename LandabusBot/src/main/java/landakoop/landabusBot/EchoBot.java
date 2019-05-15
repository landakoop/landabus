package landakoop.landabusBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class EchoBot extends TelegramLongPollingBot{
	static final String username = "LandabusBot";
	static final String token = "839842086:AAGyahyGKbaWyX3tR4KDuQqveyJJ6gPjFUM";

	@Override
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return username;
	}


	@Override
	public String getBotToken() {
		// TODO Auto-generated method stub
		return token;
	}

	@Override
	public void onUpdateReceived(Update update) {
		// TODO Auto-generated method stub
		// Se obtiene el mensaje escrito por el usuario
		final String messageTextReceived = update.getMessage().getText();
		System.out.println("mezu bat jaso dut");
		// Se obtiene el id de chat del usuario
		final long chatId = update.getMessage().getChatId();
		
		// Se crea un objeto mensaje
		SendMessage message = new SendMessage().setChatId(chatId).setText(messageTextReceived);
		
		try {
			// Se envía el mensaje
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

}
