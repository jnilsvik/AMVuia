<%@ page import="java.util.ArrayList" %>
<%@ page import="bacit.web.Modules.ToolModel" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Tool List</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta charset="utf-8" />
    <link rel="stylesheet" href="css/tabelsort.css">
</head>
<body>
<jsp:include page="../PageElements/header.jsp"/>

    <h3>Tools Listing</h3>
    <table class="table-sortable">
        <thead>
            <tr>
                <th>Name</th>
                <th>Category</th>
                <th>priceFirst</th>
                <th>priceAfter</th>
                <th>in Maintenance</th>
                <th>CertificateID</th>
            </tr>
        </thead>
        <tbody>
        <%
            ArrayList<ToolModel> model = (ArrayList<ToolModel>) request.getAttribute("toolList");
            if(model != null) {
                for (ToolModel tm : model){
                    out.print(
                            "<tr>" +
                            "<td>" + tm.getToolName() + "</td>" +
                            "<td>" + tm.getToolCategory() + "</td>" +
                            "<td>" + tm.getPriceFirst() + "</td>" +
                            "<td>" + tm.getPriceAfter() + "</td>" +
                            "<td>" + tm.getMaintenance() + "</td>" +
                            "<td>" + tm.getCertificateID() + "</td>" +
                            "</tr>");
                }
            }
        %>
        </tbody>
    </table>

    <script src="js/tabelsort.js"></script>
<jsp:include page="../PageElements/footer.jsp"/>
</body>
</html>
