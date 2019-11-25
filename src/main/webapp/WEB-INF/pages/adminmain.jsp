<%--
  Created by IntelliJ IDEA.
  User: siver
  Date: 20.10.2019
  Time: 17:45
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
    <title>Main Admin page</title>
</head>

<body>
<div class="container" id="centerform" style="top:30%">
    <div style="text-align: center; margin-top:5px; margin-bottom:20px;">
        <h3>Choose option</h3>
    </div>
    <div class="row" style="margin-left:1px; margin-right:1px">
        <form action="/choosetrain" method="GET" class="col-sm-6" style="margin-bottom:10px">
            <button type="submit" class="btn btn-primary col-sm-12">Passengers by train</button>
        </form>
        <form action="/alltrains" method="POST" class="col-sm-6" style="margin-bottom:10px">
            <input type="text" name="page" value="1" class="form-control" style="display:none">
            <button type="submit" class="btn btn-primary col-sm-12">All trains</button>
        </form>
    </div>
    <div class="row" style="margin-left:1px; margin-right:1px">
        <form action="/addtrain" method="GET" class="col-sm-6" style="margin-bottom:10px">
            <button type="submit" class="btn btn-success col-sm-12">Add/Update train</button>
        </form>
        <form action="/addstation" method="GET" class="col-sm-6" style="margin-bottom:10px">
            <button type="submit" class="btn btn-success col-sm-12">Add station</button>
        </form>
    </div>
    <div class="row" style="margin-left:1px; margin-right:1px">
        <form action="/dologout" method="POST" class="col-sm-12" style="margin-bottom:10px">
            <button type="submit" class="btn btn-secondary col-sm-12">Exit</button>
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