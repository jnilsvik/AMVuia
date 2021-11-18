<%@ page import="bacit.web.Modules.UserModel" %>
<%@ page import="java.util.List" %>
<%@ page import="bacit.web.Modules.ToolModel" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Profile</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="css/list.css">
    <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3' crossorigin='anonymous'>
</head>
<body>
<jsp:include page="../PageElements/header.jsp"/>
    <%
        boolean success = (boolean) request.getAttribute("success");
        boolean user = (boolean) request.getAttribute("user");
        if(user) {
            if (!success) {
                out.print("<h1>No user found with this email. No user could be deleted</h1>");
            } else {
                List<UserModel> users = (List<UserModel>) request.getAttribute("users");
                out.print("<h1>User got deleted</h1>");
                out.print("<table>");
                for (UserModel u : users) {
                    out.print("<tr>" +
                            "<td>" + u.getEmail() + "</td>" +
                            "</tr>");
                }
            }
        }else{
            if(!success){
                out.print("<h1>No tool found with this ID. No tool could be deleted</h1>");
            }else {
                List<ToolModel> tools = (List<ToolModel>) request.getAttribute("tools");
                out.print("<h1>Tool got deleted</h1>");
                out.print("<table>");
                for (ToolModel t : tools) {
                    out.print("<tr>" +
                            "<td>" + t.getToolName()+ "</td>" +
                            "</tr>");
                }
            }
            out.print("</table>");
        }
    %>
<jsp:include page="../PageElements/footer.jsp"/>
</body>
</html>
