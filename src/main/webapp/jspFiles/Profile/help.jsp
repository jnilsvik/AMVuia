<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 14.11.2021
  Time: 13:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" session="false" %>
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
                    <li><a href="login" class="nav-link px-2 amv-txc-b">GO BACK</a></li>
                </ul>
                <div class="text-end">
                </div>
            </div>
        </div>
    </header>
</head>
<body>
<h2>How can we help?</h2>
<style>
    .hide {
        position: absolute;
        top: -1px;
        left: -1px;
        width: 1px;
        height: 1px;
    }
</style>

<iframe name="hiddenFrame" class="hide"></iframe>
<form action='https://script.google.com/a/hsu.edu.hk/macros/s/AKfycbxfqBQo0UkCet0kkVLK8CDMtPBpann19xu0mI10/exec'
      method='POST' data-email='cheuklong20010212@gmail.com' target="hiddenFrame">
    <label for="name">Name: </label><br>
    <textarea id='name' name='name' rows='2' cols='50'></textarea><br>
    <label for="email">Email: </label><br>
    <textarea id='email' name='email' rows='2' cols='50'></textarea><br>
    <label for="message">Message: </label><br>
    <textarea id='message' name='message' rows='10' cols='50'></textarea><br><br>
    <input type='submit' value='Send Help!'>
</form>
<jsp:include page="../PageElements/footer.jsp"/>
</body>
</html>
