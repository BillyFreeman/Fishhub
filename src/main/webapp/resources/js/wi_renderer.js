var resp;

var canvas, context;
var canvasWidth, canvasHeight;

var fishCanvas, fishContext;
var fishCanvasWidth, fishCanvasHeight;

var moonCanvas, moonContext;
var moonCanvasWidth, moonCanvasHeight;

var moonList;


var yChartMax, yChartMin, yChartXLine, yChartTime, yWeatherBaseLine, yWindBaseLine;
var xArray;
var chartElements, weatherElements, windElements;
var chartVertexes;

var yRatioLevel;

var fishRatioList;
var currentFishId = -1;

var tSymbol = String.fromCharCode(8451);
var degSymbol = String.fromCharCode(176);
var dayNames = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"];
var monthNames = ["january", "february", "march", "april", "may", "june",
    "july", "august", "september", "october", "november", "december"];

var tabTitles = [];

var mainColor = "#A5A8B1";
var themeBlueColor = "#1ca8dd";

var render = function (response) {
    resp = response;

    setHeaderContent();
    initTabTitles();
    setTabTitles();

    canvas = document.getElementById("chart-canvas");
    context = canvas.getContext('2d');

    fishCanvas = document.getElementById("fish-ratio-canvas");
    fishContext = fishCanvas.getContext('2d');

    moonCanvas = document.getElementById('moon-canvas');
    moonContext = moonCanvas.getContext('2d');

    var rect = canvasBounds(canvas);
    initYLevels(rect);

    canvas.addEventListener("mousemove", function (event) {
        renderMotion(event);
    });

    canvas.addEventListener("mouseout", function () {
        drawChart();
    });

    var ratioRect = canvasBounds(fishCanvas);
    yRatioLevel = ratioRect.h * 0.5;
    fishCanvasWidth = ratioRect.w;
    fishCanvasHeight = ratioRect.h;

    var moonRect = canvasBounds(moonCanvas);
    moonCanvasWidth = moonRect.w;
    moonCanvasHeight = moonRect.h;

    moonList = new MoonPhaseList();
    moonList.initList();

    fishRatioList = new FishList();
    fishRatioList.createList();

    canvasWidth = rect.w;
    canvasHeight = rect.h;
    calculate(1); //start drawing
};

//init Y levels of all drawable elements (chart, weather, wind) 
var initYLevels = function (rect) {
    var h = rect.h;
    yChartMax = h * 0.03;
    yChartMin = h * 0.45;
    yChartXLine = h * 0.55;
    yChartTime = h * 0.6;

    yWeatherBaseLine = h * 0.72;
    yWindBaseLine = h * 0.9;
};

//recalculates all drawable elements according to 'day' argument
var calculate = function (day) {
    setDOMValues(day);

    var date = resp.location.weatherList[day - 1].forecastDate;
    var h3 = resp.location.weatherList[day - 1].h3WeatherList;
    var n = h3.length;

    initXArray(n);
    chartInit(h3);

    fishRatioList.initList(date, h3);

    drawChart();
    drawWeather();
    drawFishRatio();
};

//calculate X coordinates of h3 elements
var initXArray = function (number) {
    xArray = [];

    var base = canvasWidth / number;
    var x0 = base / 2;

    for (var i = 0; i < number; i++) {
        xArray.push(x0 + base * i);
    }
};

