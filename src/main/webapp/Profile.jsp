<%@ page import="bacit.web.Modules.BookingModel" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Profile</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="css/list.css">
    <style> table, th, td {border: 1px solid black;} th, td {padding: 0 5px;}</style>
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
        </tr>
        <%
            List<BookingModel> bookings = (List<BookingModel>) request.getAttribute("bookings");
            for(BookingModel booking : bookings){
                    out.print("<tr>");
                    out.print("<td>" + booking.getOrderID() + "</td> ");
                    out.print("<td>" + booking.getToolName() + "</td> ");
                    out.print("<td>" + booking.getStartDate() + "</td> ");
                    out.print("<td>" + booking.getEndDate() + "</td> ");
                    out.print("<td>" + booking.getTotalPrice() + "</td> ");
                    out.print("<td>" + booking.getReturnDate() + "</td> ");
                    if(booking.getStartDate().isAfter(LocalDate.now())){
                        out.print("<td>");
                            out.print("<a href=\"cancellation?id="+booking.getOrderID()+"\">cancel</a>");
                        out.print("</td>");
                    }else{
                        if(booking.getEndDate().isAfter(LocalDate.now())){
                            out.print("<td>Booking started</td>");
                        }else {
                            out.print("<td>Booking over</td>");
                        }
                    }
                    out.print("</tr>");
            }
        %>
    </table>
</body>
</html>
