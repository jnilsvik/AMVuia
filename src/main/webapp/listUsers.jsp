<%@ page import="java.util.ArrayList" %>
<%@ page import="bacit.web.a_models.ToolModel" %>
<%@ page import="bacit.web.a_models.UserModel" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: Joachim
  Date: 10.11.2021
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>User List</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta charset="utf-8" />
    <link rel="stylesheet" href="CSS/tabelsort.css">
</head>
<body>
    <jsp:include page="__head_nav.html"/>

    <h3>User Listing</h3>
    <table class="table-sortable">
        <thead>
            <tr>
                <th>User ID</th>
                <th>Email</th>
                <th>Firstname</th>
                <th>Lastname</th>
                <th>Phone nmbr</th>
                <th>Union</th>
                <th>Admin</th>
            </tr>
        </thead>
        <tbody>
        <%
            ResultSet rs = (ResultSet) request.getAttribute("userList");
            if(rs != null) {
                try {
                    while (rs.next()) {
                            out.println("<tr>");
                            out.println("<td>" + rs.getInt("userID") + "</td>");
                            out.println("<td>" + rs.getString("email") + "</td>");
                            out.println("<td>" + rs.getString("firstname") + "</td>");
                            out.println("<td>" + rs.getString("lastname") + "</td>");
                            out.println("<td>" + rs.getString("phoneNumber") + "</td>");
                            out.println("<td>" + rs.getBoolean("unionMember") + "</td>");
                            out.println("<td>" + rs.getBoolean("userAdmin") + "</td>");
                            out.println("</tr>");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        %>
        </tbody>
    </table>

    <script src="JS/tabelsort.js"></script>

</body>
</html>
