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
    <style> .page {
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
</style>

</head>
<body>
<div class='page'>
    <article class='my-3 amv-register' id='floating-labels'>
        <div class='bd-heading sticky-xl-top align-self-start mb-3 mt-xl-0 mb-xl-2'>
            <h3>Register</h3>
        </div>

        <div>
            <div class='bd-example'>
                <form action=''>
                    <div class='form-floating mb-3'>
                        <input type='email' class='form-control' id='email' name='email' placeholder='name@example.com'>
                        <label for='email'>Email address</label>
                    </div>
                    <div class='form-floating mb-3'>
                        <input type='password' class='form-control' id='password' name='password' placeholder='Password'>
                        <label for='password'>Password</label>
                    </div>
                    <div class='form-floating mb-3'>
                        <input type='text' class='form-control' id='fName' name='fname'placeholder='name'>
                        <label for='fName'>First name</label>
                    </div>
                    <div class='form-floating mb-3'>
                        <input type='text' class='form-control' id='lName' name='lname' placeholder='name'>
                        <label for='lName'>Last name</label>
                    </div>
                    <div class='form-floating mb-3'>
                        <input type='tel' class='form-control' id='phone' name='phone' placeholder='98979695'>
                        <label for='phone'>Phone Number</label>
                    </div>
                    <div class='mb-3 form-check'>
                        <input type='checkbox' class='form-check-input' id='admin' name='admin'>
                        <label class='form-check-label' for='admin'>Administrator</label>
                        <input type='checkbox' class='form-check-input' id='union' name='union'>
                        <label class='form-check-label' for='union'>Union member</label>
                    </div>
                    <div class='col-12' >
                        <button class='btn btn-primary' style='width: 100%' type='submit'>Register</button>
                    </div>
                    <div style='text-align:center'>
                        <a href='login'>Already have an account? Login here!</a>
                    </div>
                </form>
            </div>
        </div>
    </article>
</div>
<jsp:include page='_footer.jsp'/>