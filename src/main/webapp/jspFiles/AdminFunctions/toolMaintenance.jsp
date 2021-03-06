<%@ page import="java.util.ArrayList" %>
<%@ page import="bacit.web.Modules.ToolModel" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.sql.ResultSet" %><%--
  Created by IntelliJ IDEA.
  User: Dilan
  Date: 11.11.2021
  Time: 12:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Payments</title>
    <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3' crossorigin='anonymous'>
    <link rel="stylesheet" href="css/misc.css">
    <style> table, th, td { border:1px solid black;} </style>
</head>
<body>
<jsp:include page="../PageElements/header.jsp"/>

<table style='width:100%'>
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Category</th>
        <th>in Maintenance?</th>
    </tr>
    </thead>
    <tbody>
    <%
        ArrayList<ToolModel> model = (ArrayList<ToolModel>) request.getAttribute("toolList");
        if(model != null) {
            for (ToolModel tm : model){
                out.print(
                                "<tr>" +
                                "<td>" + tm.getToolID() + "</td>" +
                                "<td>" + tm.getToolName() + "</td>" +
                                "<td>" + tm.getToolCategory() + "</td>" +
                                "<td>" + tm.getMaintenance() + "</td>" +
                                "</tr>");
            }
        }
    %>
    </tbody>
</table>

<script src="js/tabelsort.js"></script>

<div class='page'>
    <article class='my-3 amv-register' id='floating-labels'>
        <div class='bd-heading sticky-xl-top align-self-start mb-3 mt-xl-0 mb-xl-2'>
            <h3>Put tool in or out of maintanence</h3>
        </div>
        <div>
            <div class='bd-example'>
                <form action = 'toolmaintenance' method = 'POST'>

                    <label for = 'toolmaintenancein'>Put tool in maintenance</label>
                    <input type = 'radio' id = 'toolmaintenancein'  name = 'toolmaintenance' value = 'ToolInMaintenanceIn'>
                    <br>

                    <label for = 'toolmaintenanceout'>Put tool out of maintenance</label>
                    <input type = 'radio' id = 'toolmaintenanceout'  name = 'toolmaintenance' value = 'ToolInMaintenanceOut'>

                    <div class='form-floating mb-3'>
                        <input type = 'text' class='form-control' id = 'toolID' name = 'toolID' placeholder='toolID'>
                        <label for = 'toolID'>Tool ID: </label>

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
