<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Successful</title>
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
