package landakoop.landabus.mainapp.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import landakoop.landabus.mainapp.dao.AutobusGeldialdiaDao;
import landakoop.landabus.mainapp.model.AutobusGeldialdia;
import landakoop.landabus.mainapp.soap.AutobusGeldialdiaRequest;
import landakoop.landabus.mainapp.soap.AutobusGeldialdiaResponse;
import landakoop.landabus.mainapp.soap.AutobusGeldialdiaSoap;

@Endpoint
public class DatuJasotzaileaSoapController {	
	@Autowired
	AutobusGeldialdiaDao autobusGeldialdiaDao;
	
	@PayloadRoot(namespace = "http://mainapp.landabus.landakoop/soap", localPart = "autobusGeldialdiaRequest")
	@ResponsePayload
	public AutobusGeldialdiaResponse autobusGeldialdiaRequest (@RequestPayload AutobusGeldialdiaRequest request) {		
		AutobusGeldialdia autobusGeldialdia = new AutobusGeldialdia();
		
		autobusGeldialdia.setErabiltzailea(request.getErabiltzailea());
		autobusGeldialdia.setIbilbidea(request.getIbilbidea());
		autobusGeldialdia.setGeltokia(request.getGeltokia());
		autobusGeldialdia.setEkintza(request.getEkintza());
		autobusGeldialdia.setNoiz(new Date(request.getNoiz()));
		System.out.println(autobusGeldialdia);
		autobusGeldialdiaDao.save(autobusGeldialdia);
		
		AutobusGeldialdiaResponse response = new AutobusGeldialdiaResponse();
		AutobusGeldialdiaSoap autobusGeldialdiaSoap = new AutobusGeldialdiaSoap();
		
		autobusGeldialdiaSoap.setErabiltzailea(request.getErabiltzailea());
		autobusGeldialdiaSoap.setIbilbidea(request.getIbilbidea());
		autobusGeldialdiaSoap.setGeltokia(request.getGeltokia());
		autobusGeldialdiaSoap.setEkintza(request.getEkintza());
		autobusGeldialdiaSoap.setNoiz(request.getNoiz());
		response.setAutobusGeldialdiaSoap(autobusGeldialdiaSoap);
		
		return response;
	}
}
