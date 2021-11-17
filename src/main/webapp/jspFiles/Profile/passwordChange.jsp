<%--
  Created by IntelliJ IDEA.
  User: Joachim
  Date: 11.11.2021
  Time: 17:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Payments</title>
    <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3' crossorigin='anonymous'>
    <link rel="stylesheet" href="css/misc.css">
    <style> table, th, td { border:1px solid black;} </style>
</head>
<body>
<jsp:include page="../PageElements/header.jsp"/>
<div class='page'>
    <article class='my-3 amv-register' id='floating-labels'>
        <div class='bd-heading sticky-xl-top align-self-start mb-3 mt-xl-0 mb-xl-2'>
            <h3>Change password</h3>
        </div>
        <div>
            <div class='bd-example'>

<form action='changepassword' method='POST'>
    <div class='form-floating mb-3'>
        <input type='text' class='form-control' name='oldpass' id="oldpass" placeholder='name' required><br>
    <label for='oldpass'>Old password: </label><br>

    </div>
    <div class='form-floating mb-3'>
        <input type='text' class='form-control' name='newpass1'id="newpass1" placeholder='name' required><br>
    <label for='newpass1'>New password: </label><br>
    </div>
        <div class='form-floating mb-3'>
            <input type='text' class='form-control' name='newpass2'id="newpass2" placeholder='name' required><br>
    <label for='newpass2'>Repeat new password: </label><br>

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

