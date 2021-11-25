<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 14.11.2021
  Time: 13:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Help</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta charset="utf-8"/>
    <link rel="stylesheet" href="css/list.css">
    <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet'
          integrity='sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3' crossorigin='anonymous'>
</head>
<body>
<jsp:include page="../PageElements/header.jsp"/>
<h2>How can we help?</h2>
<form action='https://script.google.com/a/hsu.edu.hk/macros/s/AKfycbxfqBQo0UkCet0kkVLK8CDMtPBpann19xu0mI10/exec'
      method='POST' data-email='cheuklong20010212@gmail.com'>
    <label for="name">Name: </label><br>
    <textarea id='name' name='name' rows='2' cols='50'></textarea><br>
    <label for="email">Email: </label><br>
    <textarea id='email' name='email' rows='2' cols='50'></textarea><br>
    <label for="message">Message: </label><br>
    <textarea id='message' name='message' rows='10' cols='50'></textarea><br>
    <input type='submit' value='Send Help!'>
</form>
<jsp:include page="../PageElements/footer.jsp"/>
</body>
</html>
