package landakoop.landabus.landabusBot;

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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import landakoop.landabus.beans.Geltokia;
import landakoop.landabus.beans.Kontsulta;

@Component
public class LandabusBot extends TelegramLongPollingBot{
	private static final Logger logger = LoggerFactory.getLogger(LandabusBot.class);
	
	static final String[] hasiera = {"KONTSULTATU"};
	static final String[] arrayOrduak = {"05:00","05:30","06:00","06:30","07:00","07:30","08:00","08:30","09:00","09:30","10:00",
			"10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30",
			"18:00","18:30","19:00","19:30","20:00","20:30","21:00","21:30","22:00","22:30","23:00","23:30","00:00","00:30"};
	static final String aurkezpena = "Ordutegien kontsulta edo eskakizun bat egiteko sakatu: KONTSULTATU ";
	static final String irteeraGaldera = "Non hartu nahi duzu autobusa?";
	static final String helmugaGaldera = "Nora heldu nahi duzu autobusean?";
	static final String irteeraOrduGaldera = "Ze ordutatik aurrera irten zaitezke?";
	static final String helmugaOrduGaldera = "Ze ordutarako egon behar zara helmugan?";
	static final String[] atzera = {"ZUZENDU","HASIERA"};
	
	String[] arrayGeltokiak;
	GeltokiakJaso geltokiakJaso;
	List<Kontsulta> kontsultak;
	List<Geltokia> geltokiak;
	int stage;
	
	public LandabusBot() {
		kontsultak = new ArrayList<>();
		geltokiak = new ArrayList<>();
		geltokiakJaso = new GeltokiakJaso();
		geltokiak = geltokiakJaso.geltokiakJaso();
		arrayGeltokiak = new String[geltokiak.size()];
		
		for(int i = 0; i < geltokiak.size(); i++) {
			arrayGeltokiak[i] = geltokiak.get(i).getIzena();
		}
	}
	
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
			else if(message.getText().equals("ZUZENDU")) stage = stage - 2;
			else if(message.getText().equals("HASIERA")) stage = 0;
			
			
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
			case 2: 
				if(!message.getText().equals(atzera[0])) {
					Kontsulta kontsulta = new Kontsulta();
					kontsulta.setIrteera(strToGeltokia(message.getText()));
					kontsulta.setChatId(message.getChatId());
					kontsultak.add(kontsulta);
				}
				
				try {
					execute(mezuaBidali(message,helmugaGaldera));
					logger.info("Kontsultako bigarren galdetegia bidalita \"{}\" erabiltzaileari", message.getChatId());
					stage = 3;
				} catch (TelegramApiException e1) {
					logger.error("Failed to send message to {} due to error: {}", message.getChatId(), e1.getMessage());
				}
				break;
			case 3: 
					for(Kontsulta k: kontsultak) {
						if(k.getChatId().equals(message.getChatId()) && !message.getText().equals(atzera[0])) k.setHelmuga(strToGeltokia(message.getText()));
					}
				
				try {
					execute(mezuaBidali(message,irteeraOrduGaldera));
					logger.info("Kontsultako hirugarren galdetegia bidalita \"{}\" erabiltzaileari", message.getChatId());
					stage = 4;
				} catch (TelegramApiException e1) {
					logger.error("Failed to send message to {} due to error: {}", message.getChatId(), e1.getMessage());
				}
				break;
			case 4: 
				for(Kontsulta k: kontsultak) {
					if(k.getChatId().equals(message.getChatId()) && !message.getText().equals(atzera[0])) k.setIrteeraOrdua(message.getText());
				}
				try {
					execute(mezuaBidali(message,helmugaOrduGaldera));
					logger.info("Kontsultako laugarren galdetegia bidalita \"{}\" erabiltzaileari", message.getChatId());
					stage = 5;
				} catch (TelegramApiException e1) {
					logger.error("Failed to send message to {} due to error: {}", message.getChatId(), e1.getMessage());
				}
				break;
			case 5:
				SendMessage fin = new SendMessage();
				Long Id = message.getChatId();
				fin.setChatId(Id);
				
				for(Kontsulta k: kontsultak) {
					if(k.getChatId().equals(message.getChatId()) && !message.getText().equals(atzera[0])) {
						k.setHelmugaOrdua(message.getText());
						fin.setText(k.toString());
						KontsultaIgorlea igorle = new KontsultaIgorlea();
						igorle.receiveMessage(k);
						//kontsultak.remove(k);
					}
				}
				
				try {
					execute(fin);
				} catch (TelegramApiException e1) {
					e1.printStackTrace();
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
	
	private Geltokia strToGeltokia(String str) {
		for(int i = 0; i < geltokiakJaso.getLista().size(); i++) {
			if(geltokiakJaso.getLista().get(i).getIzena().equals(str)) return geltokiakJaso.getLista().get(i);
		}
		return null;
	}
	
	private SendMessage mezuaBidali(Message message, String str) {
		SendMessage msg = new SendMessage();
		msg.setChatId(message.getChatId());
		msg.setText(str);
		msg.setReplyMarkup(kontsultaGaldetegia(stage));
		return msg;
	}
	
	@SuppressWarnings("unused")
	private SendMessage inlineBidali(Message message, String str) {
		SendMessage msg = new SendMessage();
		msg.setText("");
		msg.setChatId(message.getChatId());
		msg.setReplyMarkup(atzeraBotoia());
		return msg;
	}
	
	private InlineKeyboardMarkup atzeraBotoia() {
		InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
		List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
		List<InlineKeyboardButton> arg0 = new ArrayList<>();
		InlineKeyboardButton button = new InlineKeyboardButton("Atzera");
		button.setCallbackData("Atzera");
		arg0.add(button);
		keyboard.add(arg0);
		inlineKeyboardMarkup.setKeyboard(keyboard);
		
		return inlineKeyboardMarkup;		
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
        keyboard.add(erramintaBotoiak());
        	break;
        case 2:str = arrayGeltokiak;
        keyboard.add(erramintaBotoiak());
    		break;
        case 3: 
        case 4: str = arrayOrduak;
        	return ordutegiPanela(str);
        }
        

		for(String geltokia: str) {
			KeyboardRow keyboardRow = new KeyboardRow();
			keyboardRow.add(geltokia);
			keyboard.add(keyboardRow);
		}
		replyKeyboardMarkup.setKeyboard(keyboard);
		return replyKeyboardMarkup;
	}

	private ReplyKeyboardMarkup ordutegiPanela(String str[]) {
		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);		
        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(erramintaBotoiak());
        
        for(int i = 0; i < str.length; i+=2){
        	KeyboardRow keyboardRow = new KeyboardRow();
        	keyboardRow.add(str[i]);
        	keyboardRow.add(str[1+i]);
        	keyboard.add(keyboardRow);
        }
        replyKeyboardMarkup.setKeyboard(keyboard);
		return replyKeyboardMarkup;
	}
	
	private KeyboardRow erramintaBotoiak() {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(atzera[0]);
        keyboardRow.add(atzera[1]);
		return keyboardRow;
	}
}
