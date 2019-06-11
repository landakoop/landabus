$(document).ready(() => {
	function orduaFormatter(cell,formatterParams,onRendered) {		
		return orduaFormatua(cell.getValue());
	}
	
	function orduaFormatua (denbora) {
		ordua = parseInt(denbora/60);
		minutuak = parseInt(denbora % 60);
		
		if (ordua < 10) ordua = "0" + ordua;
		else if (ordua > 23) ordua = "00";
		if (minutuak < 10) minutuak = "0" + minutuak;
		
		return ordua + ":" + minutuak;
	}
	
	function orduaFormatuaAlderantziz (denbora) {
		orduakMinutuak = denbora.split(":");
		
		return parseInt(orduakMinutuak[0]*60) + parseInt(orduakMinutuak[1]);
	}
	
	function finkoaFormatter(cell,formatterParams,onRendered) {
		return finkoaFormatua(cell.getValue());
	}
	
	function finkoaFormatua(finkoa) {
		if (finkoa) return "Bai";
		else return "Ez";
	}
	
	function finkoaFormatuaAlderantziz(finkoa) {
		if (finkoa == "Bai") return true;
		else return false;
	}
	
	function data(data) {		
		return new Date(data);
	}
	
	const lineak = new Tabulator(".administrazioa-lineak__taula", {
	    ajaxURL: "/api/linea/",
	    ajaxConfig:"GET",
	    layout: "fitColumns",
	    tooltips: true,
	    addRowPos: "top",
	    pagination: "local",
	    paginationSize: 30,
	    height:"100%",
	    selectable:1,
	    dataLoaded:function(){
	        this.selectRow(1);	        
	    },
	    columns: [
	        {title: "ID", field: "id", align: "center", width: 50},
	        {title: "Izena", field: "izena"},
	    ],
	    rowClick:function(e, row){
	    	$("#administrazioa-toolbar__geltokiaInput").val(row.getData().izena);
	    	geltokiak.replaceData("/api/geltokia/list4?lineaID="+row.getData().id);
	    	aukeratutakoak.replaceData("/api/geltokia/list3?lineaID="+row.getData().id);
	    	ordutegiak.replaceData("/api/linea/ordutegiak?lineaID="+row.getData().id);
	    }
	});
	
	const geltokiak = new Tabulator(".administrazioa-geltokiak__taula", {
	    ajaxURL: "/api/geltokia/list4?lineaID=1",
	    ajaxConfig:"GET",
	    layout: "fitColumns",
	    tooltips: true,
	    addRowPos: "top",
	    pagination: "local",
	    paginationSize: 10,
	    height:"100%",
	    selectable:1,
	    dataLoaded:function(){
	        this.selectRow(1);
	    },
	    movableRows:true,
        movableRowsReceiver: "insert",
        movableRowsSender: "delete",
        movableRowsConnectedTables: ".administrazioa-aukeratutakoak__taula",
	    columns: [
	        {title: "ID", field: "geltokiaId", align: "center", width: 50},
	        {title: "Izena", field: "izena"},
	    ],
	    rowClick:function(e, row){
	    	aukeratutakoak.addRow(row.getData(),true);
        	row.delete();}
	});
	
	const aukeratutakoak = new Tabulator(".administrazioa-aukeratutakoak__taula", {
	    ajaxURL: "/api/geltokia/list3?lineaID=1",
	    ajaxConfig:"GET",
	    layout: "fitColumns",
	    tooltips: true,
	    addRowPos: "top",
	    pagination: "local",
	    paginationSize: 10,
	    height:"100%",
	    selectable:1,
	    dataLoaded:function(){
	        this.selectRow(1);
	    },
	    movableRows:true,
        movableRowsReceiver: "insert",
        movableRowsSender: "delete",
        movableRowsConnectedTables: ".administrazioa-geltokiak__taula",
        columns: [
	        {title: "ID", field: "geltokiaId", align: "center", width: 50},
	        {title: "Izena", field: "izena"},
	    ],
	    rowClick:function(e, row){
        	geltokiak.addRow(row.getData(),true);
        	row.delete();}
	});
	
	const ordutegiak = new Tabulator(".administrazioa-ordutegiak__taula", {
	    ajaxURL: "/api/linea/ordutegiak?lineaID=1",
	    ajaxConfig:"GET",
	    layout: "fitColumns",
	    tooltips: true,
	    addRowPos: "top",
	    pagination: "local",
	    paginationSize: 10,
	    height:"100%",
	    selectable:1,
	    dataLoaded:function(){
	        this.selectRow(1);
	    },
        columns: [
        	{title: "ID", field: "ordutegiaID", align: "center", width: 50},
	        {title: "Irt Ordua", field: "irteeraOrdua", formatter:orduaFormatter},
	        {title: "Hel ordua", field: "helmugaOrdua", formatter:orduaFormatter},
	        {title: "Eguna", field: "data"},
	        {title: "Finkoa", field: "finkoa", width: 90, formatter:finkoaFormatter},
	    ],
	    rowClick:function(e, row){
	    	$("#administrazioa-ordutegia__form-irtOrInput").val(orduaFormatua(row.getData().irteeraOrdua));
	    	$("#administrazioa-ordutegia__form-helOrInput").val(orduaFormatua(row.getData().helmugaOrdua));
	    	$("#administrazioa-ordutegia__form-egunaInput").val(row.getData().data);
	    	$("#administrazioa-ordutegia__form-finkoaInput").val(finkoaFormatua(row.getData().finkoa));
	    }
	});
	
	$("#administrazioa-toolbar__new").click(function(){
		lineaIzena = $("#administrazioa-toolbar__geltokiaInput").val();
		if (lineaIzena == "") return;
		
		$.ajax({
			url: '/api/linea/',
			type: 'PUT',
			contentType: "application/x-www-form-urlencoded",
			data: { "izena": lineaIzena },
			success: function(response) {
				lineaId = response;
				
				setTimeout(function(){
					lineak.replaceData("/api/linea/");
					geltokiak.replaceData("/api/geltokia/list4?lineaID=1");
			    	aukeratutakoak.replaceData("/api/geltokia/list3?lineaID=1");
			    	ordutegiak.replaceData("/api/linea/ordutegiak?lineaID=1");
				}, 500);
			}
		});		
	});
	
	$("#administrazioa-toolbar__change").click(function(){
		lineaId = lineak.getSelectedData()[0].id;
		lineaIzena = $("#administrazioa-toolbar__geltokiaInput").val();
		if (lineaIzena == "") return;
		
		$.ajax({
			  url: '/api/linea/',
			  type: 'PUT',
			  contentType: "application/x-www-form-urlencoded",
			  data: { "id": lineaId, "izena": lineaIzena }
		});
		
		lineakUrl = "?lineaID=" + lineaId;
		geltokiakUrl = "&geltokiak=";
		$.each(aukeratutakoak.getData(), function( index, value ) {
    		if(index != 0) geltokiakUrl = geltokiakUrl + ",";
    		geltokiakUrl = geltokiakUrl + value.geltokiaId;
		});
		url = "/api/linea/gehituGeltokiak" + lineakUrl + geltokiakUrl;
		console.log(url);
		$.post(url);
		
		setTimeout(function(){
			lineak.replaceData("/api/linea/");
			geltokiak.replaceData("/api/geltokia/list4?lineaID=1");
	    	aukeratutakoak.replaceData("/api/geltokia/list3?lineaID=1");
	    	ordutegiak.replaceData("/api/linea/ordutegiak?lineaID=1");
		}, 500);
		
	});
	
	$("#administrazioa-ordutegia__form--new").click(function(){
		lineaId = lineak.getSelectedData()[0].id;
		
		irteeraOrdua = orduaFormatuaAlderantziz($("#administrazioa-ordutegia__form-irtOrInput").val());
		helmugaOrdua = orduaFormatuaAlderantziz($("#administrazioa-ordutegia__form-helOrInput").val());
		eguna = $("#administrazioa-ordutegia__form-egunaInput").val();
		finkoa = finkoaFormatuaAlderantziz($("#administrazioa-ordutegia__form-finkoaInput").val());
		
		if (eguna == "")
			data = { "linea": lineaId, "irteeraOrdua": irteeraOrdua, "helmugaOrdua": helmugaOrdua,
			  "finkoa": finkoa };
		else {
			eguna = data(eguna);
			data = { "linea": lineaId, "irteeraOrdua": irteeraOrdua, "helmugaOrdua": helmugaOrdua,
				  "data": eguna, "finkoa": finkoa };
		}
		
		$.ajax({
			  url: '/api/ordutegia/',
			  type: 'PUT',
			  contentType: "application/x-www-form-urlencoded",
			  data: data
		});
		
		setTimeout(function(){
	    	ordutegiak.replaceData("/api/linea/ordutegiak?lineaID="+lineaId);
		}, 500);
	});
	
	$("#administrazioa-ordutegia__form--change").click(function(){
		lineaId = lineak.getSelectedData()[0].id;
		
		ordutegiaId = ordutegiak.getSelectedData()[0].ordutegiaID;
		irteeraOrdua = orduaFormatuaAlderantziz($("#administrazioa-ordutegia__form-irtOrInput").val());
		helmugaOrdua = orduaFormatuaAlderantziz($("#administrazioa-ordutegia__form-helOrInput").val());
		eguna = $("#administrazioa-ordutegia__form-egunaInput").val();
		finkoa = finkoaFormatuaAlderantziz($("#administrazioa-ordutegia__form-finkoaInput").val());
		
		if (eguna == "")
			data = { "id": ordutegiaId, "linea": lineaId, "irteeraOrdua": irteeraOrdua, "helmugaOrdua": helmugaOrdua,
			  "finkoa": finkoa };
		else {
			eguna = data(eguna);
			data = { "id": ordutegiaId, "linea": lineaId, "irteeraOrdua": irteeraOrdua, "helmugaOrdua": helmugaOrdua,
				  "data": eguna, "finkoa": finkoa };
		}
		
		$.ajax({
			  url: '/api/ordutegia/',
			  type: 'PUT',
			  contentType: "application/x-www-form-urlencoded",
			  data: data
		});
		
		setTimeout(function(){
	    	ordutegiak.replaceData("/api/linea/ordutegiak?lineaID="+lineaId);
		}, 500);
	});
	
	$("#administrazioa-toolbar__change").mouseenter(function(){
		select("#administrazioa-toolbar__change");
	});
	$("#administrazioa-toolbar__new").mouseenter(function(){
		select("#administrazioa-toolbar__new");
	});
	
	$("#administrazioa-toolbar__change").mouseleave(function(){
		deselect("#administrazioa-toolbar__change");
	});
	$("#administrazioa-toolbar__new").mouseleave(function(){
		deselect("#administrazioa-toolbar__new");
	});
	
	$("#administrazioa-ordutegia__form--change").mouseenter(function(){
		select("#administrazioa-ordutegia__form--change");
	});
	$("#administrazioa-ordutegia__form--new").mouseenter(function(){
		select("#administrazioa-ordutegia__form--new");
	});
	
	$("#administrazioa-ordutegia__form--change").mouseleave(function(){
		deselect("#administrazioa-ordutegia__form--change");
	});
	$("#administrazioa-ordutegia__form--new").mouseleave(function(){
		deselect("#administrazioa-ordutegia__form--new");
	});
	
	function select(toolbar_element) {
		$(toolbar_element).attr('class', 'administrazioa-toolbar__element administrazioa-toolbar__element--selected');
	}
	
	function deselect(toolbar_element) {
		$(toolbar_element).attr('class', 'administrazioa-toolbar__element');
	}
	
});