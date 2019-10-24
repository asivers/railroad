<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: siver
  Date: 20.10.2019
  Time: 17:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="iso 8859-5">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link href="res/style.css" rel="stylesheet" type="text/css" />
    <title>Train schedule</title>
</head>

<body>
<div class="container" id="centerform" style="top:15%">
    <c:set var = "TrainNumber" value = "${TrainNumber}"/>
    <div class="row" style="margin-top:7px; margin-bottom:5px; text-align:center">
        <div class="col-12" style="text-align:center;">
            <h3>Train ${TrainNumber} stops:</h3>
        </div>
    </div>
    <c:forEach var="StationTime" items="${StationTimeList}" varStatus="i">
        <div class="row" style="margin-left:1px; margin-right:1px; margin-bottom:10px; border: 1px solid silver;">
            <div class="col-4 offset-2 my-auto">
                <form action="/trainsbystation" method="POST">
                    <button type="submit" name="station" value="${StationTime.stationName}" class="btn btn-link" style="font-weight:500; margin-bottom:-15px">${StationTime.stationName}</button>
                </form>
            </div>
            <div class="col-4 offset-2 my-auto">
                <span style="font-weight:500">${StationTime.time}</span>
            </div>
        </div>
    </c:forEach>
    <div class="row" style="margin-left:1px; margin-right:1px; margin-bottom:10px">
        <div class="col-8 offset-2 my-auto text-center">
            <a href="#" style="font-weight:700; display:none"><<</a>
            <span style="font-weight:500; margin-left:5px; margin-right:5px;">Page 1 of 1</span>
            <a href="#" style="font-weight:700">>></a>
        </div>
        <div class="col-2">
            <a href="/usermain" class="btn btn-secondary" role="button">Back</a>
        </div>
    </div>
</div>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>

</html>