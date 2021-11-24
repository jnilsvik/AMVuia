<%@ page import="bacit.web.Modules.ToolModel" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>Remove Tool</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <meta charset="utf-8" />
  <link rel="stylesheet" href="css/list.css">
  <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3' crossorigin='anonymous'>
</head>
<body>
<jsp:include page="../PageElements/header.jsp"/>
  <h2>Please enter the ID or name of the tool you want to remove</h2>
  <%// TODO: 24.11.2021 this method dont have a action???%>
  <form method='POST'>
    <label for="input">ID: </label>
    <textarea id='input' name='input' rows='1' cols='50'></textarea>
    <input type = 'submit' value = 'Remove!'>
  </form>
  <table>
    <%
      List<ToolModel> tools = (List<ToolModel>) request.getAttribute("tools");
      for(ToolModel tool : tools){
        out.print(
                "<tr>" +
                        "<td>" + tool.getToolID() + "</td>" +
                        "<td>" + tool.getToolName() + "</td>" +
                        "</tr>");
      }
    %>
  </table>
<jsp:include page="../PageElements/footer.jsp"/>
</body>
</html>
