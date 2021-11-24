<%@ page import="bacit.web.Modules.ToolModel" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.DayOfWeek" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.temporal.TemporalAdjuster" %>
<%@ page import="java.time.temporal.TemporalAdjusters" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Tool Details</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="css/list.css">
    <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3' crossorigin='anonymous'>
</head>
<body>
<jsp:include page="../PageElements/header.jsp"/>

<div class ="centerContent">
<div>
    <%
        ToolModel tool = (ToolModel) request.getAttribute("tool");
        List<LocalDate> dates = (List<LocalDate>) request.getAttribute("dates");
        out.print("<h1> " + tool.getToolName().replaceAll("_", " ") + "</h1>" +
                "<h1> from the Category: " + tool.getToolCategory().replaceAll("_", " ") + "</h1>");
        // TODO: 22.11.2021 -joachim: this need to be changed to pic. path
        out.print("<img src = 'img/amv.png' width = '156' height = '151'>");
        out.print("<h2>Price the first day: " +  tool.getPriceFirst() + "</h2>");
        out.print("<h2>Price after the first day: " +  tool.getPriceAfter() + "</h2>");
        if(tool.getDescription() == null) {
            out.print("<p></p>");
        }
        else {
            out.print("<p>" + tool.getDescription() + "</p>");
        }

    %>
    <form action = 'toolbooking' method = 'POST'>
        <%
            out.print("<input type = 'hidden' value = '" + tool.getToolID() + "' name = 'tools' readonly>");
            out.print("<label for = 'date'> Choose start date:</label>");
        %>
        <%
            request.setAttribute("today", LocalDate.now());
        %>
        <input type = 'date' id = 'date' name = 'date' min = '${today}'><br>
        <br>
        <label for='days'>Choose how many days:</label>

        <select id='days' name = 'days'>
            <option value='1'> 1 Day</option>
            <option value='2'> 2 Days</option>
            <option value='3'> 3 Days</option>
            <option value='4'> 4 Days</option>
        </select><br><br>

        <input type = 'hidden' value = '" + email + "' name = 'email' readonly>
        <input type = 'submit' value = 'Submit'>
    </form>



    <h2>Available dates</h2>
    <table>
        <tr>
            <th>Monday</th>
            <th>Tuesday</th>
            <th>Wednesday</th>
            <th>Thursday</th>
            <th>Friday</th>
            <th>Saturday</th>
            <th>Sunday</th>
        </tr>
        <tr>

            <%
                LocalDate currentDate = LocalDate.now();
                currentDate = currentDate.with(TemporalAdjusters.firstDayOfMonth());
                currentDate = currentDate.with(DayOfWeek.MONDAY);
                LocalDate dateToday = LocalDate.now();
                int days = 0;
                DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                while (days <= 180) {
                    //sets colour dependant on availability
                    String status = "Available";
                    String color = "#00FF00";
                    if (dates.contains(currentDate)) {
                        status = "Booked";
                        color = "#FF0000";
                    }
                    if (currentDate.isBefore(dateToday)) {
                        status = "Passed";
                        color = "#808080";
                    }
                    //Print the actual line
                    if (currentDate == currentDate.with(DayOfWeek.MONDAY)) {
                        out.print("</tr>");
                        out.print("<tr>");
                    }
                    String currentDateFormat = currentDate.format(formatters);
                    out.print("<td bgcolor=" + color + ">" + currentDateFormat + "<br>" + status + "</td>");
                    //resets the week (amount of days per columns)
                    currentDate = currentDate.plusDays(1);
                    days++;
                }
            %>
        </tr>
    </table>

</div>
</div>
<jsp:include page="../PageElements/footer.jsp"/>
</body>
</html>