//initialize all chart, weather and wind coordinates
var chartInit = function (h3) {
    var yArray = chartY(h3);

    chartElements = [];
    weatherElements = [];
    windElements = [];

    for (var i = 0; i < h3.length; i++) {
        var e = h3[i];

        chartElements.push({
            pos: i,
            x: xArray[i],
            y: yArray[i],
            value: e.temperature,
            time: shortTime(e.forecastTime)
        });

        var wRad = 25;
        weatherElements.push({
            src: "http://localhost:8080/Fishhub/resources/images/weather/" + e.weatherCode + ".png",
            value: e.weatherType,
            x0: xArray[i] - wRad,
            y0: yWeatherBaseLine - wRad,
            w: wRad * 2,
            h: wRad * 2
        });

        //create wind object and push it to array
        windElements.push({
            direction: e.windDirection,
            speed: e.windSpeed,
            x0: xArray[i] - wRad,
            y0: yWindBaseLine - wRad,
            w: wRad * 2,
            h: wRad * 2,
            //v1, v2, v3 - canvas coordinates of wind direction arrow 
            v1: rotateVertex(15, e.windDirection - 20, {
                x: xArray[i],
                y: yWindBaseLine
            }),
            v2: rotateVertex(20, e.windDirection, {
                x: xArray[i],
                y: yWindBaseLine
            }),
            v3: rotateVertex(15, e.windDirection + 20, {
                x: xArray[i],
                y: yWindBaseLine
            })
        });
    }

    chartVertexes = {
        start: {
            x: 0,
            y: chartElements[0].y - (Math.abs(chartElements[1].y - chartElements[0].y) / 4)
        },
        end: {
            x: canvasWidth,
            y: yChartMin - (yChartMin - chartElements[chartElements.length - 1].y) / 2
        }
    };
};

//calculate Y coordinate of chart vertexes
var chartY = function (h3) {
    var min;
    var max = min = h3[0].temperature, base;
    var yArray = [];

    for (var i = 1; i < h3.length; i++) {
        var e = h3[i].temperature;

        max = e > max ? e : max;
        min = e < min ? e : min;
    }

    base = (yChartMin - yChartMax) / (max - min);

    for (var i = 0; i < h3.length; i++) {
        yArray.push(yChartMin - (h3[i].temperature - min) * base);
    }

    return yArray;
};

var drawChart = function () {

    context.clearRect(0, 0, canvasWidth, yChartTime + 10);

    context.font = "32px Arial";
    context.textBaseline = 'top';
    context.textAlign = 'left';
    context.fillStyle = mainColor;

    context.fillText(tSymbol, 0, 0); //draw 'C' character

    //draw chart body
    var grd = context.createLinearGradient(0, yWeatherBaseLine, 0, yChartMax);
    grd.addColorStop(0, "rgba(37,40,48,0.1)");
    grd.addColorStop(1, "rgba(196,199,208,0.7)");

    context.fillStyle = grd;
    context.beginPath();

    context.moveTo(chartVertexes.start.x, yChartXLine);
    context.lineTo(chartVertexes.start.x, chartVertexes.start.y);

    for (var i = 0; i < xArray.length; i++) {
        context.lineTo(xArray[i], chartElements[i].y);
    }

    context.lineTo(chartVertexes.end.x, chartVertexes.end.y);
    context.lineTo(chartVertexes.end.x, yChartXLine);

    context.closePath();
    context.fill(); //END draw chart body

    //draw X axis
    context.strokeStyle = mainColor;
    context.lineWidth = 1;

    context.beginPath();
    context.moveTo(chartVertexes.start.x, yChartXLine);
    context.lineTo(chartVertexes.end.x, yChartXLine);
    context.closePath();
    context.stroke();

    context.fillStyle = mainColor;
    context.beginPath();

    for (var i = 0; i < chartElements.length; i++) {
        context.moveTo(chartElements[i].x, yChartXLine);
        context.arc(chartElements[i].x, yChartXLine, 3, 0, Math.PI * 2);
    }

    context.closePath();
    context.fill();//END draw X axis

    context.font = "12px Arial";
    context.textBaseline = 'middle';
    context.textAlign = 'center';

    for (var i = 0; i < chartElements.length; i++) {
        context.fillText(chartElements[i].time, chartElements[i].x, yChartTime); //draw time titles on X axis
    }
};

