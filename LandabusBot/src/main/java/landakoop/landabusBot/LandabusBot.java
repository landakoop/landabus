package landakoop.landabusBot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class LandabusBot extends TelegramLongPollingBot{
	private static final Logger logger = LoggerFactory.getLogger(LandabusBot.class);
	static final String[] hasiera = {"KONTSULTATU"};
	static final String[] arrayGeltokiak = {"AMAIUR","BERA","ZIGA","DONEZTEBE","ELIZONDO","ZUGARRAMURDI","GARTZAIN"};
	static final String aurkezpena = "Ordutegien kontsulta edo eskakizun bat egiteko sakatu: KONTSULTATU ";
	static final String irteeraGaldera = "Non hartu nahi duzu autobusa?";
	static final String helmugaGaldera = "Nora heldu nahi duzu autobusean?";
	
	int stage;
	
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

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage()) {
			Message message = update.getMessage();
			
			if(message.getText().equals("/start")) stage = 0;
			else if(message.getText().equals("KONTSULTATU")) stage = 1;
			
			switch(stage) {
			case 0: try {
					execute(mezuaBidali(message,aurkezpena));
					logger.info("Hasiera orria");
				} catch (TelegramApiException e2) {
					logger.error("Errorea hasierako orria bistaratzean");
					e2.printStackTrace();
				}
				break;
			case 1: try {
					execute(mezuaBidali(message,irteeraGaldera));
					logger.info("Kontsultako lehenengo galdetegia bidalita \"{}\" erabiltzaileari", message.getChatId());
					stage = 2;
				} catch (TelegramApiException e1) {
					logger.error("Failed to send message to {} due to error: {}", message.getChatId(), e1.getMessage());
				}
				break;
			case 2: System.out.println(message.getText());
				try {
					execute(mezuaBidali(message,helmugaGaldera));
					logger.info("Kontsultako lehenengo galdetegia bidalita \"{}\" erabiltzaileari", message.getChatId());
					stage = 3;
				} catch (TelegramApiException e1) {
					logger.error("Failed to send message to {} due to error: {}", message.getChatId(), e1.getMessage());
				}
				break;
			default: 
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
				break;
			}
		}
	}
	
	private SendMessage mezuaBidali(Message message, String str) {
		SendMessage msg = new SendMessage();
		msg.setChatId(message.getChatId());
		msg.setText(str);
		msg.setReplyMarkup(kontsultaGaldetegia(stage));
		return msg;
	}
	
	
	private ReplyKeyboardMarkup kontsultaGaldetegia(int aukeraLista) {
		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);		
        List<KeyboardRow> keyboard = new ArrayList<>();
        String str[] = null;
        
        switch(aukeraLista) {
        case 0: str = hasiera;
         break;
        case 1: str = arrayGeltokiak;
        	break;
        case 2:str = arrayGeltokiak;
    		break;
        }
  
		for(String geltokia: str) {
			KeyboardRow keyboardRow = new KeyboardRow();
			keyboardRow.add(geltokia);
			keyboard.add(keyboardRow);
		}
		
		replyKeyboardMarkup.setKeyboard(keyboard);
		return replyKeyboardMarkup;
	}
}
