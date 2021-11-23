<%@ page import="bacit.web.Modules.UserModel" %><%--
  Created by IntelliJ IDEA.
  User: Joachim
  Date: 23.11.2021
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="jspFiles/PageElements/header.jsp"/>
<section>
    <div>
        left side (preview)
        <%
            UserModel user = (UserModel) request.getAttribute("userDetailsOld");
            out.print(
                    "            <div class='form-floating mb-3'>\n" +
                    "                <p>"+user.getEmail() +"</p>\n" +
                    "            </div>\n" +
                    "            <div class='form-floating mb-3'>\n" +
                    "                <p>"+user.getFirstname()+"</p>\n" +
                    "            </div>\n" +
                    "            <div class='form-floating mb-3'>\n" +
                    "                <p>"+user.getLastname()+"</p>\n" +
                    "            </div>\n" +
                    "            <div class='form-floating mb-3'>\n" +
                    "                <p>"+user.getPhoneNumber()+"</p>\n" +
                    "            </div>\n" +
                    "            <div class='form-floating mb-3'>\n" +
                    "                <p>"+user.isUnionMember()+"</p>\n" +
                    "            </div>\n" +
                    "            <div class='form-floating mb-3'>\n" +
                    "                <p>"+user.isUserAdmin()+"</p>\n" +
                    "            </div>");

        %>

    </div>
    <div>
        right side (edit/change)
        <form action="userUpdate" method="post">
            <div class='form-floating mb-3'>
                <input type='email' class='form-control' id='email' name='email' placeholder='name@example.com'>
                <label for='email'>Email address</label>
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
</section>

</body>
</html>
