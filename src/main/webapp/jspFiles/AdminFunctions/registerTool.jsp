
<%@ page import="java.util.List" %>
<%@ page import="bacit.web.Modules.Certificate" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Register Tool</title>
    <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3' crossorigin='anonymous'>
    <link rel="stylesheet" href="css/misc.css">
</head>
<body>
<jsp:include page="../PageElements/header.jsp"/>
<div class='bd-heading sticky-xl-top align-self-start mb-3 mt-xl-0 mb-xl-2'>
    <h3>Register Tool</h3>
</div>
<form action='toolregister' method='POST' enctype='multipart/form-data'>

    <div class='form-floating mb-3'>
        <input type='text' class='form-control' name='toolname' id="toolname">
        <label for = 'toolname'>Tool Name</label>
    </div>

    <div class='form-floating mb-3'>
        <input type='number' max='1000' min='0' class='form-control' name='pricefirst' id="pricefirst">
        <label for='pricefirst'>Price First Day</label>
    </div>

    <div class='form-floating mb-3'>
        <input type='number' max='1000' min='0' class='form-control' name='priceafter' id="priceafter">
        <label for='priceafter'>Price After First Day</label>
    </div>

    <div class='form-floating mb-3'>
        <select name='toolCategory' id='toolCategory' class="form-select">
            <%
                List<String> categories = (List<String>) request.getAttribute("categories");
                List<Certificate> certificates = (List<Certificate>) request.getAttribute("certificates");

                for(String cat : categories){
                    out.print("<option value = '" + cat + "'> " + cat + " </option>");
                }
            %>
        </select>
        <label for='toolCategory'>Tool Category</label>
    </div>

    <div class='form-floating mb-3'>
        <select name='toolcertificate' id='toolcertificate' class="form-select">
            <%
                for(Certificate cert : certificates){
                    out.print("<option value = '" + cert.getCertificateID() + "'> " + cert.getCertificateName() + " </option>");
                }
            %>
        </select>
        <label for='toolcertificate'>Tool Certificate</label>
    </div>

    <div class='form-floating mb-3'>
        <input type='text' class='form-control' name='tooldesc' id="tooldesc">
        <label for = 'tooldesc'>Tool Description</label>
    </div>

    <input type='file' name='file' id='file'/>
    <br>
    <input type='submit' class='btn btn-primary' style='width: 100%' value='Register Tool'/>
</form>
<jsp:include page="../PageElements/footer.jsp"/>
</body>
</html>

