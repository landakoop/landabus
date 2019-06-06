package landakoop.landabus.mainapp.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import landakoop.landabus.mainapp.dao.AutobusGeldialdiaDao;
import landakoop.landabus.mainapp.dao.GeltokiaDao;
import landakoop.landabus.mainapp.dao.IbilbideaDao;
import landakoop.landabus.mainapp.logic.Sender;
import landakoop.landabus.mainapp.model.AurrekoGeltokia;
import landakoop.landabus.mainapp.model.GeldialdiEkintza;
import landakoop.landabus.mainapp.model.Geltokia;
import landakoop.landabus.mainapp.model.Ibilbidea;

@RestController
@RequestMapping("/api/ia")
public class IAController {
	@Value("${ia.path}")
	private String IApath;
	
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
	
	@GetMapping("run")
	public void runIA(){
		Sender sender = new Sender();
		Date date = new Date();
		for(Ibilbidea ibilbidea : ibilbideaDao.findAll()) {
			Map<String,String> geltokiak = mapaHutsa();
			int nGeltokiak = 0;
			for(Geltokia g : ibilbidea.getOrdutegia().getLinea().getGeltokiak()) {
				Map<String,String> params = new HashMap<>();
				params.put("geltokia",String.valueOf(g.getId()));
				params.put("ekintza","igo");
				params.put("ordua", String.valueOf(ibilbidea.getOrdutegia().getIrteeraOrdua()));
				params.put("eguna",String.valueOf(date.getDay()));
				params.put("hilabete",String.valueOf(date.getMonth()));
				params.put("eguraldia",ibilbidea.getEguraldia());
				params.putAll(geltokiak);
				//sender.makeGet("http://172.17.21.79:8082/predict", params);
				geltokiak.put("x" + ++nGeltokiak, "true");
			}
		}
	}
	
	public Map<String,String> mapaHutsa(){
		Map<String,String> params = new HashMap<>();
		for(int i = 1; i < geltokiaDao.count();i++) {
			params.put("x"+i, "false");
		}
		return params;
	}
	
	
	@GetMapping("ibilbidea")
	public void sortuIbilbidea(@RequestParam(name="geltokiak", required=true) List<Long> geltokiak,
			                   @RequestParam(name="eskaerak", required=true) List<Long> eskaerak,
			                   @RequestParam(name="hasieraOrdua", required=true) int hasieraOrdua) {
		for(Long geltokiaID : geltokiak) {
			
		}
		
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
	
	
	
	/*public void sendPost(AutobusGeldialdia autobusGeldialdia) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		URI uri = new URI(url+"/api/datujasotzailea/postFromJson");		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);			
			
		HttpEntity<AutobusGeldialdia> requestBody = new HttpEntity<>(autobusGeldialdia, headers);
		restTemplate.postForEntity(uri, requestBody, AutobusGeldialdia.class);		
	}*/


}
