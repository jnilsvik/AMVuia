<%@ page import="java.sql.ResultSet" %><%--
  Created by IntelliJ IDEA.
  User: Joachim
  Date: 10.11.2021
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta charset="utf-8" />
    <link rel="stylesheet" href="CSS/tabelsort.css">
</head>
<body>
    <h3>Tool Stuff</h3>
    <table class="table-sortable">
        <thead>
            <tr>
                <th>toolName</th>
                <th>category</th>
                <th>priceFirst</th>
                <th>priceAfter</th>
                <th>maintenance</th>
                <th>certificateID</th>
            </tr>
        </thead>
        <tbody>
        <%
            ResultSet rs = (ResultSet) request.getAttribute("toollist");
            try {
                while (rs.next()) {
                    //prints them to the table
                    out.print(
                            "<tr" +
                            "<td>" + rs.getString("toolName") + "</th>"+
                            "<td>" + rs.getString("toolCategory") + "</th> " +
                            "<td>" + rs.getInt("priceFirst") + "</th>" +
                            "<td>" + rs.getInt("priceAfter") + "</th>" +
                            "<td>" + rs.getBoolean("maintenance") + "</th>" +
                            "<td>" + rs.getInt("certificateID") + "</th>" +
                            "</tr>");
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        %>
        </tbody>
    </table>

    <script src="JS/tabelsort.js"></script>

</body>
</html>
