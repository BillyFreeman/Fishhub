$(document).ready(function () {
    $(".floating-button-add").click(function () {
        $('.to-blur').toggleClass('is-blurred');
        $(".faded-background").fadeIn({
            duration: 250, start: function () {
                $(".calculation-block").slideDown({duration: 100});
                setFishListPos();
                render(unit);
                initFishListDOM(fishRatioList);
            }
        });
    });

    $(".close-panel").click(function () {
        $('.to-blur').toggleClass('is-blurred');
        $(".faded-background").fadeOut({
            duration: 250, start: function () {
                $(".calculation-block").slideUp({duration: 100});
            }
        });
        activateFirstTab();
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


var activateFirstTab = function () {
    var tabs = $(".location-info-block > ul > li");
    if (!tabs.first().hasClass("active")) {
        tabs.removeClass("active");
        tabs.first().addClass("active");
    }
};


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

    list.css("left", button.position().left);
    list.css("width", button.css("width"));
};

var initFishListDOM = function (list) {
    var fish;
    if (currentFishId > -1) {
        fish = list.getFish(currentFishId);
    } else {
        fish = list.getFirst();
        currentFishId = fish.id;
    }
    initCurrentFishEement(fish);
    initHiddenFishes();
};

var initCurrentFishEement = function (fish) {
    var tags = "<img src='images" + fish.img + "'><span class='fish-name'>" + fish.name + "</span><i class='fa fa-chevron-up'></i>";
    $("#wi-choose-fish").html(tags);
};

var initHiddenFishes = function () {
    var container = $("#wi-fish-list");
    container.html("");
    for (var i = 0; i < fishRatioList.array.length; i++) {
        var e = fishRatioList.array[i];
        if (e.id != currentFishId) {
            var tags = "<div onclick='selectFish(" + e.id + ")'><img src='images/" + e.img + "'><span class='fish-name'>" + e.name + "</span></div>";
            container.append(tags);
        }
    }
};

var getLocationInfo = function (id) {
    alert(id);
};
