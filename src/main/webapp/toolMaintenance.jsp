<%@ page import="java.util.ArrayList" %>
<%@ page import="bacit.web.a_models.ToolModel" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.sql.ResultSet" %><%--
  Created by IntelliJ IDEA.
  User: Dilan
  Date: 11.11.2021
  Time: 12:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Tool List</title>
    <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3' crossorigin='anonymous'>
    <style>
        .page {
            flex-grow: 1;
            width: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
            padding-bottom: 70px;

            background-image: url(https://media.discordapp.net/attachments/472062607646261249/702987431653277705/unknown.png);
            background-repeat: no-repeat;
            background-attachment: fixed;
            background-size: 100% 100%;
        }
        .amv-register {
            width: 480px;
            background: #fff;
            box-shadow: 0 25px 75px rgba(16, 30, 54, .25);
            border-radius: 6px;
            padding: 30px 60px 26px;
            margin-top: -75px;
        }
    </style>
</head>
<body>
<jsp:include page="__head_nav.html"/>

<html>
<head>
    <title>Tool maintenance</title>
</head>
<style>
    table, th, td { border:1px solid black;}
</style>
<body>
<jsp:include page="__head_nav.html"/>

<form action = 'toolmaintenance' method = 'POST'>

    <label for = 'toolmaintenance'>Put tool in maintenance</label></td>
    <input type = 'radio' id = 'toolmaintenance'  name = 'toolmaintenance' value = 'ToolInMaintenanceIn'>
    <br>

    <label for = 'toolmaintenance'>Put tool out of maintenance</label>
    <input type = 'radio' id = 'toolmaintenance'  name = 'toolmaintenance' value = 'ToolInMaintenanceOut'>
    <br>

    <label for = 'toolID'>Tool ID: </label><br>
    <input type = 'text' name = 'toolID'><br>
    <br>
    <input type = 'submit' value = 'Submit'>
</form>
<br>

</body>
</html>
