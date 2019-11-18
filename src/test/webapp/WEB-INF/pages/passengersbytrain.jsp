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
    <title>Passengers by train</title>
</head>

<body>
<div class="container" id="centerform" style="top:15%">
    <c:set var = "TrainNumber" value = "${TrainNumber}"/>
    <div class="row" style="margin-top:7px; margin-bottom:5px; text-align:center">
        <div class="col-12" style="text-align:center;">
            <h3>Train ${TrainNumber} passengers:</h3>
        </div>
    </div>
    <div class="row" style="margin-left:1px; margin-right:1px; margin-bottom:10px; border: 1px solid silver;">
        <div class="col-4 my-auto">
            <span style="font-weight:700; margin-right:5px">First name</span>
        </div>
        <div class="col-4 my-auto">
            <span style="font-weight:700; margin-right:5px">Second name</span>
        </div>
        <div class="col-4 my-auto">
            <span style="font-weight:700">Birth date</span>
        </div>
    </div>
    <c:forEach var="PassengerInfo" items="${PassengersList}" varStatus="i">
        <div class="row" style="margin-left:1px; margin-right:1px; margin-bottom:10px; border: 1px solid silver;">
            <div class="col-4 my-auto">
                <span style="font-weight:500; margin-right:5px">${PassengerInfo.firstName}</span>
            </div>
            <div class="col-4 my-auto">
                <span style="font-weight:500; margin-right:5px">${PassengerInfo.secondName}</span>
            </div>
            <div class="col-4 my-auto">
                <span style="font-weight:500">${PassengerInfo.birthDate}</span>
            </div>
        </div>
    </c:forEach>
    <c:set var = "Page" value = "${Page}"/>
    <c:set var = "PagesCount" value = "${PagesCount}"/>
    <div class="row" style="margin-left:1px; margin-right:1px;">
        <div class="col-1 offset-2 my-auto text-center">
            <form action="/passengersbytrain" method="POST">
                <input type="text" name="train" value="${TrainNumber}" class="form-control" style="display:none">
                <button type="submit" name="page" value="${Page - 1}" class="btn btn-link" style="font-weight:900; ${Page == 1 ? "display:none" : ""}"><<</button>
            </form>
        </div>
        <div class="col-3 offset-1 offset-right-1 my-auto text-center">
            <form action="/passengersbytrain" method="POST">
                <input type="text" name="train" value="${TrainNumber}" class="form-control" style="display:none">
                <button type="submit" name="page" value="${Page}" class="btn btn-link" style="font-weight:500; color:black; text-decoration:none">Page ${Page} of ${PagesCount}</button>
            </form>
        </div>
        <div class="col-1 my-auto text-center">
            <form action="/passengersbytrain" method="POST">
                <input type="text" name="train" value="${TrainNumber}" class="form-control" style="display:none">
                <button type="submit" name="page" value="${Page + 1}" class="btn btn-link" style="font-weight:900; ${Page == PagesCount ? "display:none" : ""}">>></button>
            </form>
        </div>
        <div class="col-1 offset-2">
            <a href="/adminmain" class="btn btn-secondary" role="button">Back</a>
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