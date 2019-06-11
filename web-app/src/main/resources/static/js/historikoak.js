$(document).ready(function(){
    $.getJSON('/api/ibilbidea/historikoa' + i,    function (data) {
        seriesOptions[i] = {
            name: name,
            data: data
        };
        seriesCounter += 1;
        if (seriesCounter === names.length) {
        	createTimeSeries(seriesOptions);
        }
    });
}

var seriesOptions = [],
seriesCounter = 0,
names = ['Guztira','Malguak','Finkoak'];

Highcharts.stockChart('timeseries', {
    rangeSelector: {
        selected: 4
    },
    yAxis: {
        plotLines: [{
            value: 0,
            width: 2,
            color: 'silver'
        }]
    },
    tooltip: {
        valueDecimals: 2,
        split: true
    },
    series: data
});
