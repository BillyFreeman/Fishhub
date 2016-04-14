var periods = ['0:00', '3:00', '6:00', '9:00', '12:00', '15:00', '18:00', '21:00', '24:00'];
var rendered = false;

var chartInfo;
var canvas;
var context;
var renderParams;

function startRendering() {
    canvas = document.getElementById("chart-canvas");
    context = canvas.getContext('2d');

    chartInfo = setChartInfo([9, 12, 13, 18, 12, 19, 20, 13, 12]);

    var rect = getCanvasRect(canvas);
    renderParams = getRenderParams(rect);

    renderChart();
    rendered = true;

    canvas.addEventListener("mousemove", function (event) {
        renderMotion(event);
    });

    canvas.addEventListener('mouseout', function () {
        renderChart();
    });
}

function renderChart() {
    context.save();
    context.fillStyle = "#252830";
    context.fillRect(0, 0, renderParams.rect.w, renderParams.rect.h);

    context.fillStyle = "#434857";
    context.beginPath();
    context.moveTo(renderParams.innerRect.right, renderParams.positions[renderParams.positions.length - 1].yAxis);
    context.lineTo(renderParams.innerRect.right, renderParams.innerRect.bottom);
    context.lineTo(renderParams.innerRect.left, renderParams.innerRect.bottom);
    context.lineTo(renderParams.innerRect.left, renderParams.positions[0].yAxis);
    for (var i = 0; i < renderParams.positions.length; i++) {
        context.lineTo(renderParams.positions[i].xAxis, renderParams.positions[i].yAxis);
    }
    context.fill();

    context.strokeStyle = "#c4c7d0";
    context.beginPath();
    context.moveTo(renderParams.innerRect.left, renderParams.chartBottom);
    context.lineTo(renderParams.innerRect.left, 0);
    context.stroke();

    context.strokeStyle = "#1bc98e";
    context.lineWidth = 2;
    context.beginPath();
    context.moveTo(renderParams.innerRect.left, renderParams.positions[0].yAxis);
    for (var i = 0; i < renderParams.positions.length; i++) {
        context.lineTo(renderParams.positions[i].xAxis, renderParams.positions[i].yAxis);
        context.stroke();
    }

    context.fillStyle = "#1bc98e";
    for (var i = 0; i < renderParams.positions.length; i++) {
        context.beginPath();
        context.arc(renderParams.positions[i].xAxis, renderParams.positions[i].yAxis, 3, 0, 2 * Math.PI);
        context.fill();
    }

    context.fillStyle = "#c4c7d0";
    context.font = "14px Arial";
    context.textBaseline = "top";
    context.fillText('\u00B0C', 0, 0);

    context.font = "12px Arial";
    context.textBaseline = 'middle';
    context.textAlign = 'center';
    for (var i = 0; i < renderParams.positions.length; i++) {
        context.fillText(chartInfo[i].time, renderParams.positions[i].xAxis, renderParams.innerRect.bottom + 14);
    }
    context.restore();
}


function renderMotion(event) {
    renderChart();
    var targetX = event.x - $('#chart-canvas').offset().left;
    var index = getClosestIndex(targetX);
    if (index >= 0) {
        context.strokeStyle = "#1FE3A0";
        context.beginPath();
        context.moveTo(renderParams.positions[index].xAxis, renderParams.innerRect.bottom);
        context.lineTo(renderParams.positions[index].xAxis, renderParams.rect.y);
        context.stroke();

        context.fillStyle = "#1FE3A0";
        context.beginPath();
        context.arc(renderParams.positions[index].xAxis, renderParams.positions[index].yAxis, 5, 0, 2 * Math.PI);
        context.fill();
    }
}

function getCanvasRect(canvas) {
    var right = canvas.getBoundingClientRect().right - $('#chart-canvas').offset().left;
    var bottom = canvas.getBoundingClientRect().bottom - $('#chart-canvas').offset().top;
    return {
        x: 0,
        y: 0,
        w: right,
        h: bottom
    }
}

function getRenderParams(rect) {
    var top = Math.abs(rect.h * 0.1);
    var right = Math.abs(rect.w * 0.96);
    var bottom = Math.abs(rect.h * 0.85);
    var left = Math.abs(rect.w * 0.04);

    var chartBottom = Math.abs(rect.h * 0.75);

    var extremums = getExtremums(chartInfo);
    var ySample = extremums.max > 0 ? extremums.max - extremums.min : Math.abs(extremums.min) - Math.abs(extremums.max);
    var xFragment = (rect.w - left * 2) / (chartInfo.length - 1);
    var yFragment = (chartBottom - top) / ySample;

    var positions = [];
    for (var i = 0; i < chartInfo.length; i++) {
        var position = {};
        position.xAxis = left + i * xFragment;
        position.yAxis = chartBottom - (chartInfo[i].value - extremums.min) * yFragment;
        positions.push(position);
    }

    var signPaths = [];
    for (var i = 0; i < chartInfo.length; i++) {
        signPath = {};
        if (i <= chartInfo.length / 2) {

        } else {

        }
        signPaths.push(signPath);
    }

    return {
        rect: rect,
        innerRect: {
            top: top,
            right: right,
            bottom: bottom,
            left: left
        },
        xFragment: xFragment,
        yFragment: yFragment,
        chartBottom: chartBottom,
        positions: positions,
        extremums: extremums
    }
}

function setChartInfo(newInfo) {
    var array = [];
    for (var i = 0; i < newInfo.length; i++) {
        var infoElement = {time: periods[i], value: newInfo[i]};
        array.push(infoElement);
    }
    return array;
}

function getExtremums(numbers) {
    var min = numbers[0].value;
    var max = numbers[0].value;
    for (var i = 0; i < numbers.length; i++) {
        if (numbers[i].value < min) {
            min = numbers[i].value;
        }
        if (numbers[i].value > max) {
            max = numbers[i].value;
        }
    }
    return {
        max: max,
        min: min
    }
}

function getClosestIndex(value) {
    var index = -1;
    var array = renderParams.positions;
    if (value >= array[0].xAxis && value <= array[array.length - 1].xAxis) {
        var found = false;
        var i = 0;
        while (!found && i < (array.length - 1)) {
            found = value < array[++i].xAxis ? true : false;
        }
        index = value <= (array[i - 1].xAxis + array[i].xAxis) / 2 ? i - 1 : i;
    }
    return index;
}
