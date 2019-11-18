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
    <title>Success</title>
</head>

<body>
<div class="container" id="centerform">
    <c:set var = "TrainNumber" value = "${TrainNumber}"/>
    <c:set var = "LastStation" value = "${LastStation}"/>
    <c:set var = "StopTime" value = "${StopTime}"/>
    <div style="text-align: center; margin-top:5px;">
        <h3>Station for train ${TrainNumber} added!</h3>
    </div>
    <div style="text-align: center; margin-top:5px; margin-bottom:20px;">
        <h6>Last added station: ${LastStation}, ${StopTime}</h6>
    </div>
    <div class="row">
        <form action="/addstationfortrain" method="POST" class="col-sm-7" style="margin-bottom:10px">
            <button type="submit" name="train" value="${TrainNumber}" class="btn btn-success col-sm-12">More stations for this train</button>
        </form>
        <form action="/addtrain" method="GET" class="col-sm-5" style="margin-bottom:10px">
            <button type="submit" class="btn btn-success col-sm-12">Add one more train</button>
        </form>
    </div>
    <div class="row">
        <form action="/adminmain" method="GET" class="col-sm-12" style="margin-bottom:10px">
            <button type="submit" class="btn btn-primary col-sm-12">Main page</button>
        </form>
    </div>
</div>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>

</html>