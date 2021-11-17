<%@ page import="bacit.web.AdminFunctions.AdminAccess" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet'
          integrity='sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3' crossorigin='anonymous'>
    <link rel="stylesheet" href="css/misc.css">
</head>

<body>
<header class="p-3 border-bottom amv-bgc-y" style="font-family: Arial,Helvetica,sans-serif">
    <div class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
            <a href="jspFiles/index.jsp" class="d-flex align-items-center mb-2 mb-lg-0 text-dark text-decoration-none">
                <img src="img/amv.png" class="bi me-2" height="40" role="img" aria-label="Bootstrap" alt="AMV logo">
            </a>

            <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                <li><a href="xtl" class="nav-link px-2 amv-txc-b">TOOL OVERVIEW</a></li>
                <li><a href="profile" class="nav-link px-2 amv-txc-b">RENTALS</a></li>
                <li><a href="help" class="nav-link px-2 amv-txc-b">FAQ & HELP</a></li>
                <%
                    String email = (String) session.getAttribute("email");
                    if(AdminAccess.accessRights(email)){
                        out.print("<li><a href=\"admin\" class=\"nav-link px-2 amv-txc-b\">ADMIN PAGE</a></li>");
                    }
                %>
            </ul>

            <div class="text-end">
                <%
                    if(email == null) out.print("<a href='login' class='btn amv-btn'>LOGIN</a>");
                    else out.print("<a href='logout' class='btn amv-btn'>LOGOUT</a>");
                %>
            </div>
        </div>
    </div>
</header>
</body>
</html>