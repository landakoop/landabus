package landakoop.landabusBot;

import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class EchoBot extends TelegramWebhookBot{
	static final String username = "LandabusBot";
	static final String token = "832360127:AAGbxjRBncq9NSdgOB1q3-t7ZnUaFp1RFSo";
	
	@Override
	public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
		// Se obtiene el mensaje escrito por el usuario
		final String messageTextReceived = update.getMessage().getText();
		
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
		
		return null;
	}

	@Override
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public String getBotPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBotToken() {
		// TODO Auto-generated method stub
		return token;
	}

}
