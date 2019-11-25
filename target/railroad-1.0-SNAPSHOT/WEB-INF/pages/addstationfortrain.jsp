<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: siver
  Date: 20.10.2019
  Time: 17:47
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
    <title>Add station for train</title>
</head>

<body>
<div class="container" id="centerform" style="top:35%">
    <c:set var = "TrainNumber" value = "${TrainNumber}"/>
    <div style="text-align: center; margin-top:5px; margin-bottom:20px;">
        <h3>Add station for train ${TrainNumber}</h3>
    </div>
    <form action="/finishaddstationfortrain" method="POST" style="margin-top:10px; margin-bottom:10px">
        <div class="form-group row">
            <label class="col-sm-3 col-form-label">Station</label>
            <div class="col-sm-9">
                <input type="text" name="train" value="${TrainNumber}" class="form-control" style="display:none">
                <input type="text" name="station" value="" class="form-control">
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-3 col-form-label">Stop time</label>
            <div class="col-sm-9">
                <input type="time" name="stopTime" value="00:00" class="form-control">
            </div>
        </div>
        <div class="row" style="margin-left:1px; margin-right:1px">
            <button type="submit" class="btn btn-success col-sm-3 offset-sm-4" style="margin-bottom:1px;">Add</button>
            <a href="/adminmain" class="btn btn-secondary col-sm-3 offset-sm-1 offset-right-sm-1" role="button" style="margin-bottom:1px;">Back</a>
        </div>
    </form>
</div>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>

</html>