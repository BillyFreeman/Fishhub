<%-- 
    Document   : home
    Created on : 24.03.2016, 13:03:05
    Author     : Віктор
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Fishhub</title>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="resources/css/map.css">
        <link rel="stylesheet" href="resources/css/mainpanel.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <script src="resources/js/map.js"></script>
        <script src="resources/js/controller.js"></script>
        <script src="resources/js/wi_renderer.js"></script>
        <script src="resources/js/ratio_renderer.js"></script>
        <script src="resources/js/moon_renderer.js"></script>
        <script src="https://maps.googleapis.com/maps/api/js?callback=initMap" async defer></script>
        <script src="resources/js/markerclusterer.js"></script>
    </head>
    <body data-spy="scroll" data-target=".navbar-fixed-top">
        <div class="container-fluid to-blur">
            <nav class="navbar navbar-inverse navbar-fixed-top">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <a class="navbar-brand" href="#"><img src="resources/images/fh_paper_logo_small.png"></a>
                    </div>
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="#" data-toggle="tooltip" data-placement="bottom" title="Homepage"><i
                                    class="fa fa-home"></i></a></li>
                        <li><a href="#">API</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                Lng
                                <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="#">en</a></li>
                                <li class="divider"></li>
                                <li><a href="#">ua</a></li>
                            </ul>
                        </li>
                        <li>
                            <div class="user-name"><i class="fa fa-user"></i>Root</div>
                        </li>
                        <li><a href="#" data-toggle="tooltip" data-placement="bottom" title="Logout"><i class="fa fa-times"></i></a>
                        </li>
                    </ul>
                </div>
            </nav>
            <div id="map"></div>
            <div class="floating-button floating-button-all hidden" data-toggle="tooltip" data-placement="left"
                 title="Show all"><i
                    class="fa fa-reply-all"></i></div>
            <div class="floating-button floating-button-favorites" data-toggle="tooltip" data-placement="left"
                 title="Show only favorites"><i class="fa fa-heart"></i></div>
            <div class="floating-button floating-button-edit" data-toggle="tooltip" data-placement="left" title="Edit location">
                <i
                    class="fa fa-pencil"></i></div>
            <div class="floating-button floating-button-add" data-toggle="tooltip" data-placement="left"
                 title="Add new location"><i
                    class="fa fa-plus"></i></div>
        </div>
        <div class="faded-background">
        </div>
        <div class="calculation-block panel">
            <div class="container-fluid">
                <div class="panel-header">
                    <span class="location-name" id="wi-location-name">Header</span>
                    <span class="header-action-block"><i class="fa fa-heart add-to-favorites"></i> <i
                            class="fa fa-times close-panel"></i></span>
                </div>
                <div class="panel-body">
                    <div class="location-lat-lng wi-mid-light">
                        <span class="wi-lat">Latitude: </span><span id="wi-lat">50.447011</span>
                        <span class="wi-lng">Longitude: </span><span id="wi-lng">30.531006</span>
                    </div>
                    <div class="location-info-block">
                        <ul class="nav nav-tabs nav-justified">
                            <li class="active"><a href="#day1" data-toggle="tab" onclick="calculate(1)">Today</a></li>
                            <li><a href="#day2" data-toggle="tab" onclick="calculate(2)">Tomorrow</a></li>
                            <li><a href="#day3" data-toggle="tab" onclick="calculate(3)" id="wi-day-3">Wed</a></li>
                            <li><a href="#day4" data-toggle="tab" onclick="calculate(4)" id="wi-day-4">Thu</a></li>
                            <li><a href="#day5" data-toggle="tab" onclick="calculate(5)" id="wi-day-5">Fri</a></li>
                        </ul>
                        <div class="weather-info">
                            <div class="wi-daily-block wi-content-center">
                                <div class="wi-conditions-block">
                                    <div class="wi-date-string wi-mid-light" align="center"><span id="wi-date-string">Tuesday, 05 april 2016</span>
                                    </div>
                                    <div class="wi-additional-info wi-inline-content">
                                        <div class="wi-i-moon-phase" align="center">
                                            <canvas id="moon-canvas" width="64" height="64"></canvas><br>
                                            <span class="wi-sm-white" id="wi-moon-phase-title">Moon phase</span>
                                        </div>
                                        <div class="wi-i-pressure" align="center">
                                            <span class="wi-sm-white">Pressure</span><br>
                                            <span class="wi-i-pressure-value wi-lg-white" id="wi-i-pressure-value">867</span>
                                            <span class="wi-mid-white"> mmHg</span>
                                        </div>
                                        <div class="wi-i-humidity" align="center">
                                            <span class="wi-sm-white">Humidity</span><br>
                                            <span class="wi-i-pressure-value wi-lg-white" id="wi-i-humidity-value">89</span>
                                            <span class="wi-mid-white"> %</span>
                                        </div>
                                        <div class="wi-temperature-block" align="end">
                                            <div class="wi-day-temperature wi-xlg-white"><span id="wi-day-temperature">20</span>&deg
                                            </div>
                                            <div class="wi-night-temperature wi-xlg-dark"><span
                                                    id="wi-night-temperature">9</span>&deg
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="wi-h3-block">
                                <div class="wi-h3-heading">
                                    <div class="wi-sm-light wi-heading-line">Hourly weather</div>
                                </div>
                                <div class="chart-container" id="chart-container">
                                    <canvas id="chart-canvas" height="300" width="600"></canvas>
                                </div>
                                <div class="wi-h3-heading">
                                    <div class="wi-sm-light wi-heading-line">Fishing ratio</div>
                                </div>
                                <div class="ratio-container" id="ratio-container">
                                    <canvas id="fish-ratio-canvas" height="75" width="600"></canvas>
                                </div>
                                <div class="wi-fish-select wi-content-center">
                                    <div class="wi-fish-list hidden" id="wi-fish-list"></div>
                                    <div class="wi-choose-fish fishes-toggle panel" id="wi-choose-fish">
                                        <img src="images/fishes/karas.png"><span class="fish-name">Carasian</span><i class="fa fa-chevron-up"></i>
                                    </div>
                                    <div class="wi-total-ratio-block wi-mid-light panel">Total daily fishing ratio: <span
                                            class="wi-green wi-lg-white" id="wi-total-ratio">7</span>/10
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="panel-footer">
                    Weather source: <a href="http://openweathermap.org" target="_blank">OpenWeatherMap</a>
                </div>
            </div>
        </div>
    </body>
</html>
