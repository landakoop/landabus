package landakoop.landabusBot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
//import org.telegram.telegrambots.meta.api.methods.send.;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@Component
public class LandabusBot extends TelegramLongPollingBot{
	private static final Logger logger = LoggerFactory.getLogger(LandabusBot.class);
	//static final String fUsername = "LandabusBot";
	//static final String fToken = "839842086:AAGyahyGKbaWyX3tR4KDuQqveyJJ6gPjFUM";
	//static final String[] arrayGeltokiak = {"AMAIUR","BERA","ZIGA","DONEZTEBE","ELIZONDO","ZUGARRAMURDI","GARTZAIN"};
	
	@Value("${bot.token}")
	private String token;
	
	@Value("${bot.username}")
	private String username;
	
	@Override
	public String getBotToken() {
		return token;
	}
	
	@Override
	public String getBotUsername() {
		return username;
	}
	
	@PostConstruct
	public void start() {
		logger.info("username: {}, token: {}", username, token);
	}
	
	@PreDestroy
	public void kill() {
			try {
				this.finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage()) {
			Message message = update.getMessage();
			SendMessage response = new SendMessage();
			Long chatId = message.getChatId();
			response.setChatId(chatId);
			String text = message.getText();
			response.setText(text);
			try {
				execute(response);
				logger.info("Mezua \"{}\" , nori: {}", text, chatId);
			} catch (TelegramApiException e) {
				logger.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
			}
		}
	}
	
	
	@SuppressWarnings("unused")
	private static ReplyKeyboardMarkup stringListToMarkup(List<String> list) {
		ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup();
		
		return replyMarkup;
	}

	@SuppressWarnings("unused")
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
