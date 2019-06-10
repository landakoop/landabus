<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<div class="content ibilbideak">
	<div class=ibilbideak-toolbar>
		<div id="ibilbideak-toolbar__gaur" class="ibilbideak-toolbar__element ibilbideak-toolbar__element--selected" >Gaur</div>
		<div id="ibilbideak-toolbar__bihar" class="ibilbideak-toolbar__element">Bihar</div>
		<div id="ibilbideak-toolbar__denak" class="ibilbideak-toolbar__element">Denak</div>
	</div>
	<div class="blokea ibilbideak-zerrenda">
		<h2 class="blokea__titulua">Ibilbideak</h2>
		<div class="ibilbideak-zerrenda__taula"></div>
	</div>
	
	<div class="blokea ibilbideak-mapa">
		<h2 class="blokea__titulua">Mapa</h2>
		<div class="ibilbideak-mapa__element">
			<c:forEach var = "i" begin = "0" end = "6">
         		<div id="<c:out value="${i}"/>" class="ibilbideak-mapa__element__ilara">
         			<c:forEach var = "j" begin = "0" end = "6">
         				<div id="<c:out value="${j}"/>_5-<c:out value="${i}"/>_5" class="ibilbideak-mapa__element__zutabea"></div>
         				<div id="<c:out value="${j+1}"/>-<c:out value="${i}"/>_5" class="ibilbideak-mapa__element__zutabea"></div>
         			</c:forEach>
            	</div>
            	<div id="<c:out value="${i}"/>.5" class="ibilbideak-mapa__element__ilara">
         			<c:forEach var = "j" begin = "0" end = "6">
         				<div id="<c:out value="${j}"/>_5-<c:out value="${i+1}"/>" class="ibilbideak-mapa__element__zutabea"></div>
         				<div id="<c:out value="${j+1}"/>-<c:out value="${i+1}"/>" class="ibilbideak-mapa__element__zutabea"></div>
         			</c:forEach>
            	</div>
      		</c:forEach>
		</div>
	</div>
	
	<div class="blokea ibilbideak-eskaerak">
		<h2 class="blokea__titulua">Geltokiak</h2>
		<div class="ibilbideak-eskaerak__taula"></div>
	</div>
</div>
<link href="https://unpkg.com/tabulator-tables@4.1.4/dist/css/tabulator.min.css" rel="stylesheet">
<link rel="stylesheet" href="/css/ibilbideak.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="/js/ibilbideak.js"></script>