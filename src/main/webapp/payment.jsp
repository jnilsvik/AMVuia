<%@ page import="java.util.ArrayList" %>
<%@ page import="bacit.web.a_models.ToolModel" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<html>
<head>
    <title>Payments</title>
    <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3' crossorigin='anonymous'>
    <style>
        .page {
            flex-grow: 1;
            width: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
            padding-bottom: 70px;

            background-image: url(https://media.discordapp.net/attachments/472062607646261249/702987431653277705/unknown.png);
            background-repeat: no-repeat;
            background-attachment: fixed;
            background-size: 100% 100%;
        }
        .amv-register {
            width: 480px;
            background: #fff;
            box-shadow: 0 25px 75px rgba(16, 30, 54, .25);
            border-radius: 6px;
            padding: 30px 60px 26px;
            margin-top: -75px;
        }

        table, th, td { border:1px solid black;}
    </style>
</head>
<body>
<jsp:include page="__head_nav.html"/>

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

<div class='page'>
    <article class='my-3 amv-register' id='floating-labels'>
        <div class='bd-heading sticky-xl-top align-self-start mb-3 mt-xl-0 mb-xl-2'>
            <h3>Mark order as payed</h3>
        </div>
        <div>
            <div class='bd-example'>
    <form action = 'payment' method = 'POST'>
        <div class='form-floating mb-3'>
            <input type = 'text' class='form-control' name = 'orderID' id="orderID" placeholder='name'>
            <label for ='orderID' >Order ID: </label>
        </div>
        <div class='col-12' >
            <button class='btn btn-primary' style='width: 100%'  type='submit'>Submit</button>
        </div>
    </form>
  </div>
 </div>
</article>
</div>
</body>
</html>

</body>
</html>
