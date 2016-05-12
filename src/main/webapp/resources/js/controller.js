$(document).ready(function () {
    $(".close-panel").click(function () {
        $('.to-blur').toggleClass('is-blurred');
        $(".faded-background").fadeOut({
            duration: 250, start: function () {
                $(".calculation-block").slideUp({duration: 100});
            }
        });
        activateFirstTab();
        currentFishId = -1;
    });

    $("#wi-choose-fish").click(function () {
        $("#wi-fish-list").toggleClass("hidden");
    });

    $(".floating-button-favorites").click(function () {
        $(this).addClass("hidden");
        $(".floating-button-all").removeClass("hidden");
    });

    $(".floating-button-all").click(function () {
        $(this).addClass("hidden");
        $(".floating-button-favorites").removeClass("hidden");
    });

    $('[data-toggle="tooltip"]').tooltip();
});

//activate first tab after weather panel was closed
var activateFirstTab = function () {
    var tabs = $(".location-info-block > ul > li");
    if (!tabs.first().hasClass("active")) {
        tabs.removeClass("active");
        tabs.first().addClass("active");
    }
};

//hide fishlist when click outside
$(document).click(function (e) {
    if (!$("#wi-fish-list").hasClass("hidden")) {
        if (!$(e.target).is("#wi-fish-list") && !$(e.target).is("#wi-choose-fish") && !$(e.target).is("#wi-choose-fish *")) {
            $("#wi-fish-list").toggleClass("hidden");
        }
    }
});

var setFishListPos = function () {
    var button = $("#wi-choose-fish");
    var list = $("#wi-fish-list");

    list.css("left", button.position().left); //align fishlist to fishbutton
    list.css("width", button.css("width")); //set fishlist width
};

//open weather panel
var showLocationInfo = function (id) {
    $('.to-blur').toggleClass('is-blurred');
    $(".faded-background").fadeIn({
        duration: 250, start: function () {
            var url = "api/location/" + id + ".json";
            $.getJSON(url, {}, function (json) { //get the curent location data
                $(".calculation-block").slideDown({duration: 100});
                setFishListPos();
                render(json); //start rendering weather panel using REST api response
                initFishListDOM(fishRatioList);
            })
        }
    });
};

//create fish button content
var initCurrentFishEement = function (fish) {
    var tags = "<img src='http://localhost:8080/Fishhub/resources/images" + fish.img + "'><span class='fish-name'>" + fish.name + "</span><i class='fa fa-chevron-up'></i>";
    $("#wi-choose-fish").html(tags);
};

//create fishlist content
var initHiddenFishes = function () {
    var container = $("#wi-fish-list");
    container.html("");
    for (var i = 0; i < fishRatioList.array.length; i++) { //for all fishes
        var e = fishRatioList.array[i];
        if (e.id != currentFishId) { //exclude curently selected fish from list. It was created by initCurrentFish()
            var tags = "<div onclick='selectFish(" + e.id + ")'><img src='http://localhost:8080/Fishhub/resources/images/" + e.img + "'><span class='fish-name'>" + e.name + "</span></div>";
            container.append(tags);
        }
    }
};

var initFishListDOM = function (list) {
    var fish;
    if (currentFishId > -1) {
        fish = list.getFish(currentFishId);
    } else {
        fish = list.getFirst(); //if there was no previous fish selection, show first fish
        currentFishId = fish.id;
    }
    initCurrentFishEement(fish);
    initHiddenFishes();
};
