package landakoop.landabus.mainapp.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import landakoop.landabus.mainapp.dao.AutobusGeldialdiaDao;
import landakoop.landabus.mainapp.dao.EskaeraDao;
import landakoop.landabus.mainapp.dao.GeltokiaDao;
import landakoop.landabus.mainapp.dao.IbilbideaDao;
import landakoop.landabus.mainapp.dao.LineaDao;
import landakoop.landabus.mainapp.dao.OrdutegiaDao;
import landakoop.landabus.mainapp.logic.Sender;
import landakoop.landabus.mainapp.logic.Util;
import landakoop.landabus.mainapp.model.AurrekoGeltokia;
import landakoop.landabus.mainapp.model.BilaketaEmaitza;
import landakoop.landabus.mainapp.model.Emaitza;
import landakoop.landabus.mainapp.model.Eskaera;
import landakoop.landabus.mainapp.model.GeldialdiEkintza;
import landakoop.landabus.mainapp.model.Geltokia;
import landakoop.landabus.mainapp.model.Ibilbidea;
import landakoop.landabus.mainapp.model.Linea;
import landakoop.landabus.mainapp.model.Ordutegia;

@RestController
@RequestMapping("/api/ia")
public class IAController {
	@Value("${ia.path}")
	private String IApath;
	
	/*@Value("${COM_PROTOCOL}://${TELEGRAM_HOST}:${TELEGRAM_PORT}")
	String url;*/
	
	@Autowired
	private Logger logger;
	
    @Autowired
    private TaskExecutor taskExecutor;
    
	@Autowired
	AutobusGeldialdiaDao autobusGeldialdiaDao;
	@Autowired
	IbilbideaDao ibilbideaDao;
	@Autowired
	GeltokiaDao geltokiaDao;
	@Autowired
	EskaeraDao eskaeraDao;
	@Autowired
	OrdutegiaDao ordutegiaDao;
	@Autowired
	LineaDao lineaDao;
	
	@Autowired
	Sender sender;
	
	@Autowired
	Util util;
	
	private static String[] ekintzak = {"igo","jaitsi"};
	
	@SuppressWarnings("deprecation")
	@GetMapping("run")
	public void runIA(){
		Date date = new Date();
		for(Ibilbidea ibilbidea : ibilbideaDao.findAll()) {
			Map<String,String> geltokiak = mapaHutsa();
			int nGeltokiak = 0;
			for(Geltokia g : ibilbidea.getOrdutegia().getLinea().getGeltokiak()) {
				for(String ekintza : ekintzak) {
					Map<String,String> params = new HashMap<>();
					params.put("geltokia",String.valueOf(g.getId()));
					params.put("ekintza",ekintza);
					params.put("ordua", String.valueOf(ibilbidea.getOrdutegia().getIrteeraOrdua()));
					DateFormat format=new SimpleDateFormat("EEEE"); 
					params.put("eguna",String.valueOf(format.format(date)));
					params.put("hilabetea",String.valueOf(date.getMonth()));
					params.put("eguraldia",ibilbidea.getEguraldia());
					params.putAll(geltokiak);
					Integer obj =  sender.makeGet("http://ml:8000/predict", params);
					if(obj != null) {
						logger.error(obj.getClass()+"********************************");
						geltokiak.put("x" + ++nGeltokiak, "true");
						//logger.error("Rek erantzuna NULL. params={}",params);
					}
				}
			}
		}
	}
	
	@PostMapping(path = "bilaketaEmaitza", consumes = "application/json")
	public void postFromJson(@RequestBody BilaketaEmaitza emaitza) {
		//Eskaerak lortu
		List<Eskaera> eskaerak = (List<Eskaera>) eskaeraDao.findAllById(emaitza.getEskaerak());
		//Linea sortu
		Linea linea = new Linea();
		linea.setIzena("Malgua- ");
		for(Geltokia gel : geltokiaDao.findAllById(emaitza.getLinea())) {
			linea.addGeltokia(gel);
		}
		linea=lineaDao.save(linea);
		// Ordutegia sortu
		Ordutegia ordutegia = new Ordutegia();
		ordutegia.setLinea(linea);
		ordutegia.setData(eskaerak.get(0).getData());
		ordutegia.setIrteeraOrdua(emaitza.getIrteeraOrdua());
		ordutegia.setFinkoa(false);
	    ordutegia=ordutegiaDao.save(ordutegia);
		// Ibilbideak sortu
		Ibilbidea ibilbidea = new Ibilbidea();
		ibilbidea.setOrdutegia(ordutegia);
		ibilbidea=ibilbideaDao.save(ibilbidea);
		logger.info("Malgua sortu da: linea = {}, ibilbidea= {}, ordutegia = {}",linea.getId(),ibilbidea.getId(),ordutegia.getId());
		//Eskaerak onartu
		List<Long> onartuenIDak = new ArrayList<>();
		for(Eskaera e : eskaerak) {
			e.setOnartua(true);
			e.setIbilbidea(ibilbidea);
			eskaeraDao.save(e);
			logger.info("Eskaera onartu da: id={}",e.getId());
			onartuenIDak.add(e.getChatId());
		}
		
		Util util = new Util();
		Emaitza bidalketa = new Emaitza();
		bidalketa.setListId(onartuenIDak);
		bidalketa.setLinea(util.getLinea(ibilbidea.getId()));

		try {
			sendPost(bidalketa);
		} catch (Exception e1) {
			logger.error("Ezin izan dira onartutako lineak bidali");
		}
	}
	
