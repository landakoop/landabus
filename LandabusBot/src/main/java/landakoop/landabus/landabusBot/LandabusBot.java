package landakoop.landabus.landabusBot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
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

import landakoop.landabus.beans.Geltokia;
import landakoop.landabus.beans.Kontsulta;

@Component
public class LandabusBot extends TelegramLongPollingBot{
	private static final Logger logger = LoggerFactory.getLogger(LandabusBot.class);
	static final String[] hilabeteak = {"Urtarrilak","Otsailak","Martxoak","Apirilak","Maiatzak","Ekainak","Uztailak","Abuztuak","Irailak","Urriak","Azaroak","Abenduak"};
	static final String[] hasiera = {"KONTSULTATU"};
	static final String[] arrayOrduak = {"05:00","05:30","06:00","06:30","07:00","07:30","08:00","08:30","09:00","09:30","10:00",
			"10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30",
			"18:00","18:30","19:00","19:30","20:00","20:30","21:00","21:30","22:00","22:30","23:00","23:30","00:00","00:30"};
	static final String aurkezpena = "Ordutegien kontsulta edo eskakizun bat egiteko sakatu: <b>KONTSULTATU</b>";
	static final String egunaGaldera = "Hautatu autobusa hartzeko eguna: ";
	static final String irteeraGaldera = "Non hartu nahi duzu autobusa?";
	static final String helmugaGaldera = "Nora heldu nahi duzu autobusean?";
	static final String irteeraOrduGaldera = "Ze ordutatik aurrera irten zaitezke?";
	static final String helmugaOrduGaldera = "Ze ordutarako egon behar zara helmugan?";
	static final String amaieraMezua = "Eskaera bidali da. \n"+"Sakatu <b>HASIERA</b> hasierara itzultzeko.";
	static final String[] atzera = {"ZUZENDU","HASIERA"};
	static final String[] onartu = {"ESKAERA BIDALI"};
	
	String[] arrayGeltokiak;
	GeltokiakJaso geltokiakJaso;
	List<Kontsulta> kontsultak;
	List<Geltokia> geltokiak;
	Map<String,Integer> hilabeteMap;
	BidiMap<String,Integer> orduakMap;
	
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
	public BidiMap<String, Integer> getOrduakMap() {
		return orduakMap;
	}
	
	public LandabusBot() {
		kontsultak = new ArrayList<>();
		geltokiak = new ArrayList<>();
		geltokiakJaso = new GeltokiakJaso();
		geltokiak = geltokiakJaso.geltokiakJaso();
		arrayGeltokiak = new String[geltokiak.size()];
		hilabeteMap = new HashMap<>();
		orduakMap = new DualHashBidiMap<>();
		varInit();

	}
	
