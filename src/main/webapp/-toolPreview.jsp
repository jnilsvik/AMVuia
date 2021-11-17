<%@ page import="bacit.web.a_models.ToolModel" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.DayOfWeek" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3' crossorigin='anonymous'>
    <link rel="stylesheet" href="css/misc.css">
    <style>
        .container{
            align-self: center;
            width: 1200px;
        }
        .pm {
            padding: 10px;
            border: 1px solid black;
        }
        .container_product {
            display: flex;
            flex-wrap: wrap;

            justify-content: center;
            align-items: center;
            text-align: center;
        }
        .container_product div{
            width: 33%;
        }

        * {box-sizing: border-box;}
        ul {list-style-type: none;}
        body {font-family: Verdana, sans-serif;}

        .month {
            padding: 70px 25px;
            width: 100%;
            background: #1abc9c;
            text-align: center;
        }

        .month ul {
            margin: 0;
            padding: 0;
        }

        .month ul li {
            color: white;
            font-size: 20px;
            text-transform: uppercase;
            letter-spacing: 3px;
        }

        .month .prev {
            float: left;
            padding-top: 10px;
        }

        .month .next {
            float: right;
            padding-top: 10px;
        }

        .weekdays {
            margin: 0;
            padding: 10px 0;
            background-color: #ddd;
        }

        .weekdays li {
            display: inline-block;
            width: 13.6%;
            color: #666;
            text-align: center;
        }

        .days {
            padding: 10px 0;
            background: #eee;
            margin: 0;
        }

        .days li {
            list-style-type: none;
            display: inline-block;
            width: 13.6%;
            text-align: center;
            margin-bottom: 5px;
            font-size:12px;
            color: #777;
        }

        .days li .active {
            padding: 5px;
            background: #1abc9c;
            color: white !important
        }

        /* Add media queries for smaller screens */
        @media screen and (max-width:720px) {
            .weekdays li, .days li {width: 13.1%;}
        }

        @media screen and (max-width: 420px) {
            .weekdays li, .days li {width: 12.5%;}
            .days li .active {padding: 2px;}
        }

        @media screen and (max-width: 290px) {
            .weekdays li, .days li {width: 12.2%;}
        }
</style>
</head>
<body>
<jsp:include page="_head_nav.jsp"/>
    <section class="" style="width: 100%">
        <div class="container">
            <%
                ToolModel tool = (ToolModel) request.getAttribute("tool");
                out.print("<h1> " + tool.getToolName().replaceAll("_", " ") +
                        " from the Category: " + tool.getToolCategory().replaceAll("_", " ") + "</h1>");
            %>
            <div class='container_product'>
                <div class="product_img pm">
                    <%out.print("<img src = 'img/amv.png' width = '156' heigth = '151'>");%>
                </div>
                <div class="product_desc pm">
                    <%out.print("<p>" + tool.getDescription()+"</p>");%>
                </div>
                <div class="product_price pm">
                    <%
                        out.print("<h2>Price the first day: " +  tool.getPriceFirst() + "</h2>");
                        out.print("<h2>Price after the first day: " +  tool.getPriceAfter() + "</h2>");
                    %>
                </div>
            </div>

            <!--
            <div class="container_booking">
                <div class="booking_calender pm">
                    <div class="month">
                        <ul>
                            <li class="prev">&#10094;</li>
                            <li class="next">&#10095;</li>
                            <li>
                                August<br>
                                <span style="font-size:18px">2021</span>
                            </li>
                        </ul>
                    </div>

                    <ul class="weekdays">
                        <li>Mo</li>
                        <li>Tu</li>
                        <li>We</li>
                        <li>Th</li>
                        <li>Fr</li>
                        <li>Sa</li>
                        <li>Su</li>
                    </ul>

                    <ul class="days">
                        <li>1</li>
                        <li>2</li>
                        <li>3</li>
                        <li>4</li>
                        <li>5</li>
                        <li>6</li>
                        <li>7</li>
                        <li>8</li>
                        <li>9</li>
                        <li><span class="active">10</span></li>
                        <li>11</li>
                        <li>12</li>
                        <li>13</li>
                        <li>14</li>
                        <li>15</li>
                        <li>16</li>
                        <li>17</li>
                        <li>18</li>
                        <li>19</li>
                        <li>20</li>
                        <li>21</li>
                        <li>22</li>
                        <li>23</li>
                        <li>24</li>
                        <li>25</li>
                        <li>26</li>
                        <li>27</li>
                        <li>28</li>
                        <li>29</li>
                        <li>30</li>
                        <li>31</li>
                    </ul>
                </div>
                <div class="booking_booking pm" style="text-align: center">
                    <form action='b' method='post' >
                        <div>
                            <label for='dateStart'> start: </label>
                            <input type='date' name='dateStart' id='dateStart' min='' required>
                        </div>
                        <div>
                            <label for='dateEnd'> until: </label>
                            <input type='date' name='dateEnd' id='dateEnd' min='' max='' required>
                        </div>
                        <div>
                            <input type='hidden' name='uID' value=''>
                            <input type='hidden' name='uID' value=''>
                            <input type='submit'>
                        </div>
                    </form>
                </div>
            </div>
            -->
            <div class="container_calendar">

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
                            List<LocalDate> dates = (List<LocalDate>) request.getAttribute("dates");
                            LocalDate currentDate = LocalDate.now();
                            currentDate = currentDate.with(DayOfWeek.MONDAY);
                            int days = 0;
                            int resetWeek = 1;
                            DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                            while (days <= 120) {
                                //sets colour dependant on availability
                                String status = "Available";
                                String color = "#00FF00";
                                if (dates.contains(currentDate)) {
                                    status = "Booked";
                                    color = "#FF0000";
                                }
                                //Print the actual line
                                String currentDateFormat = currentDate.format(formatters);
                                out.print("<td bgcolor=" + color + ">" + currentDateFormat + "<br>" + status + "</td>");
                                //resets the week (amount of days per coloums
                                if (resetWeek == 7) {
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    resetWeek = 0;
                                }
                                currentDate = currentDate.plusDays(1);
                                days++;
                                resetWeek++;
                            }
                        %>

                    </tr>
                </table>

            </div>
            <div class="container_book">
                <form action = 'toolbooking' method = 'POST'>
                    <%
                        out.print("<input type = 'hidden' value = '" + tool.getToolID() + "' name = 'tools'>");
                        out.print("<label for = 'date'> Choose start date:</label>");
                        out.print("<input type = 'hidden' value = '" + request.getAttribute("email") + "' name = 'email'>");
                    %>
                    <label for = 'date'> Choose start date:</label>"
                    <input type = 'date' id = 'date' name = 'date'><br>

                    <label for='days'>Choose duration:</label>
                    <select id='days' name = 'days'>
                        <option value='1'> 1 Day</option>
                        <option value='2'> 2 Days</option>
                        <option value='3'> 3 Days</option>
                    </select>

                    <input type = 'submit' value = 'Submit'>
                </form>
            </div>
        </div>
    </section>
</body>
</html>