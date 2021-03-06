<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Homework-44</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="CSS/mainstyle.css">
</head>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Raleway:wght@100;400;600;700;800&display=swap" rel="stylesheet">
<body>
<header class="header">
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container">
            <a class="navbar-brand me-5" href="index.html">
                <img src="images/booklogo.jpg" alt="Company Library">
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse justify-content-between ms-2" id="navbarNav">
                <ul class="navbar-nav text-uppercase fw-bold">
                    <li class="nav-item me-4">
                        <a class="nav-link" href="/books">Books</a>
                    </li>
                    <li class="nav-item me-4">
                        <a class="nav-link" href="/employees">Employees</a>
                    </li>
                    <li class="nav-item me-4">
                        <a class="nav-link" href="index.html">About us</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="index.html">Contacts</a>
                    </li>
                </ul>
                <hr class="line">
                <form action="/register" >
                    <button class="btn-signup">Registration</button></form>
                <form action="/login">
                    <button class="btn-signup">Sign Up</button></form>
            </div>
        </div>
    </nav>
</header>
<div id="mainCarousel" class="carousel slide" data-bs-ride="carousel">
    <div class="carousel-indicators">
        <button type="button" data-bs-target="#mainCarousel" data-bs-slide-to="0" class="active" aria-current="true"
                aria-label="Slide 1"></button>
        <button type="button" data-bs-target="#mainCarousel" data-bs-slide-to="1" aria-label="Slide 2"></button>
        <button type="button" data-bs-target="#mainCarousel" data-bs-slide-to="2" aria-label="Slide 3"></button>
    </div>
    <div class="carousel-inner">
        <div class="carousel-item slideimg active">
            <img src="images/header.jpg" class="d-block w-100" alt="...">
            <div class="carousel-caption d-none d-sm-block">
                <h1 class="maintext">If you want to be acquainted with the past and the present, you must read five cartloads of books</p></h1>
            </div>
        </div>
    </div>
</div>
<div class="container">
    <div class="card py-5 pe-5 ps-5 border-0">
        <div class="row justify-content-between row-cols-sm-1 row-cols-md-2 g-0">
            <div class="col-md-4 findjob">
                <figure class="figure">
                    <img src="${imagePath}" class="imagePath figure-img img-fluid rounded" alt="...">
                    <figcaption class="figure-caption text-center">${name}</figcaption>
                </figure>
            </div>
            <div class="col-md-5">
                <h3 class="card-title sectiontitle text-center py-5 mb-0"><span class="greentitle">${name}</span></h3>
                <p class="card-text cardtext">${about}
                    ${description}
                </p>
                <#if status == "available">
                    <form action="/book" method="post">
                        <input type="number" name="id" value="${id}" hidden>
                        <input type="text" name="email" value="jonh@gmail.com" hidden>
                        <input type="submit" value="Take">
                    </form>
                <#else>
                    <form action="/return" method="post">
                        <input type="number" name="id" value="${id}" hidden>
                        <input type="submit" value="return" class="button">
                    </form>
                </#if>
            </div>
        </div>
    </div>
</div>
<footer class="footer">
    <div class="container">?? 2022 Company's Library By Enikeev Ilya</div>
</footer>
</body>
</html>