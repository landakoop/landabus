package landakoop.landabus.mainapp.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import landakoop.landabus.mainapp.dao.AutobusGeldialdiaDao;
import landakoop.landabus.mainapp.dao.GeltokiaDao;
import landakoop.landabus.mainapp.dao.IbilbideaDao;
import landakoop.landabus.mainapp.model.AurrekoGeltokia;
import landakoop.landabus.mainapp.model.GeldialdiEkintza;
import landakoop.landabus.mainapp.model.Geltokia;

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
			for(GeldialdiEkintza ekintza : ekintzak) {
				out.print(ekintza.getKopurua());
				List<AurrekoGeltokia> geltokiak = autobusGeldialdiaDao.getAurrekoGeltokiak(ekintza.getLinea(), geltokiaID);
				for(AurrekoGeltokia geltokia:geltokiak) {
					out.print(","+ geltokia.getPasatu());
				}
				out.println();
			}
		}
		
		public void idatziBurua(PrintWriter out) {
			out.print("kopurua");
			for(int i = 1; i <= geltokiaDao.count();i++) {
				if(i!=geltokiaID) out.print(","+i);
			}
			out.println();
		}
	}
	
	/*@GetMapping("dataset")
	public List<Dataset> getDataset(){
		List<Dataset> dataset=new ArrayList<>();
		List<Ibilbidea> ibilbideak=(List<Ibilbidea>) ibilbideaDao.findAll();
		
		
		for(int i=0;i<ibilbideak.size();i++) {
			Set<AutobusGeldialdia> lista=ibilbideak.get(i).getAutobusGeldialdia();
			Dataset d=new Dataset();
			List<Geltokia> pasatutakoGeltokiak=new ArrayList<>();
			int pertsonaKop=0, maxPertsona=0;
			
			d.setEguraldia(ibilbideak.get(i).getEguraldia());
			System.out.println(lista.size());
			for(AutobusGeldialdia l:lista) {
				if(l.getEkintza().equals("igo")) pertsonaKop++;
				else pertsonaKop--;
				if(pertsonaKop>maxPertsona) maxPertsona=pertsonaKop;
				if(!pasatutakoGeltokiak.contains(l.getGeltokia())) pasatutakoGeltokiak.add(l.getGeltokia());
			}
			d.setPertsonaKopurua(maxPertsona);
			System.out.println("*****************");
			System.out.println(pasatutakoGeltokiak.size()+"*********");
			for(Geltokia g:pasatutakoGeltokiak) {
				konprobatuGeltokiak(d,g);
			}
			dataset.add(d);
		}
		
		return dataset;
	}

	private void konprobatuGeltokiak(Dataset d, Geltokia g) {
		System.out.println(String.valueOf(g.getId()));
		switch(String.valueOf(g.getId())) {
		case "1": d.setGeltokia1(true); break; 
		case "2": d.setGeltokia2(true); break; 
		case "3": d.setGeltokia3(true); break; 
		case "4": d.setGeltokia4(true); break; 
		case "5": d.setGeltokia5(true); break; 
		case "6": d.setGeltokia6(true); break; 
		case "7": d.setGeltokia7(true); break; 
		case "8": d.setGeltokia8(true); break; 
		}
	}*/


}
