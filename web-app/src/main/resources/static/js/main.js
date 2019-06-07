$(document).ready(() => {
	$("#aside__hasiera").click(function(){
		select("#aside__hasiera");
		deselect("#aside__ibilbideak");
		deselect("#aside__historikoak");
		deselect("#aside__administrazioa");
		deselect("#aside__irten");
	});
	
	$("#aside__ibilbideak").click(function(){
		select("#aside__ibilbideak");
		deselect("#aside__hasiera");
		deselect("#aside__historikoak");
		deselect("#aside__administrazioa");
		deselect("#aside__irten");
	});
	
	function select(aside_element) {
		$(aside_element).attr('class', 'aside__element aside__element--selected');
	}
	
	function deselect(aside_element) {
		$(aside_element).attr('class', 'aside__element');
	}
	
});