//draw weather and wind icons
var drawWeather = function () {
    context.clearRect(0, yChartTime + 10, canvasWidth, canvasHeight);

    for (var i = 0; i < weatherElements.length; i++) {
        var img = new Image();
        img.index = i;
        img.rect = weatherElements[i];
        img.onload = function () {
            context.drawImage(this, this.rect.x0, this.rect.y0, this.rect.w, this.rect.h); //draw img when loading completed
        };
        img.src = weatherElements[i].src; //load img source. When loading completed, img is drawing
    }
    context.strokeStyle = themeBlueColor;
    context.lineWidth = 5;

    context.beginPath();
    //draw wind icons
    for (var i = 0; i < windElements.length; i++) {
        var e = windElements[i];
        context.moveTo(xArray[i] + 15, yWindBaseLine);
        context.arc(xArray[i], yWindBaseLine, 15, 0, Math.PI * 2);
        //draw wind direction arrow
        context.moveTo(e.v1.x, e.v1.y);
        context.lineTo(e.v2.x, e.v2.y);
        context.lineTo(e.v3.x, e.v3.y);
    }
    context.closePath();
    context.stroke();

    context.fillStyle = "white";
    for (var i = 0; i < windElements.length; i++) {
        context.fillText(windElements[i].speed, xArray[i], yWindBaseLine);
    }
};


var drawFishRatio = function () {
    if (currentFishId > -1) {
        fishRatioList.getFish(currentFishId).drawValues()
    } else {
        fishRatioList.getFirst().drawValues();
    }
};


//renders when hovering over canvas
var renderMotion = function (event) {

    var targetY = event.y - canvas.getBoundingClientRect().top; //cursor vertical position
    if (targetY < yChartXLine) { //if cursor is over canvas (above X axis)
        drawChart(); //draw base canvas state

        var targetX = event.x - canvas.getBoundingClientRect().left;
        var chartElement = getNearestElement(targetX); //nearest chart vertex coordinates
        context.strokeStyle = mainColor;
        context.lineWidth = 1;

        context.beginPath();
        context.moveTo(chartElement.x, yChartXLine);
        context.lineTo(chartElement.x, chartElement.y);
        context.closePath();
        context.stroke(); //draw vertical line from X axis to nearest chart vertex

        context.fillStyle = "rgba(255,255,255,0.3)";
        context.beginPath();
        context.arc(chartElement.x, chartElement.y, 7, 0, Math.PI * 2);
        context.closePath();
        context.fill(); //draw transparent circle in nearest chart vertex

        context.fillStyle = "white";
        context.beginPath();
        context.arc(chartElement.x, chartElement.y, 3, 0, Math.PI * 2);
        context.closePath();
        context.fill(); //draw white circle in nearest chart vertex

        var sPos = new SignPosition(chartElement); //info panel position element
        var i = chartElement.pos; //nearest vertex index

        context.fillStyle = "white";
        context.textBaseline = 'middle';
        context.textAlign = sPos.align;

        context.font = "20px Arial";
        context.fillText(chartElement.value + degSymbol, sPos.x, sPos.tY); //draw h3 temperature on info panel

        context.font = "14px Arial";
        context.fillText(weatherElements[i].value, sPos.x, sPos.weValY); //draw h3 weather string on info panel
        context.fillText(windElements[i].value, sPos.x, sPos.wiValY); //draw h3 wind name string on info panel
        context.fillText(windElements[i].speed + " m/s", sPos.x, sPos.wiSpeedY); //draw h3 wind speed on info panel

        context.fillStyle = "#1ca8dd";
        context.font = "12px Arial";
        context.fillText("weather:", sPos.x, sPos.weTitleY); //draw 'weather:' string on info panel
        context.fillText("wind:", sPos.x, sPos.wiTitleY); //draw 'wind:' string on info panel
    }
};

var canvasBounds = function (canvas) {
    var right = canvas.getBoundingClientRect().right - canvas.getBoundingClientRect().left;
    var bottom = canvas.getBoundingClientRect().bottom - canvas.getBoundingClientRect().top;
    return {
        x: 0,
        y: 0,
        w: right,
        h: bottom
    }
};

