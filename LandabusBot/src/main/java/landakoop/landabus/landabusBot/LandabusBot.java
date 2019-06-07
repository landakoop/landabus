package landakoop.landabus.landabusBot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
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
	static final String balidazioakHuts = "Ez dizut ulertu, erabili teklatuko botoiak";
	static final String amaieraMezua = "Eskaera bidali da. \n"+"Sakatu <b>HASIERA</b> hasierara itzultzeko.";
	static final String[] atzera = {"ZUZENDU","HASIERA"};
	static final String[] onartu = {"ESKAERA BIDALI"};
	
	String[] arrayGeltokiak;
	GeltokiakJaso geltokiakJaso;
	List<Geltokia> geltokiak;
	Map<String,Integer> hilabeteMap;
	BidiMap<String,Integer> orduakMap;
	
	
    @Autowired
    private TaskExecutor taskExecutor;
	ConcurrentHashMap<Long,Kontsulta> eskaerak;
	
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
		eskaerak = new ConcurrentHashMap<>();
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
			Message mezua = update.getMessage();
			taskExecutor.execute(new Eskaera(mezua));
		}
	}
	
	public class Eskaera implements Runnable {
		Message mezua;
		Long chatID;
		Kontsulta kontsulta;
		String mezuaTxt;
		public Eskaera(Message mezua) {
			this.mezua = mezua;
			this.chatID = mezua.getChatId();
			this.mezuaTxt = mezua.getText();
		}
		@Override
		public void run() {
			kontsulta = eskaerak.get(chatID);
			if(kontsulta == null) {
			    kontsulta = new Kontsulta();
				eskaerak.put(chatID, kontsulta);
			}
			synchronized(kontsulta) {
				if(!inputBalidazioa(mezuaTxt,kontsulta.getStage())) {
					try {
						execute(mezuaBidali(mezua,balidazioakHuts, kontsulta.getStage()));
					} catch (TelegramApiException e) {
						logger.error("Errorea mezua bidaltzean.Id={}, Stage={}, Ex={}",chatID,kontsulta.getStage(),e.getClass());
					}
					return;
				}
				System.out.println(mezuaTxt);
				
				if(mezuaTxt.equals("/start")) kontsulta.setStage(Stage.HASIERA);
				else if(mezuaTxt.equals("ZUZENDU")) kontsulta.previousStage();
				else if(mezuaTxt.equals("HASIERA")) kontsulta.setStage(Stage.HASIERA);
				
				try {
					switch(kontsulta.getStage()) {
					case HASIERA: 
						kontsulta.nextStage();
						execute(mezuaBidali(mezua,aurkezpena, kontsulta.getStage()));
						logger.info("Hasiera orria. id={}",chatID);
						break;
					case KONTSULTATU:
						if(mezuaTxt.equals("KONTSULTATU")){
							kontsulta.nextStage();
							execute(mezuaBidali(mezua,egunaGaldera, kontsulta.getStage()));
							logger.info("Kontsultako lehenengo galdetegia (egutegia) bidalita \"{}\" erabiltzaileari", mezua.getChatId());	
						}
						break;
					case EGUTEGIA: 
						if(!mezuaTxt.equals(atzera[0]))
							kontsulta.setData(strToDate(mezuaTxt));
						kontsulta.nextStage();
						execute(mezuaBidali(mezua,irteeraGaldera,kontsulta.getStage()));
						logger.info("Kontsultako bigarren galdetegia (irteera geltokia) bidalita \"{}\" erabiltzaileari", mezua.getChatId());
						break;
					case GELTOKIA_IRTEERA:
						if(!mezuaTxt.equals(atzera[0]))
							kontsulta.setIrteera(strToGeltokia(mezuaTxt));
						kontsulta.nextStage();
						execute(mezuaBidali(mezua,helmugaGaldera,kontsulta.getStage()));
						logger.info("Kontsultako hirugarren galdetegia (helmuga geltokia) bidalita \"{}\" erabiltzaileari", mezua.getChatId());
						break;
					case GELTOKIA_HELMUGA: 
						if(!mezuaTxt.equals(atzera[0]))
							kontsulta.setHelmuga(strToGeltokia(mezuaTxt));
						kontsulta.nextStage();
						execute(mezuaBidali(mezua,irteeraOrduGaldera,kontsulta.getStage()));
						logger.info("Kontsultako laugarren galdetegia (irteera ordua) bidalita \"{}\" erabiltzaileari", mezua.getChatId());
						break;
					case ORDUA_IRTEERA:
						if(!mezuaTxt.equals(atzera[0]))
							kontsulta.setIrteeraOrdua(orduakMap.get(mezuaTxt));
						kontsulta.nextStage();
						execute(mezuaBidali(mezua,helmugaOrduGaldera,kontsulta.getStage()));
						logger.info("Kontsultako bostgarren galdetegia (irteera helmuga) bidalita \"{}\" erabiltzaileari", mezua.getChatId());
						break;
					case ORDUA_HELMUGA:
						if(!mezuaTxt.equals(atzera[0]))
							kontsulta.setHelmugaOrdua(orduakMap.get(mezuaTxt));
						kontsulta.nextStage();
						execute(mezuaBidali(mezua,kontsulta.toString(),kontsulta.getStage()));
						logger.info("Kontsultaren konfirmazioa bidalita \"{}\" erabiltzaileari", mezua.getChatId());
						break;
					case KONFIRMAZIOA:
						if(mezuaTxt.equals("ESKAERA BIDALI")) {
							KontsultaIgorlea igorle = new KontsultaIgorlea();
							igorle.receiveMessage(kontsulta);
							kontsulta.nextStage();
							execute(mezuaBidali(mezua,amaieraMezua,kontsulta.getStage()));
						}
						break;
					default: 
						SendMessage response = new SendMessage();
						Long chatId = mezua.getChatId();
						response.setChatId(chatId);
						String text = mezuaTxt;
						response.setText(text);
						execute(response);
						logger.info("Stage ezezaguna \"{}\" , nori: {}", text, chatId);
						break;
					}
				}catch (TelegramApiException e) {
					logger.error("Errorea mezua bidaltzean.Id={}, Stage={}, Ex={}",chatID,kontsulta.getStage(),e.getClass());
				}
			}		
		}
	}
	
	public boolean inputBalidazioa(String mezua, Stage stage) {
		if(mezua.matches("ZUZENDU|HASIERA|/start")) return true;
		switch(stage) {
		case EGUTEGIA:
			return Arrays.asList(hurrengoEgunak()).contains(mezua);
		case GELTOKIA_IRTEERA:
		case GELTOKIA_HELMUGA:
			return Arrays.asList(arrayGeltokiak).contains(mezua);
		case ORDUA_IRTEERA:
		case ORDUA_HELMUGA:
			return mezua.matches("[0-9][0-9]:[0-9][0-9]");
		case KONTSULTATU:
			return mezua.equals("KONTSULTATU");
		case KONFIRMAZIOA:
			return mezua.equals("ESKAERA BIDALI");
		default:
			break;
		}
		return true;
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
	
	private SendMessage mezuaBidali(Message mezua, String str, Stage stage) {
		SendMessage msg = new SendMessage();
		msg.setChatId(mezua.getChatId());
		msg.setText(str);
		msg.setReplyMarkup(kontsultaGaldetegia(stage));
		msg.enableHtml(true);
		msg.setParseMode("HTML");
		return msg;
	}
	
	private ReplyKeyboardMarkup kontsultaGaldetegia(Stage stage) {
		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);		
        List<KeyboardRow> keyboard = new ArrayList<>();
        String str[] = null;
        
        switch(stage) {
        case HASIERA: 
        case KONTSULTATU:
        	str = hasiera;
         	break;
        case EGUTEGIA: 
        	str = hurrengoEgunak();
    		return egutegiPanela(str);
        case GELTOKIA_IRTEERA: 
        case GELTOKIA_HELMUGA:
        	str = arrayGeltokiak;
        	keyboard.add(erramintaBotoiak());
    		break;
        case ORDUA_HELMUGA: 
        case ORDUA_IRTEERA: 
        	str = arrayOrduak;
        	return ordutegiPanela(str);
        case KONFIRMAZIOA: 
        	str = onartu;
        	keyboard.add(erramintaBotoiak());
        	break;
        case AMAIERA:
        	return amaieraPanela();
        }
		for(String geltokia: str) {
			KeyboardRow keyboardRow = new KeyboardRow();
			keyboardRow.add(geltokia);
			keyboard.add(keyboardRow);
		}
		replyKeyboardMarkup.setKeyboard(keyboard);
		return replyKeyboardMarkup;
	}

	private static ReplyKeyboardMarkup ordutegiPanela(String str[]) {
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
	
	private static ReplyKeyboardMarkup amaieraPanela() {
		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);		
        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(erramintaBotoiak());
        replyKeyboardMarkup.setKeyboard(keyboard);
		return replyKeyboardMarkup;
	}
	
	private static ReplyKeyboardMarkup egutegiPanela(String str[]) {
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
	
	private static KeyboardRow erramintaBotoiak() {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(atzera[0]);
        keyboardRow.add(atzera[1]);
		return keyboardRow;
	}
	
	private static String [] hurrengoEgunak() {
		Calendar calendar = new GregorianCalendar();
		String [] str = new String[8];
		for(int i = 0; i < 8; i++) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			str[i] = ""+hilabeteak[calendar.get(Calendar.MONTH)]+" "+calendar.get(Calendar.DAY_OF_MONTH);
		}
		return str;
	}
	
	public static int strOrduaToInt(String orduaStr) {
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