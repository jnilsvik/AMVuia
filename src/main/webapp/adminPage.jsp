<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 10.11.2021
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<head>
<title>Admin Functions</title>
<meta name=viewport content=width=device-width/>
 <meta charset=utf-8 />
<style>
.bigbutton {
display: block;
width: 50%;
border: none;
background-color: #ffb300;
color: black;
padding: 14px 28px;
font-size: 16px;
cursor: pointer;
margin: auto;
text-align: center; }
.bigbutton:hover {
background-color: #11165a;
color: white; }
</style>
</head>
<body>
<a href=toolregister> <span class=bigbutton> Register Tool </span></a>
<br>
<a href=toolmaintenance> <span class=bigbutton> Tool Maintenance </span></a>
<br>
<a href=el> <span class=bigbutton> List of Users </span></a>
<br>
<a href=payment> <span class=bigbutton> Payment Report </span></a>
<br>
<a href=givecertificate> <span class=bigbutton> Give Certificate </span></a>
</body>