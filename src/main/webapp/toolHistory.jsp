<%@ page import="java.util.ArrayList" %>
<%@ page import="bacit.web.a_models.BookingModel" %>
<%@ page import="java.time.LocalDate" %><%--
  Created by IntelliJ IDEA.
  User: Joachim
  Date: 10.11.2021
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Tool History</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta charset="utf-8" />
    <link rel="stylesheet" href="css/tabelsort.css">
</head>
<body>
    <jsp:include page="_head_nav.jsp"/>

    <h3>Tool History</h3>
    <table class="table-sortable">
        <thead>
            <tr>
                <th>Order ID</th>
                <th>User ID</th>
                <th>Tool ID</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Return Date</th>
            </tr>
        </thead>
        <tbody>
        <%
            ArrayList<BookingModel> model = (ArrayList<BookingModel>) request.getAttribute("bookingList");
            if(model != null) {
                for (BookingModel bm : model){
                    out.print(
                            "<tr>" +
                            "<td>" + bm.getOrderID() + "</td>" +
                            "<td>" + bm.getUserID() + "</td>" +
                            "<td>" + bm.getToolID() + "</td>" +
                            "<td>" + bm.getStartDate().toString() + "</td>" +
                            "<td>" + bm.getEndDate().toString() + "</td>");
                    if (bm.getReturnDate().isEqual(LocalDate.parse("1999-12-12"))){
                        out.print(
                                "<td>N/A</td></tr>");
                    } else {
                        out.print(
                                "<td>" + bm.getReturnDate().toString() + "</td>" +
                                        "</tr>");
                    }
                }
            }
        %>
        </tbody>
    </table>

    <script src="js/tabelsort.js"></script>

</body>
</html>