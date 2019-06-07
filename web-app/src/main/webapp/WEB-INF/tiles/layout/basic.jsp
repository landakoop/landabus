<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
	<head>
		<title>Landabus</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="/css/main.css">
		<link href="https://unpkg.com/tabulator-tables@4.2.7/dist/css/tabulator.min.css" rel="stylesheet">
<script type="text/javascript" src="https://unpkg.com/tabulator-tables@4.2.7/dist/js/tabulator.min.js"></script>
	</head>
	<body>
		<tiles:insertAttribute name="header" />
		<tiles:insertAttribute name="aside" />
		<tiles:insertAttribute name="content" />
		<tiles:insertAttribute name="footer" />
	</body>
</html> 