	private void varInit() {
		for(int i = 0; i < hilabeteak.length; i++) {
			hilabeteMap.put(hilabeteak[i],i+1);
		}
		for(int i = 0; i < arrayOrduak.length; i++) {
			orduakMap.put(arrayOrduak[i], strOrduaToInt(arrayOrduak[i]));
		}
		for(int i = 0; i < geltokiak.size(); i++) {
			arrayGeltokiak[i] = geltokiak.get(i).getIzena();
		}
	}

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage()) {
			Message message = update.getMessage();
			boolean berria = true;
			int index = 0;
			
			for(Kontsulta k: kontsultak) {
				if(k.getChatId().equals(message.getChatId())) {
					index = kontsultak.indexOf(k);
					berria = false;
				}
			}
			
			if(berria) {
				Kontsulta kontsulta = new Kontsulta();
				kontsulta.setChatId(message.getChatId());
				kontsultak.add(kontsulta);
				index = kontsultak.indexOf(kontsulta);
			}
			
			if(message.getText().equals("/start")) kontsultak.get(index).setStage(0);
			else if(message.getText().equals("KONTSULTATU")) kontsultak.get(index).setStage(1);
			else if(message.getText().equals("ZUZENDU")) kontsultak.get(index).setStage(kontsultak.get(index).getStage() - 2);
			else if(message.getText().equals("HASIERA")) kontsultak.get(index).setStage(0);
			else if(message.getText().equals("ESKAERA BIDALI")) kontsultak.get(index).setStage(7);
			
			
			switch(kontsultak.get(index).getStage()) {
			case 0: try {
					execute(mezuaBidali(message,aurkezpena, index));
					logger.info("Hasiera orria");
				} catch (TelegramApiException e2) {
					logger.error("Errorea hasierako orria bistaratzean");
					e2.printStackTrace();
				}
				break;
			case 1: try {
				execute(mezuaBidali(message,egunaGaldera, index));
				logger.info("Kontsultako lehenengo galdetegia bidalita \"{}\" erabiltzaileari", message.getChatId());
				kontsultak.get(index).setStage(2);
			} catch (TelegramApiException e1) {
				logger.error("Failed to send message to {} due to error: {}", message.getChatId(), e1.getMessage());
			}

			break;
			case 2: 
				if(!message.getText().equals(atzera[0])) kontsultak.get(index).setData(strToDate(message.getText()));
				try {
					execute(mezuaBidali(message,irteeraGaldera,index));
					logger.info("Kontsultako bigarren galdetegia bidalita \"{}\" erabiltzaileari", message.getChatId());
					kontsultak.get(index).setStage(3);
				} catch (TelegramApiException e1) {
					logger.error("Failed to send message to {} due to error: {}", message.getChatId(), e1.getMessage());
				}
				break;
			case 3: 
				if(!message.getText().equals(atzera[0])) kontsultak.get(index).setIrteera(strToGeltokia(message.getText()));
				try {
					execute(mezuaBidali(message,helmugaGaldera,index));
					logger.info("Kontsultako hirugarren galdetegia bidalita \"{}\" erabiltzaileari", message.getChatId());
					kontsultak.get(index).setStage(4);
				} catch (TelegramApiException e1) {
					logger.error("Failed to send message to {} due to error: {}", message.getChatId(), e1.getMessage());
				}
				break;
			case 4: 
				if(!message.getText().equals(atzera[0])) kontsultak.get(index).setHelmuga(strToGeltokia(message.getText()));
				try {
					execute(mezuaBidali(message,irteeraOrduGaldera,index));
					logger.info("Kontsultako laugarren galdetegia bidalita \"{}\" erabiltzaileari", message.getChatId());
					kontsultak.get(index).setStage(5);
				} catch (TelegramApiException e1) {
					logger.error("Failed to send message to {} due to error: {}", message.getChatId(), e1.getMessage());
				}
				break;
			case 5: 
				if(!message.getText().equals(atzera[0])) kontsultak.get(index).setIrteeraOrdua(orduakMap.get(message.getText()));
				try {
					execute(mezuaBidali(message,helmugaOrduGaldera,index));
					logger.info("Kontsultako bostgarren galdetegia bidalita \"{}\" erabiltzaileari", message.getChatId());
					kontsultak.get(index).setStage(6);
				} catch (TelegramApiException e1) {
					logger.error("Failed to send message to {} due to error: {}", message.getChatId(), e1.getMessage());
				}
				break;
			case 6:
				if(!message.getText().equals(atzera[0])) kontsultak.get(index).setHelmugaOrdua(orduakMap.get(message.getText()));
				try {
					execute(mezuaBidali(message,kontsultak.get(index).toString(),index));
				} catch (TelegramApiException e1) {
					e1.printStackTrace();
				}
				break;
			case 7:
				KontsultaIgorlea igorle = new KontsultaIgorlea();
				igorle.receiveMessage(kontsultak.get(index));
				try {
					execute(mezuaBidali(message,amaieraMezua,index));
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
	
	@SuppressWarnings("deprecation")
	private Date strToDate(String text) {
		Date data = new Date();
		String [] str = text.split(" ");
		int a = hilabeteMap.get(str[0]);
		data.setMonth(a);
		data.setDate(Integer.valueOf(str[1]));
		return data;
	}

	private Geltokia strToGeltokia(String str) {
		for(int i = 0; i < geltokiakJaso.getLista().size(); i++) {
			if(geltokiakJaso.getLista().get(i).getIzena().equals(str)) return geltokiakJaso.getLista().get(i);
		}
		return null;
	}
	
	private SendMessage mezuaBidali(Message message, String str, int index) {
		SendMessage msg = new SendMessage();
		msg.setChatId(message.getChatId());
		msg.setText(str);
		msg.setReplyMarkup(kontsultaGaldetegia(kontsultak.get(index).getStage()));
		msg.enableHtml(true);
		msg.setParseMode("HTML");
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
        case 1: str = hurrengoEgunak();
    		return egutegiPanela(str);
        case 2: str = arrayGeltokiak;
        keyboard.add(erramintaBotoiak());
        	break;
        case 3:str = arrayGeltokiak;
        keyboard.add(erramintaBotoiak());
    		break;
        case 4: 
        case 5: str = arrayOrduak;
        	return ordutegiPanela(str);
        case 6: str = onartu;
        	keyboard.add(erramintaBotoiak());
        	break;
        case 7: return amaieraPanela();
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
	
	private ReplyKeyboardMarkup amaieraPanela() {
		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);		
        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(erramintaBotoiak());
        replyKeyboardMarkup.setKeyboard(keyboard);
		return replyKeyboardMarkup;
	}
	
	private ReplyKeyboardMarkup egutegiPanela(String str[]) {
		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);		
        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(erramintaBotoiak());
        for(int i = 0; i < str.length; i+=4){
        	KeyboardRow keyboardRow = new KeyboardRow();
        	for(int t = i; t < i+4; t++) keyboardRow.add(str[t]);
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
	
	private String [] hurrengoEgunak() {
		Calendar calendar = new GregorianCalendar();
		String [] str = new String[8];
		for(int i = 0; i < 8; i++) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			str[i] = ""+hilabeteak[calendar.get(Calendar.MONTH)]+" "+calendar.get(Calendar.DAY_OF_MONTH);
		}
		return str;
	}
	
	public int strOrduaToInt(String orduaStr) {
		int orduaMinututan;
		int ordua, minutuak;
		String str[] = new String[2];
		str = orduaStr.split(":");
		ordua = Integer.parseUnsignedInt(str[0]);
		minutuak = Integer.parseUnsignedInt(str[1]);
		orduaMinututan = (ordua*60) + minutuak;
		return orduaMinututan;
	}
}


