<script type="text/javascript">
var map;


function initialize() {
	
    // Set up the map
    var mapOptions = {
        zoom: 10,
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        streetViewControl: false
    };
    
    map = new google.maps.Map(document.getElementById('map_canvas'), mapOptions);
    alert("HERE");
    // Try HTML5 geolocation
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            var pos = new google.maps.LatLng(position.coords.latitude,
            position.coords.longitude);

            var infowindow = new google.maps.InfoWindow({
                map: map,
                position: pos,
                content: 'Location found using HTML5.'
            });

            map.setCenter(pos);
        }, function () {
            alert("Your browser supports Geolocation, however you have it disabled!");
        });

    }else {
            // Browser doesn't support Geolocatio
            alert("Your browser does not support geolocation!");
    }
    
}


    google.maps.event.addDomListener(window, 'load', initialize);
</script>