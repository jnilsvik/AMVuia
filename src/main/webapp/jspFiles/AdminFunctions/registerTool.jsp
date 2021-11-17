<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Register Tool</title>
</head>
<body>
<h2>Register Tool</h2>
<form action = 'toolregister' method = 'POST'> 
    <label for = 'toolname'>Tool Name: </label>
    <input type = 'text' name = 'toolname'id="toolname">
    <label for = 'pricefirst'>Price First Day: </label>
    <input type = 'text' name = 'pricefirst' id="pricefirst">
    <label for = 'priceafter'>Price After First Day: </label>
    <input type = 'text' name = 'priceafter' id="priceafter">

    <label for = 'toolCategory'>Tool Category: </label>
    <select name = 'toolCategory' id = 'toolCategory'>
<%
    ResultSet rs = (ResultSet) request.getAttribute("regTool1");
    ResultSet rs1 = (ResultSet) request.getAttribute("regTool2");
    try{
        while (rs.next()) {
            String categoryName = rs.getString("toolCategory");
           out.print("<option value = '" + categoryName + "'> " + categoryName + " </option>");
       }
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }

%>
    </select>
    <label for = 'toolcertificate'>Tool Certificate: </label>
    <select name = 'toolcertificate' id = 'toolcertificate'>
        <%
            try {
                while (rs1.next()) {
                    int certificateID = rs1.getInt("certificateID");
                    String certificateName = rs1.getString("certificateName");
                    out.print("<option value = '" + certificateID + "'> " + certificateName + " </option>");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        %>
    </select>
    <label for = 'tooldesc'>Tool Description: </label>
    <input type = 'text' name = 'tooldesc' id="tooldesc">
    <input type = 'submit' value = 'Register Tool'>
    </form>
</body>
</html>

