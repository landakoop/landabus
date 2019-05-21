<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
	<head>
		<title><tiles:getAsString name="title" /></title>
		<link rel="stylesheet" href="/css/main.css">
	</head>
	<body>
		<!-- Header -->
		<tiles:insertAttribute name="header" />

		<div class=center>
			<!-- Aside -->
			<tiles:insertAttribute name="aside" />
			<!-- Body -->
			<tiles:insertAttribute name="body" />
		</div>
		<!-- Footer -->
		<tiles:insertAttribute name="footer" />
	</body>
</html> 