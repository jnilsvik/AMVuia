<%--
  Created by IntelliJ IDEA.
  User: Joachim
  Date: 04.11.2021
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType='text/html;charset=UTF-8'%>
<html>
<head>
    <title>Title</title>

    <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3' crossorigin='anonymous'>
    <link rel="stylesheet" href="css/misc.css">
</head>
<body>
<jsp:include page="_head_nav.jsp"/>
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
                        <a href='register'>Don't have an account? Register here!</a>
                    </div>
                </form>
            </div>
        </div>
    </article>
</div>
<jsp:include page="_footer.jsp"/>