
function initMap() {
    var mapDiv = document.getElementById("map");
    var map = new google.maps.Map(mapDiv, {
        center: {lat: 50.447011, lng: 30.531006},
        zoom: 7,
        disableDefaultUI: true,
        zoomControl: true,
        zoomControlOptions: {
            position: google.maps.ControlPosition.RIGHT_CENTER
        }
    });

    var infoWindow = new google.maps.InfoWindow();

    $.getJSON('api/locations.json', {}, function (json) {
        var markers = [];
        $.each(json["locationListWrapper"]["locations"], function (i, val) {
            var pos = new google.maps.LatLng(val.latitude, val.longtitude);

            var marker = new google.maps.Marker({
                position: pos
            });
//            marker.setTitle(val.name);
            marker.set("id", val.id);
            marker.set("heading", val.title);
            marker.set("lat", val.latitude);
            marker.set("lng", val.longtitude);
            marker.addListener('click', function () {
                infoWindow.set("content", getContentString(marker.get("heading"), marker.get("lat"), marker.get("lng"), marker.get("id")));
                infoWindow.open(map, marker);
            });
            markers.push(marker);
        });
        var markerCluster = new MarkerClusterer(map, markers);
    });
}

var getContentString = function (heading, lat, lng, id) {
    var infoString = "<div id='marker-info-content'>" +
            "<div id='marker-info-heading'>" + heading + "</div>" +
            "<div id='marker-info-lat' class='marker-info-title'>" +
            "<div>LAT</div>" +
            "</div>" +
            "<div id='marker-info-lat-value' class='marker-info-value'>" + lat + "</div>" +
            "<div id='marker-info-lng' class='marker-info-title'>" +
            "<div>LNG</div>" +
            "</div>" +
            "<div id='marker-info-lng-value' class='marker-info-value'>" + lng + "</div>" +
            "<div id='marker-info-button'>" +
            "<button onclick='showLocationInfo(" + id + ")' class='btn btn-md'>Details</button>" +
            "</div>" +
            "</div>";
    return infoString;
};


