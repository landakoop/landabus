package landakoop.landabusBot;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class LandabusBot extends TelegramLongPollingBot{
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
		//SendMessage message = new SendMessage().setChatId(chatId).setText(messageTextReceived);
		SendMessage message2 = new SendMessage().setChatId(chatId).setText("main menu").setReplyMarkup(getMainMenuKeyboard());
		
		try {
			// Se envía el mensaje
			execute(message2);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		

	}

	private static ReplyKeyboardMarkup getMainMenuKeyboard() {
		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(getKontsultatuCommand());
        keyboard.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        
		return replyKeyboardMarkup;
	}
	
    private static String getKontsultatuCommand() {
        return "KONTSULTATU";
    }

}
