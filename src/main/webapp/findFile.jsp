<%--
  Created by IntelliJ IDEA.
  User: Joachim
  Date: 29.10.2021
  Time: 10:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Find Tool by id in the database :-)</h1>
    <form action='GetTool' method='GET'>
        <label for='toolID'>Tool ID:</label>
        <input type='number' name='toolID'/>
        <input type='submit' />
    </form>

    <h1>Find Tool by name in the database :-)</h1>
    <form action='GetTool2' method='GET'>
        <label for='toolName'>Tool ID:</label>
        <input type='text' name='toolName'/>
        <input type='submit' />
    </form>
</body>
</html>
