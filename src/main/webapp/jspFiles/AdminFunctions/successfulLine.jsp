<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Tool List</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta charset="utf-8" />
    <link rel="stylesheet" href="css/tabelsort.css">
</head>
<body>
<jsp:include page="../PageElements/header.jsp"/>

<html>
<head>
    <title>Tool maintenance</title>
</head>
<style>
    table, th, td { border:1px solid black;}
</style>
<body>
<jsp:include page="../PageElements/header.jsp"/>
<%
        String successfulLine = (String) request.getAttribute("successfulLine");
        out.print(successfulLine);
        request.removeAttribute("successfulLine");
%>
<jsp:include page="../PageElements/footer.jsp"/>

</body>
</html>
