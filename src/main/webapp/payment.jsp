<%@ page import="java.util.ArrayList" %>
<%@ page import="bacit.web.a_models.ToolModel" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDate" %>
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
    <title>Tool List</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta charset="utf-8" />
    <link rel="stylesheet" href="css/tabelsort.css">
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
<table style = 'width:100%'>
    <tr>
        <th>User ID</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Email</th>
        <th>Phone Number</th>
        <th>Order ID</th>
        <th>Start Date</th>
        <th>End Date</th>
        <th>Return Date</th>
        <th>Tool ID</th>
        <th>Total Price</th>
        </tr>

    <%

        ResultSet rs1 = (ResultSet) request.getAttribute("unpaid");
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        try {
            while(rs1.next()) {
                int userID = rs1.getInt("userID");
                String firstName = rs1.getString("firstName");
                String lastName = rs1.getString("lastName");
                String email1 = rs1.getString("email");
                String phoneNumber = rs1.getString("phoneNumber");

                int orderID = rs1.getInt("orderID");
                LocalDate startDate = rs1.getDate("startDate").toLocalDate();
                LocalDate endDate = rs1.getDate("endDate").toLocalDate();
                LocalDate toolReturnDate = rs1.getDate("returnDate").toLocalDate();
                int toolID = rs1.getInt("toolID");
                int totalPrice = rs1.getInt("totalPrice");

                String startDateString = startDate.format(formatters);
                String endDateString = endDate.format(formatters);
                String toolReturnalDateString = toolReturnDate.format(formatters);

                out.print("<tr>");
                out.print("<td>" + userID + "</td> ");
                out.print("<td>" + firstName + "</td> ");
                out.print("<td>" + lastName + "</td> ");
                out.print("<td>" + email1 + "</td> ");
                out.print("<td>" + phoneNumber + "</td> ");
                out.print("<td>" + orderID + "</td> ");
                out.print("<td>" + startDateString + "</td> ");
                out.print("<td>" + endDateString + "</td> ");
                out.print("<td>" + toolReturnalDateString + "</td> ");
                out.print("<td>" + toolID + "</td> ");
                out.print("<td>" + totalPrice + "</td> ");
                out.print("</tr>");
            }

        }  catch (Exception e) {
            e.printStackTrace();
        }

        request.removeAttribute("unpaid");
    %>
    </tr>
    </table>
<br>
<h2>Mark order as payed</h2>
<form action = 'payment' method = 'POST'>
    <label for ='orderID' >Order ID: </label><br>
    <input type = 'text' name = 'orderID' id="orderID"><br>
    <input type = 'submit' value = 'Submit'>
    </form>
</body>
</html>

</body>
</html>
