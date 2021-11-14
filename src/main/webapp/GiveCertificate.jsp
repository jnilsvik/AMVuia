<%@ page import="java.util.List" %>
<%@ page import="bacit.web.a_models.Certificate" %><%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 14.11.2021
  Time: 13:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Give Certificate</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <meta charset="utf-8" />
  <link rel="stylesheet" href="css/list.css">
  <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3' crossorigin='anonymous'>
</head>
<body>
    <jsp:include page="_head_nav.jsp"/>
    <h2>Register User</h2>
    <form action = 'givecertificate' method = 'POST'>
        <%--@declare id="userid"--%><label for = "userID">User ID: </label><br>
        <input type = 'text' name = 'userID'><br>
        <%--@declare id="accomplishdate"--%><label for = 'accomplishdate'>Accomplish Date: </label><br>
        <input type = 'date' name = 'accomplishdate'><br>
        <%
            List<Certificate> certificateNames = (List<Certificate>) request.getAttribute("certificates");
            out.print("<label for = 'certificateID'>Tool Certificate: </label><br>");
            out.print("<select name = 'certificateID' id = 'certificateID'><br>");
            for (Certificate cert : certificateNames) {
                String certificateName = cert.getCertificateName();
                int certificateID = cert.getCertificateID();
                out.print("<option value = '" + certificateID + "'> " + certificateName + " </option>");
            }
        %>
        </select>
        <br>
        <br>
        <input type = 'submit' value = 'Giver User Certificate'>
    </form>
</body>
</html>
