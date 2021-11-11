<%@ page import="bacit.web.a_models.BookingModel" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile Page</title>
</head>
<body>
<!DOCTYPE html>
<head>
    <title>Toollist</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="CSS/list.css">
    <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3' crossorigin='anonymous'>
</head>
<body>
    <jsp:include page="__head_nav.html"></jsp:include>
    <jsp:include page="_sidebar.jsp"></jsp:include>
    <%
    List<BookingModel> bookings = (List<BookingModel>) request.getAttribute("bookings");
    PrintWriter writer = (PrintWriter) request.getAttribute("out");
    %>
</body>
    <h2>Your current bookings</h2>
    <table style = 'width:90%'>
        <tr>
            <th>Order Number</th>
            <th>Tool Name</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Total Price</th>
            <th>Return Date</th>
            <th>Cancel Booking</th>
        </tr>
        <%
            for(BookingModel booking : bookings){
                out.println("<tr>");
                out.println("<td>" + booking.getOrderID() + "</td> ");
                out.println("<td>" + booking.getToolName(writer) + "</td> ");
                out.println("<td>" + booking.getStartDate() + "</td> ");
                out.println("<td>" + booking.getStartDate() + "</td> ");
                out.println("<td>" + booking.getTotalPrice(writer) + "</td> ");
                out.println("<td>" + booking.getReturnDate() + "</td> ");

                out.println("</tr>");
            }
        %>
    </table>
</body>
</html>
