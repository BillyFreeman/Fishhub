
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
    
    $.getJSON('api/locations.json', {}, function (json) {
        var markers = [];
        $.each(json["locationListWrapper"]["locations"], function (i, val) {
            var pos = new google.maps.LatLng(val.latitude, val.longtitude);
            var marker = new google.maps.Marker({
                position: pos
            });
            marker.setTitle(val.name);
            marker.set("id", val.id);
            marker.addListener('click', function () {
                alert(marker.get("id"));
            });
            markers.push(marker);
        });
        var markerCluster = new MarkerClusterer(map, markers);
    });
}

