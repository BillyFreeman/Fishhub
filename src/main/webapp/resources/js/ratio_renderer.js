//fish object
var fish = function (context, e) {

    this.ctx = context; //ratio canvas context
    this.id = e.id;
    this.name = e.name;
    this.translit = e.translit;
    this.latinName = e.latinName;
    this.img = e.imgPath;
    this.allRatio = e.activityRatio;
    this.values = [];

    this.setValues = function (date, h3) {
        this.values = [];

        for (var i = 0; i < h3.length; i++) {
            this.values.push(this.allRatio[getKey(date, h3[i].forecastTime)]);
        }
    };

    this.drawValues = function () {
        this.ctx.clearRect(0, 0, fishCanvasWidth, fishCanvasHeight);
        for (var i = 0; i < this.values.length; i++) {
            var color = getRatioColor(this.values[i]);
            this.ctx.strokeStyle = color;
            this.ctx.lineWidth = 2;

            this.ctx.beginPath();
            this.ctx.arc(xArray[i], yRatioLevel, 20, 0, Math.PI * 2);
            this.ctx.closePath();
            this.ctx.stroke();

            this.ctx.fillStyle = color;
            this.ctx.fillText(this.values[i], xArray[i], yRatioLevel);
        }
    };
};

var FishList = function () {
    this.array = [];

    this.createList = function () {
        var e = resp.location.fishlist;
        for (var i = 0; i < e.length; i++) {
            var f = new fish(fishContext, e[i]);
            this.array.push(f);
        }
    };

    this.initList = function (date, h3) {
        fishContext.font = "18px Arial";
        fishContext.textBaseline = 'middle';
        fishContext.textAlign = 'center';

        for (var i = 0; i < this.array.length; i++) {
            this.array[i].setValues(date, h3);
        }
    };

    this.getFish = function (id) {
        for (var i = 0; i < this.array.length; i++) {
            var f = this.array[i];
            if (f.id === id) {
                return f;
            }
        }
    };

    this.getFirst = function () {
        return this.array[0];
    };
};

//build a key string
function getKey(date, time) {
    var str = "D" + date + "T" + time;
    return str.replace(/[-:]/g, "");
}

//return color from predefined list according the ratio value
function getRatioColor(ratio) {
    switch (ratio) {
        case 1:
            return "#e85a59";
        case 2:
            return "#d96258";
        case 3:
            return "#c86b56";
        case 4:
            return "#ac7a54";
        case 5:
            return "#928852";
        case 6:
            return "#7a9550";
        case 7:
            return "#62a14e";
        case 8:
            return "#4eac4c";
        case 9:
            return "#37b84a";
        case 10:
            return "#1dc748";
        default:
            return "#1ca8dd";
    }
}

//chose fish by id and redraw the DOM fishlist objects
function selectFish(id) {
    var f = fishRatioList.getFish(id);
    f.drawValues();
    currentFishId = f.id;
    initFishListDOM(fishRatioList);
}
