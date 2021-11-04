<%--
  Created by IntelliJ IDEA.
  User: Joachim
  Date: 04.11.2021
  Time: 16:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Forgot password</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body style="
    background-image: url(https://media.discordapp.net/attachments/472062607646261249/702987431653277705/unknown.png);
    background-repeat: no-repeat;
    background-attachment: fixed;
    background-size: 100% 100%;">
<article class="my-3" id="floating-labels" style="margin: 20%"> <!-- this is temp to genter-->
    <div class="bd-heading sticky-xl-top align-self-start mt-5 mb-3 mt-xl-0 mb-xl-2">
        <h3>Forgot password</h3>
    </div>

    <div>
        <div class="bd-example">
        <form action="">
            <div class="form-floating mb-3">

                <input type="password" class="form-control" id="floatingNewPassword1" placeholder="New password">
                <label for="floatingNewPassword1">Password</label>
            </div>
            <div class="form-floating mb-3">
                <input type="password" class="form-control" id="floatingNewPassword2" placeholder="repeat">
                <label for="floatingNewPassword2">Password</label>
            </div>
            <div class="col-12">
                <button class="btn btn-primary" type="submit">Submit form</button>
            </div>

        </form>
    </div>
    </div>
</article>
</body>
</html>
