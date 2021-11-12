<%@ page import="bacit.web.a_models.BookingModel" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Register</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="css/list.css">
    <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3' crossorigin='anonymous'>
</head>
<body>
    <jsp:include page="_head_nav.jsp"/>
    <h2>Your current bookings</h2>
    <table>
        <tr>
            <th>Order Number</th>
            <th>Tool Name</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Total Price</th>
            <th>Return Date</th>
            <th>Cancel Booking</th>
        <%
            List<BookingModel> bookings = (List<BookingModel>) request.getAttribute("bookings");
            PrintWriter writer = (PrintWriter) request.getAttribute("out");
            for(BookingModel booking : bookings){
                    out.println("<tr>");
                    out.println("<td>" + booking.getOrderID() + "</td> ");
                    out.println("<td>" + booking.getToolName() + "</td> ");
                    out.println("<td>" + booking.getStartDate() + "</td> ");
                    out.println("<td>" + booking.getEndDate() + "</td> ");
                    out.println("<td>" + booking.getTotalPrice() + "</td> ");
                    out.println("<td>" + booking.getReturnDate() + "</td> ");
                    if(booking.getStartDate().isAfter(LocalDate.now())){
                        out.println("<td>");
                            out.println("<a href=\"/bacit-web-1.0-SNAPSHOT/cancellation?id="+booking.getOrderID()+"\">cancel</a>");
                        out.println("</td>");
                    }else{
                        if(booking.getEndDate().isAfter(LocalDate.now())){
                            out.println("<td>Booking started</td>");
                        }else {
                            out.println("<td>Booking over</td>");
                        }
                    }
                    out.println("</tr>");
            }

        %>
    </table>
</body>
</html>
