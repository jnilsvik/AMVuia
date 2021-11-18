<%@ page import="java.util.ArrayList" %>
<%@ page import="bacit.web.Modules.ToolModel" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<html>
<head>
    <title>Return tools</title>
    <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3' crossorigin='anonymous'>
    <link rel="stylesheet" href="css/misc.css">
    <style> table, th, td { border:1px solid black;} </style>
</head>
<body>
<jsp:include page="../PageElements/header.jsp"/>

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
        <th>Tool ID</th>
        <th>Total Price</th>
    </tr>

    <%
        ResultSet rs1 = (ResultSet) request.getAttribute("notReturned");
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        try {
            while(rs1.next()) {
                if(rs1.getDate("endDate").toLocalDate().isBefore(LocalDate.now().plusDays(1))) {
                    out.print("<tr>");
                    out.print("<td>" + rs1.getInt("userID") + "</td> ");
                    out.print("<td>" + rs1.getString("firstName") + "</td> ");
                    out.print("<td>" + rs1.getString("lastName") + "</td> ");
                    out.print("<td>" + rs1.getString("email") + "</td> ");
                    out.print("<td>" + rs1.getString("phoneNumber") + "</td> ");
                    out.print("<td>" + rs1.getInt("orderID") + "</td> ");
                    out.print("<td>" + rs1.getDate("startDate").toLocalDate().format(formatters) + "</td> ");
                    out.print("<td>" + rs1.getDate("endDate").toLocalDate().format(formatters) + "</td> ");
                    out.print("<td>" + rs1.getInt("toolID") + "</td> ");
                    out.print("<td>" + rs1.getInt("totalPrice") + "</td> ");
                    out.print("</tr>");
                }
            }

        }  catch (Exception e) {
            e.printStackTrace();
        }
        request.removeAttribute("notReturned");
    %>
</table>
<br>

<div class='page'>
    <article class='my-3 amv-register' id='floating-labels'>
        <div class='bd-heading sticky-xl-top align-self-start mb-3 mt-xl-0 mb-xl-2'>
            <h3>Mark tool as returned</h3>
        </div>
        <div>
            <div class='bd-example'>
                <form action = 'toolreturnal' method = 'POST'>
                    <div class='form-floating mb-3'>
                        <input type = 'text' class='form-control' name = 'orderID' id="orderID" placeholder='name'>
                        <label for ='orderID' >Order ID: </label>
                    </div>
                    <div class='form-floating mb-3'>
                        <input type = 'date' name = 'returndate' id = 'returndate' class='form-control'><br>
                        <%--@declare id="accomplishdate"--%><label for = 'returndate'>Return Date: </label>
                    </div>
                    <div class='col-12' >
                        <button class='btn btn-primary' style='width: 100%'  type='submit'>Submit</button>
                    </div>
                </form>
            </div>
        </div>
    </article>
</div>
<jsp:include page="../PageElements/footer.jsp"/>
</body>
</html>