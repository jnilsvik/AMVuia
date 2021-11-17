<%@ page import="java.sql.ResultSet" %>
<%@ page import="bacit.web.Modules.ToolModel" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.tools.Tool" %><%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 14.11.2021
  Time: 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Remove Tool</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <meta charset="utf-8" />
  <link rel="stylesheet" href="css/list.css">
  <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3' crossorigin='anonymous'>
</head>
<body>
  <jsp:include page="_head_nav.jsp"/>
  <h2>Please enter the ID or name of the tool you want to remove</h2>
  <form method='POST'>
    <h3>ID:</h3>
    <textarea id='input' name='input' rows='1' cols='50'></textarea><br><br>
    <input type = 'submit' value = 'Remove!'>

    <table>
      <%
        List<ToolModel> tools = (List<ToolModel>) request.getAttribute("tools");
        for(ToolModel tool : tools){
          out.print("<tr>" +
                  "<td>" + tool.getToolID() + "</td>" +
                  "<td>" + tool.getToolName() + "</td>" +
                  "</tr>");
        }
      %>
    </table>
    </form>
</body>
</html>
