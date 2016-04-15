var MoonPhase = function (context, args) {
    this.ctx = context;
    this.args = args;

    this.draw = function () {
        var mPh = this;
        mPh.ctx.clearRect(0, 0, moonCanvasWidth, moonCanvasHeight);

        var img = new Image();
        img.onload = function () {
            mPh.ctx.drawImage(this, 0, 0, moonCanvasWidth, moonCanvasWidth);
        };
        img.src = this.args.src;
        $("#wi-moon-phase-title").html(this.args.title);
    };
};


var MoonPhaseList = function () {

    this.initList = function () {
        this.array = [];

        var e = resp.location.weatherList;

        for (var i = 0; i < e.length; i++) {

            var args = new MoonPhaseArgs(e[i].moonPhaseName);
            moonContext.fillStyle = "rgba(0, 0, 0, 0.5)";
            var moonPhase = new MoonPhase(moonContext, args);
            this.array.push(moonPhase);
        }

    };

    this.getPhase = function (day) {
        return this.array[day];
    };

};


var MoonPhaseArgs = function (title) {
    var imgPath = "http://localhost:8080/Fishhub/resources/images/moon/";
    this.title = title;
    switch (title) {
        case "New moon":
            this.src = imgPath + "fh_new.png";
            break;
        case "Young moon":
            this.src = imgPath + "fh_young.png";
            break;
        case "Waxing crescent":
            this.src = imgPath + "fh_wxc.png";
            break;
        case "First quarter":
            this.src = imgPath + "fh_fst_q.png";
            break;
        case "Waxing gibbous":
            this.src = imgPath + "fh_wxg.png";
            break;
        case "Full moon":
            this.src = imgPath + "fh_full.png";
            break;
        case "Waning gibbous":
            this.src = imgPath + "fh_wng.png";
            break;
        case "Third quarter":
            this.src = imgPath + "fh_thrd_q.png";
            break;
        case "Waning crescent":
            this.src = imgPath + "fh_wnc.png";
            break;
        case "Old moon":
            this.src = imgPath + "fh_old.png";
            break;
    }
};