//calculate info panel size and cootdinates when hoveting over chart
var SignPosition = function (element) {
    this.align = element.pos < chartElements.length / 2 ? 'left' : 'right'; //choose side where info panel will apear

    var dx = this.align === 'left' ? 20 : -20;
    var dy = Math.abs(element.y - yChartMax) < 60 ? element.y - yChartMax : 60;

    this.x = element.x + dx;
    this.tY = element.y - dy;
    this.weTitleY = element.y - dy + 18;
    this.weValY = element.y - dy + 32;
    this.wiTitleY = element.y - dy + 48;
    this.wiValY = element.y - dy + 62;
    this.wiSpeedY = element.y - dy + 74;
};

//when hovering over chart detects the closest chart vertex to the cursor
var getNearestElement = function (targetX) {
    var nearest = chartElements[0];
    for (var i = 1; i < chartElements.length; i++) {
        nearest = Math.abs(targetX - nearest.x) < Math.abs(targetX - chartElements[i].x) ? nearest : chartElements[i];
    }
    return nearest;
};

//return time string without seconds characters (for 3h chart X axis)
var shortTime = function (time) {
    var pattern = "^[0-9]{2}:[0-9]{2}";
    return time.match(pattern);
};

//rotates vertex on the specified angle and returns it's coordinates
var rotateVertex = function (radius, angle, center) {
    var a = (360 - angle + 90) > 360 ? 90 - angle : 360 - angle + 90;
    var x1 = center.x + (Math.cos(a * Math.PI / 180) * radius);
    var y1 = center.y - (Math.sin(a * Math.PI / 180) * radius);
    return {
        x: x1,
        y: y1
    };
};

var setHeaderContent = function () {
    $("#wi-location-name").html(resp.location.title);
    $("#wi-lat").html(resp.location.latitude);
    $("#wi-lng").html(resp.location.longitude);
};

//set general weather data (date, pressure, humidity, day/night temperature, moon phase)
var setDOMValues = function (day) {
    moonList.getPhase(day - 1).draw(); //draw on moon canvas

    var dayElement = resp.location.weatherList[day - 1]; //JSON single day element
    $("#wi-date-string").html(formattedDate(dayElement.forecastDate));
    $("#wi-i-pressure-value").html(dayElement.pressure);
    $("#wi-i-humidity-value").html(dayElement.humidity);
    $("#wi-day-temperature").html(dayElement.maxTemperature);
    $("#wi-night-temperature").html(dayElement.minTemperature);
};

//creates Date object from date JSON date string yyyy-MM-dd
var DateObj = function (dateStr) {
    this.year = dateStr.substring(0, 4);
    this.month = dateStr.substring(6, 7) - 1;
    this.day = dateStr.substring(8, 10);

    this.getCurDate = function () {
        return new Date(this.year, this.month, this.day);
    }
};

//creates Weather block heading with curent date string 
var formattedDate = function (dateStr) {
    var dObj = new DateObj(dateStr);
    var date = dObj.getCurDate(); //for getting curent day and month names
    var day = dObj.day.substring(0, 1) === '0' ? dObj.day.substring(1, 2) : dObj.day;
    return dayNames[date.getUTCDay()] + ", " + day + " " + monthNames[date.getMonth()] + " " + dObj.year;
};

//initialize string array with tab names acording to current date.
var initTabTitles = function () {
    tabTitles = [];
    var e = resp.location.weatherList;
    for (var i = 0; i < e.length; i++) {
        var dayN = new DateObj(e[i].forecastDate).getCurDate().getUTCDay();
        tabTitles.push(dayNames[dayN]);
    }
};

//set tab names determined by initTabTitles() function.
var setTabTitles = function () {
    //we do not change first and second tab names. Those are 'Today' and 'Tomorrow';) 
    $("#wi-day-3").html(tabTitles[2]);
    $("#wi-day-4").html(tabTitles[3]);
    $("#wi-day-5").html(tabTitles[4]);
};
