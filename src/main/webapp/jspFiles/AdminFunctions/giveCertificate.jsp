<%@ page import="java.util.List" %>
<%@ page import="bacit.web.Modules.CertificateModel" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>Give Certificate</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <meta charset="utf-8" />
    <link rel="stylesheet" href="css/list.css">
  <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3' crossorigin='anonymous'>
</head>
<body>
<jsp:include page="../PageElements/header.jsp"/>
<div class='page'>
    <article class='my-3 amv-register' id='floating-labels'>
        <div class='bd-heading sticky-xl-top align-self-start mb-3 mt-xl-0 mb-xl-2'>
            <h3>Give Certificate</h3>
        </div>
            <div class='bd-example'>
                <form action = 'givecertificate' method = 'POST'>
                    <div class='form-floating mb-3'>
                        <input type = 'text' id='userID' name = 'userID' class='form-control' placeholder='name'>
                        <label for = "userID">User ID: </label>
                    </div>
                    <div class='form-floating mb-3'>
                        <input type = 'date' id='accomplishdate' name = 'accomplishdate' class='form-control'>
                        <label for = 'accomplishdate'>Accomplish Date: </label>
                    </div>
                    <div class='form-floating mb-3'>
                        <h3>Choose Certificate:</h3>
                        <select name = 'certificateID' id = 'certificateID' class='form-control'>
                            <label for = 'certificateID'>Tool Certificate: </label>
                            <%
                                List<CertificateModel> certificateNames = (List<CertificateModel>) request.getAttribute("certificates");

                                for (CertificateModel cert : certificateNames) {
                                    out.print("<option value='" + cert.getCertificateName() + "'> " + cert.getCertificateName() + "</option>");
                                }
                            %>
                        </select>
                    </div>
                    <div class='col-12' >
                        <button class='btn btn-primary' style='width: 100%'  type='submit'>Submit</button>
                    </div>
                </form>
            </div>
        </article>
    </div>
<jsp:include page="../PageElements/footer.jsp"/>
</body>
</html>