	public void sendPost(Emaitza emaitza) throws URISyntaxException{
		RestTemplate restTemplate = new RestTemplate();
		//System.out.println(url);
		URI uri = new URI("http://telegram-bot:8082"+"/telegram/emaitza/onartuakPostFromJson");		
		System.out.println(uri.toString());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);			
			
		HttpEntity<Emaitza> requestBody = new HttpEntity<>(emaitza, headers);
		restTemplate.postForEntity(uri, requestBody, Emaitza.class);		
	}
	
	public Map<String,String> mapaHutsa(){
		Map<String,String> params = new HashMap<>();
		for(int i = 1; i < geltokiaDao.count();i++) {
			params.put("x"+i, "false");
		}
		return params;
	}

	@GetMapping("sortuCSV")
	public int sortuCSV() {	
		List<Geltokia> geltokiak = geltokiaDao.findAll();
		for(Geltokia g:geltokiak) {
			taskExecutor.execute(new CSVsortzailea(g.getId(),"igo"));
			taskExecutor.execute(new CSVsortzailea(g.getId(),"jaitsi"));
		}
		return geltokiak.size();
	}
	
	@GetMapping("sortuCSVsinkronoa")
	public int sortuCSVSinkronoa() {	
		long start = System.currentTimeMillis();
		List<Geltokia> geltokiak = geltokiaDao.findAll();
		for(Geltokia g:geltokiak) {
			CSVsortzailea sor1 = new CSVsortzailea(g.getId(),"igo");
			sor1.run();
			CSVsortzailea sor2 = new CSVsortzailea(g.getId(),"jaitsi");
			sor2.run();
		}
		long end = System.currentTimeMillis();
		logger.info("CSVak sinkronoki sortzeko exekuzio denbora: {}",(end-start));
		return geltokiak.size();
	}
	
	public class CSVsortzailea implements Runnable {
		long geltokiaID;
		String ekintza;
		
		public CSVsortzailea(long geltokiaID,String ekintza) {
			this.geltokiaID = geltokiaID;
			this.ekintza = ekintza;
		}
		
		@Override
		public void run() {
			String fitxategia = IApath + geltokiaID + "_" + ekintza + ".csv";
			logger.info("csv SORTUKO da: {}",fitxategia);
			Optional<Geltokia> geltokiaOpt = geltokiaDao.findById(geltokiaID);
			if(geltokiaOpt.isPresent()) {
				try(PrintWriter out=new PrintWriter(new FileWriter(fitxategia))){
					idatziBurua(out);
					idatziGorputza(out);
					logger.info("csv SORTU da: geltokiaID={} ekintza={}",geltokiaID,ekintza);
				} catch (IOException e) {
					logger.error("csv IDAZTE ERROREA: exception={} geltokiaID={} ekintza={}",e.getClass(),geltokiaID,ekintza);
				}
			}
		}
		
		@SuppressWarnings("deprecation")
		public void idatziGorputza(PrintWriter out) {
			List<GeldialdiEkintza> ekintzak = autobusGeldialdiaDao.getGeldialdiaEkintzak(ekintza,geltokiaID);
			for(GeldialdiEkintza ek : ekintzak) {
				List<AurrekoGeltokia> geltokiak;
				if(ekintza == "igo")
					geltokiak = autobusGeldialdiaDao.getAurrekoGeltokiakIgo(ek.getLinea(), geltokiaID);
				else
					geltokiak = autobusGeldialdiaDao.getAurrekoGeltokiakJaitsi(ek.getLinea(), geltokiaID);
				for(AurrekoGeltokia geltokia:geltokiak) {
					out.print(geltokia.getPasatu()+",");
				}
				Date d=ek.getData();
				out.print(d.getHours()+",");
				DateFormat format=new SimpleDateFormat("EEEE"); 
				out.print(format.format(d)+",");
				out.print(d.getMonth()+",");
				out.print(ek.getEguraldia()+",");
				out.print(ek.getKopurua());
				out.println();
			}
		}
		
		public void idatziBurua(PrintWriter out) {
			for(int i = 1; i <= geltokiaDao.count();i++) {
				if(i!=geltokiaID) out.print(i+",");
			}
			out.print("ordua,eguna,hilabetea,eguraldia,kopurua");
			out.println();
		}
	}

}
