<%@ page import="java.util.ArrayList" %>
<%@ page import="bacit.web.Modules.ToolModel" %>
<%@ page import="bacit.web.Modules.UserModel" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%--
  Created by IntelliJ IDEA.
  User: Joachim
  Date: 10.11.2021
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Admin User List</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta charset="utf-8" />
    <link rel="stylesheet" href="css/tabelsort.css">
</head>
<body>
<jsp:include page="../PageElements/header.jsp"/>

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
                            out.print(
                            "<FORM action='userhistoryadmin' method='get'>" +
                            "<tr>" +
                            "<td>" + rs.getInt("userID") + "</td>" +
                            "<td>" + rs.getString("email") + "</td>" +
                            "<td>" + rs.getString("firstname") + "</td>" +
                            "<td>" + rs.getString("lastname") + "</td>" +
                            "<td>" + rs.getString("phoneNumber") + "</td>" +
                            "<td>" + rs.getBoolean("unionMember") + "</td>" +
                            "<td>" + rs.getBoolean("userAdmin") + "</td>" +
                            "<td> <button name='userID' type='submit' value='"+rs.getInt("userID")+"'> View User's Tool History </button> </td>" +
                            "</tr>");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        %>
        </tbody>
    </table>

    <script src="js/tabelsort.js"></script>
<jsp:include page="../PageElements/footer.jsp"/>
</body>
</html>
