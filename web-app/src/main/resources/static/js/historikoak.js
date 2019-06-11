$(document).ready(function() {
	var xArdatza = [], datuErrealak = [], datuAurreikuspenak = [];
	
    $.getJSON('/api/ibilbidea/historikoa', function (data) {
    	var names = ['Guztira','Malguak','Finkoak'], 
    		datuak = [], datuak2 = [], datuak3 = [];
    	
    	for (var i = 0; i < data.length; i++) {
    		datuak.push([new Date(data[i].data).getTime(), data[i].bidaiariak]);
    	}
    	
    	for (var i = 0; i < data.length; i++) {
    		datuak2.push([new Date(data[i].data).getTime(), data[i].malguak]);
    	}
    	
    	for (var i = 0; i < data.length; i++) {
    		datuak3.push([new Date(data[i].data).getTime(), data[i].finkoak]);
    	}
    	
    	var datuDenak = [];
    	datuDenak[0] = datuak;
    	datuDenak[1] = datuak2;
    	datuDenak[2] = datuak3;
    	
    	createTimeSeries(names, datuDenak);
    });
    
    $.getJSON('/api/eskaera/malgutasuna', function (data) {
    	createPieChart(data);
    });
    
    $.getJSON('/api/geldialdia/errealak', function (data) {    	
    	for (var i = 1; i < data.length; i++) {
    		datuErrealak.push(data[i].igo);
    	}
    });
    
    $.getJSON('/api/geldialdia/aurreikuspenak', function (data) {    	
    	for (var i = 0; i < data.length; i++) {
    		xArdatza.push(data[i].data.toString());
    		datuAurreikuspenak.push(data[i].igo);
    	}
    	
    });
    
    setTimeout(function(){
    	createBarChart(xArdatza, datuErrealak, datuAurreikuspenak);
    }, 500);

    function createTimeSeries(names, data) {
    	Highcharts.chart('timeseries', {
            xAxis: {
                type: 'datetime'
            },
            yAxis: {
                title: {
                    text: 'Bidaiari kopurua'
                }
            },
    	    title: {
    	        text: ''
    	    },
            tooltip: {
                split: true
            },
            series: [{
	            	name: names[0],
	                data: data[0]
	            },
	            {
	            	name: names[1],
	                data: data[1]
	            },
	            {
	            	name: names[2],
	                data: data[2]
	            },
            ]            
        });
    }
    
    function createPieChart(data) {
    	Highcharts.chart('pie', {
    	    chart: {
    	        plotBackgroundColor: null,
    	        plotBorderWidth: null,
    	        plotShadow: false,
    	        type: 'pie'
    	    },
    	    title: {
    	        text: ''
    	    },
    	    tooltip: {
    	        pointFormat: '<b>{point.percentage:.1f}%</b>'
    	    },
    	    plotOptions: {
    	        pie: {
    	            allowPointSelect: true,
    	            cursor: 'pointer',
    	            dataLabels: {
    	                enabled: false
    	            },
    	            showInLegend: true
    	        }
    	    },
    	    series: [{
    	        colorByPoint: true,
    	        data: [{
    	            name: 'Ez onartua',
    	            y: 100-data,
    	        }, {
    	            name: 'Onartua',
    	            y: data
    	        }]
    	    }]
    	});
    }
    
    function createBarChart(xArdatza, datuErrealak, datuAurreikuspenak) {
    	Highcharts.chart('bar', {
    		chart: {
    	        type: 'column'
    	    },
    	    title: {
    	        text: ''
    	    },
    	    xAxis: {
    	        categories: xArdatza
    	    },
    	    yAxis: {
    	        min: 0,
    	        title: {
    	            text: 'Igoera kopurua'
    	        }
    	    },
    	    tooltip: {
    	    	headerFormat: '<span style="font-size:15px">{point.key}</span><table>',
    	        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
    	            '<td style="padding:0"><b>{point.y}</b></td></tr>',
    	        footerFormat: '</table>',
    	        shared: true,
    	        useHTML: true
    	    },
    	    series: [{
    	    	name: 'Erreala',
    	    	data: datuErrealak
    	    },
    	    {
    	    	name: 'Aurreikuspena',
    	    	data: datuAurreikuspenak
    	    }]
    	});
    }
});
