<%--
  Created by IntelliJ IDEA.
  User: Joachim
  Date: 04.11.2021
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType='text/html;charset=UTF-8' session="false" %>

<html>
<head>
    <title>Login</title>
    <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3' crossorigin='anonymous'>
    <link rel="stylesheet" href="css/misc.css">
    <header class="p-3 border-bottom amv-bgc-y" style="font-family: Arial,Helvetica,sans-serif">
        <div class="container">
            <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
                <a href="login" class="d-flex align-items-center mb-2 mb-lg-0 text-dark text-decoration-none">
                    <img src="img/amv.png" class="bi me-2" height="40" role="img" aria-label="Bootstrap" alt="AMV logo">
                </a>
                <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                    <li><a href="help" class="nav-link px-2 amv-txc-b">FAQ & HELP</a></li>
                </ul>
                <div class="text-end">
                </div>
            </div>
        </div>
    </header>
</head>
<body>

<div class='page'>
    <article class='my-3' id='floating-labels'>
        <div class='bd-heading sticky-xl-top align-self-start mb-3 mt-xl-0 mb-xl-2'>
            <h3>Login</h3>
        </div>

        <div>
            <div class='bd-example'>
                <form action='login' method='POST'>
                    <div class='form-floating mb-3'>
                        <input type='email' class='form-control' id='email' name='email' placeholder='name@example.com'>
                        <label for='email'>Email address</label>
                    </div>
                    <div class='form-floating mb-3'>
                        <input type='password' class='form-control' id='pass' name='pass' placeholder='Password'>
                        <label for='pass'>Password</label>
                    </div>
                    <div class='col-12'>
                        <button class='btn btn-primary' type='submit'>Login</button>
                    </div>
                    <div style='text-align:center'>
                        <p>You don't have an account? Just contact the administrator</p>
                    </div>
                </form>
            </div>
        </div>
    </article>
</div>
<jsp:include page="../PageElements/footer.jsp"/>