function orduaFormatter(cell,formatterParams,onRendered){

	return parseInt(cell.getValue()/60) + ":" + parseInt(cell.getValue() % 60);
} 

const ibilbideak = new Tabulator(".ibilbideak-zerrenda__taula", {
    ajaxURL: "http://localhost:8080/api/ibilbidea/list",
    ajaxConfig:"GET",
    layout: "fitColumns",
    tooltips: true,
    addRowPos: "top",
    pagination: "local",
    paginationSize: 15,
    height:"100%",
    groupBy: "mota",
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
    	geltokiak.replaceData("http://localhost:8080/api/geltokia/list2?ibilbideaID="+row.getData().ibilbideaID);
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