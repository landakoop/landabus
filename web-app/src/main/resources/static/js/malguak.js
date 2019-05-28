const malguak = new Tabulator(".malguak-eskaerak__taula", {
    ajaxURL: "http://localhost:8080/api/eskaera/list",
    ajaxConfig:"GET",
    layout: "fitColumns",
    tooltips: true,
    addRowPos: "top",
    pagination: "local",
    paginationSize: 15,
    height:"100%",
    columns: [
        {title: "ID", field: "id", align: "center", width: 50},
        {title: "Jatorria", field: "irteera"},
        {title: "Helmuga", field: "helmuga"},
        {title: "Irteera Ordua", field: "irteeraOrdua"},
        {title: "Helmuga Ordua", field: "helmugaOrdua"},
        {title: "Onartua", field: "sent", formatter: "tickCross", width: 100, align: "center"},
    ],
});

const ibilbideak = new Tabulator(".malguak-ibilbideak__taula", {
    ajaxURL: "http://localhost:8080/api/eskaera/list",
    ajaxConfig:"GET",
    layout: "fitColumns",
    tooltips: true,
    addRowPos: "top",
    pagination: "local",
    paginationSize: 15,
    height:"100%",
    columns: [
        {title: "ID", field: "id", align: "center", width: 50},
        {title: "Jatorria", field: "irteera"},
        {title: "Helmuga", field: "helmuga"},
        {title: "Irteera Ordua", field: "irteeraOrdua"},
        {title: "Helmuga Ordua", field: "helmugaOrdua"},
        {title: "Onartua", field: "sent", formatter: "tickCross", width: 100, align: "center"},
    ],
});