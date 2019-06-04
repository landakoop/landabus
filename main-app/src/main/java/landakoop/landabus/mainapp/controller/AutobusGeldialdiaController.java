package landakoop.landabus.mainapp.controller;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import landakoop.landabus.mainapp.dao.AutobusGeldialdiaDao;
import landakoop.landabus.mainapp.dao.GeltokiaDao;
import landakoop.landabus.mainapp.dao.IbilbideaDao;
import landakoop.landabus.mainapp.model.AurrekoGeltokia;
import landakoop.landabus.mainapp.model.AutobusGeldialdia;
import landakoop.landabus.mainapp.model.Dataset;
import landakoop.landabus.mainapp.model.GeldialdiEkintza;
import landakoop.landabus.mainapp.model.Geltokia;
import landakoop.landabus.mainapp.model.Ibilbidea;

@RestController
@RequestMapping("/api/geldialdia")
public class AutobusGeldialdiaController {
	@Autowired
	AutobusGeldialdiaDao autobusGeldialdiaDao;
	@Autowired
	IbilbideaDao ibilbideaDao;
	@Autowired
	GeltokiaDao geltokiaDao;
	
	/*@GetMapping("proba")
	public List<GeldialdiEkintza> getProba() {
		return autobusGeldialdiaDao.foo("jaitsi",3);
	}
	
	@GetMapping("proba2")
	public List<AurrekoGeltokia> getProba2(){
		return autobusGeldialdiaDao.foo2(2,2);
	}*/
	
	@GetMapping("sortuCSV")
	public void sortuCSV() {
		List<Geltokia> geltokiak=geltokiaDao.findAll();
		for(Geltokia g:geltokiak) {
			try(PrintWriter out=new PrintWriter(new FileWriter(g.getId()+".csv"))){
				out.print("kopurua");
				for(int i = 1; i < geltokiak.size()+1;i++) {
					if(i!=g.getId()) out.print(","+i);
				}
				List<GeldialdiEkintza> ekintzak=autobusGeldialdiaDao.getGeldialdiaEkintzak("igo", g.getId());
				for(GeldialdiEkintza e:ekintzak) {
					List<AurrekoGeltokia> aurrekoak=autobusGeldialdiaDao.getAurrekoGeltokiak(e.getLinea(), g.getId());
					out.println();
					out.print(e.getKopurua());
					for(AurrekoGeltokia a:aurrekoak) {
						out.print(","+a.getPasatu());
					}
				}
			}catch(Exception e){}
		}
	}
	
	@GetMapping("dataset")
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
		
	}


}
