$(document).ready(() => {
	url = "http://localhost:8080/api/geltokia/list";
	
	$.getJSON(url, function(result) {
		$(result).each(function( index ) {	    			
			posizioa = result[index].x + "-" + result[index].y;
			posizioa = posizioa.replace(".", "_");
			$("#" + posizioa).append("<div><span id='" + posizioa + "_dot' class='dot'></span>"
					+ "<span class='geltoki-izena'>" + result[index].izena) + "</span></div>";
		});
	});
	
	function drawLine(linea, from, to) {
		linea.beginPath();
		linea.moveTo(from.position().left+from.width(), from.position().top+from.height());
		linea.lineTo(to.position().left+to.width(), to.position().top+to.height());
		linea.stroke();
	}
	
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
	    	
	    	$(".dot").each(function() {
	    		$(this).css("background-color", $("*").css("--secondary-bg-color"));
	    	});
	    	
	    	$(".line").each(function() {
	    		$(this).remove();
	    	});
	    	
	    	$.getJSON(url, function(result) {
	    		$(result).each(function(index) {
	    			posizioa = "#" + result[index].x + "-" + result[index].y;
	    			posizioa = posizioa.replace(".", "_");
	    			$(posizioa+"_dot").css("background-color", $("*").css("--selected-bg-color"));
	    			
	    			if ((result.length - 1) > index) {
	    				posizioa2 = "#" + result[index+1].x + "-" + result[index+1].y;
	    				posizioa2 = posizioa2.replace(".", "_");
	    				
	    				var newCanvas = $('<canvas/>', { id: 'line'+index, 'class': 'line',
	    					Height: $(window).height(), Width: $(window).width()});
	    				$(".ibilbideak-mapa__element").append(newCanvas);
	    				
	    				linea = newCanvas.get(0).getContext('2d');	    				
	    				from = $(posizioa);
	    				to = $(posizioa2);
	    				drawLine(linea, from, to);
	    			}
	    		});
	    	});
			
	    	geltokiak.replaceData(url);
	    },
	});
	
	const geltokiak = new Tabulator(".ibilbideak-eskaerak__taula", {
	    ajaxURL: "http://localhost:8080/api/geltokia/list2?ibilbideaID=1",
	    ajaxConfig:"GET",
	    layout: "fitColumns",
	    tooltips: true,
	    addRowPos: "top",
	    pagination: "local",
	    paginationSize: 10,
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