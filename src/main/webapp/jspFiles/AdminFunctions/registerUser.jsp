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
    <title>Register User</title>
    <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3' crossorigin='anonymous'>
    <link rel="stylesheet" href="css/misc.css">
</head>

<body>
<jsp:include page="../PageElements/header.jsp"/>
<div class='page'>
    <article class='my-3' id='floating-labels'>
        <div class='bd-heading sticky-xl-top align-self-start mb-3 mt-xl-0 mb-xl-2'>
            <h3>Register</h3>
        </div>

        <div>
            <div class='bd-example'>
                <form action='register' method="post">
                    <div class='form-floating mb-3'>
                        <input type='email' class='form-control' id='email' name='email' placeholder='name@example.com'>
                        <label for='email'>Email address</label>
                    </div>
                    <div class='form-floating mb-3'>
                        <input type='password' class='form-control' id='password' name='password' placeholder='Password'>
                        <label for='password'>Password</label>
                    </div>
                    <div class='form-floating mb-3'>
                        <input type='text' class='form-control' id='fName' name='fname' placeholder='name'>
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
                    </div>
                    <div class='mb-3 form-check'>
                        <input type='checkbox' class='form-check-input' id='union' name='union'>
                        <label class='form-check-label' for='union'>Union member</label>
                    </div>
                    <div class='col-12' >
                        <button class='btn btn-primary' style='width: 100%' type='submit'>Register</button>
                    </div>
                </form>
            </div>
        </div>
    </article>
</div>
<jsp:include page="../PageElements/footer.jsp"/>
</body>
</html>