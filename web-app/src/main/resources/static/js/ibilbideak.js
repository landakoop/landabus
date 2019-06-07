$(document).ready(() => {
	function orduaFormatter(cell,formatterParams,onRendered) {		
		return orduaFormatua(cell.getValue());
	}
	
	function orduaFormatua (denbora) {
		ordua = parseInt(denbora/60);
		minutuak = parseInt(denbora % 60);
		
		if (ordua < 10) ordua = "0" + ordua;
		if (minutuak < 10) minutuak = "0" + minutuak;
		
		return ordua + ":" + minutuak;
	}
	
	const ibilbideak = new Tabulator(".ibilbideak-zerrenda__taula", {
	    ajaxURL: "http://localhost:8080/api/ibilbidea/list-gaur",
	    ajaxConfig:"GET",
	    layout: "fitColumns",
	    tooltips: true,
	    addRowPos: "top",
	    pagination: "local",
	    paginationSize: 15,
	    height:"100%",
	    groupBy: "mota",
	    selectable:1,
	    dataLoaded:function(){
	        this.selectRow(1);
	    },
	    groupHeader:function(value, count, data, group){
	        if(data[0].finkoa){
	        	return "Finkoak";
	        }else{
	        	return "Malguak";
	        }
	    },
	    columns: [
	        {title: "ID", field: "ibilbideaID", align: "center", width: 50},
	        {title: "Irt ordua", field: "irteeraOrdua", formatter:orduaFormatter},
	        {title: "Hel ordua", field: "helmugaOrdua", formatter:orduaFormatter},
	        {title: "Irteera", field: "irteera",headerSort:false},
	        {title: "Helmuga", field: "helmuga",headerSort:false},
	        {title: "Geldialdi kop", field: "geldialdiak", align: "center",headerSort:false},
	    ],
	    rowClick:function(e, row){
	    	url = "http://localhost:8080/api/geltokia/list2?ibilbideaID="+row.getData().ibilbideaID;
	    	
	    	$.getJSON(url, function(result) {    		    
	    		console.log(result[0].izena);
    		});	    	
	    	$("#1-2").append("<span id='rb" + "0'" + " class='dot'></span>");
	    	geltokiak.replaceData(url);
	    },
	});
	
	const geltokiak = new Tabulator(".ibilbideak-eskaerak__taula", {
	    ajaxURL: "http://localhost:8080/api/geltokia/list2?ibilbideaID=2",
	    ajaxConfig:"GET",
	    layout: "fitColumns",
	    tooltips: true,
	    addRowPos: "top",
	    pagination: "local",
	    paginationSize: 5,
	    columns: [
	        {title: "ID", field: "geltokiaID", align: "center", width: 50},
	        {title: "Izena", field: "izena"},
	        {title: "Igo", field: "igo"},
	        {title: "Jaitsi", field: "jaitsi"},
	        {title: "Eskaerak", field: "eskaerak"},
	        {title: "Ordua", field: "helmugaOrdua"},
	    ],
	});
	
	$("#ibilbideak-toolbar__gaur").click(function(){
		ibilbideak.replaceData("http://localhost:8080/api/ibilbidea/list-gaur");
		select("#ibilbideak-toolbar__gaur");
		deselect("#ibilbideak-toolbar__bihar");
		deselect("#ibilbideak-toolbar__denak");
	});
	
	$("#ibilbideak-toolbar__bihar").click(function(){
		ibilbideak.replaceData("http://localhost:8080/api/ibilbidea/list-bihar");
		select("#ibilbideak-toolbar__bihar");
		deselect("#ibilbideak-toolbar__gaur");
		deselect("#ibilbideak-toolbar__denak");
	});
	
	$("#ibilbideak-toolbar__denak").click(function(){
		ibilbideak.replaceData("http://localhost:8080/api/ibilbidea/list");
		select("#ibilbideak-toolbar__denak");
		deselect("#ibilbideak-toolbar__bihar");
		deselect("#ibilbideak-toolbar__gaur");
	});
	
	function select(toolbar_element) {
		$(toolbar_element).attr('class', 'ibilbideak-toolbar__element ibilbideak-toolbar__element--selected');
	}
	
	function deselect(toolbar_element) {
		$(toolbar_element).attr('class', 'ibilbideak-toolbar__element');
	}
});