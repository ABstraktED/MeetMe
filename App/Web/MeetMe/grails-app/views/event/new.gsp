<g:applyLayout name="event_layout">
	<head>
<r:require module="jquery" />
<r:require module="jquery-ui" />
<r:layoutResources />
<link rel="stylesheet"
	href="${resource(dir: 'css/le-frog', file: 'jquery-ui-1.10.3.custom.css')}"
	type="text/css">
<g:javascript src="jquery-ui-timepicker-addon.js" />
<g:javascript src="jquery.geocomplete.js" />
<script type="text/javascript"
	src="http://maps.googleapis.com/maps/api/js?libraries=places&sensor=false"></script>
	</head>

	<content tag="event_content"> <g:form action="processEvent">
		<div>
			<g:if test="${flash.message}">
				<div class="message" role="status">
					${flash.message}
				</div>
			</g:if>
			<g:if test="${flash.error}">
				<div class="alert alert-error" style="display: block">
					${flash.error}
				</div>
			</g:if>
			<label for="title">Title</label>
			<g:textField name="title" maxLength="50" />
			<br /> <label for="date">Date</label> <input type="text"
				id="calendar" name="date" /><br /> <br /> <label
				for="description">Description</label>
			<g:textArea name="description" />
			<br /> <br />
			<g:checkBox name="allCanJoin" />
			<label for="allCanJoin">Everybody can join this event</label> <br />
			<br />
			<g:checkBox name="allCanInvite" />
			<label for="allCanInvite">Everybody can invite to this event</label>
			<br /> <br /> <label for="address">Address</label>
			<g:textField name="address" placeholder="" />
			<br />
			<div id="map_canvas" style="height: 350px; width: 600px"></div>
			<br />
		</div>
		<g:hiddenField name="lat" />
		<g:hiddenField name="lng" />
		<input type="submit" value="Create">
	</g:form> <script type="text/javascript">
		// JQUERY

		// Calendar
		$(document).ready(function() {
			$('#calendar').datetimepicker({
				numberOfMonths : 2,
				dateFormat : "dd/mm/yy",
				timeFormat : "HH:mm",
				minDate : 0,
				addSliderAccess : true,
				sliderAccessArgs : {
					touchonly : false
				}
			});

			// Map
			$('#address').geocomplete({
				map : "#map_canvas",
				mapOptions : {
					zoom : 16
				},
				markerOption : {
					draggable : true
				},
				details : "form"
			});

		});

		// Hide map when not needed
		//	$('#address').focus(function(){
		//	$('#map_canvas').show();
		//});

		//$('#address').blur(function(){
		//if($('#address').val() === "")
		//$('#map_canvas').hide();
		//});

		// JAVASCRIPT
		// Gets and sets current location on map

		//function initialize() {
		// Set up the map
		//var mapOptions = {
		//zoom : 10,
		//mapTypeId : google.maps.MapTypeId.ROADMAP,
		//streetViewControl : false
		//};

		//var map = new google.maps.Map(document.getElementById('map_canvas'),
		//	mapOptions);
		// Try HTML5 geolocation
		//if (navigator.geolocation) {
		//navigator.geolocation
		//	.getCurrentPosition(
		//		function(position) {
		//		var pos = new google.maps.LatLng(
		//			position.coords.latitude,
		//		position.coords.longitude);

		//var marker = new google.maps.Marker({
		//title : "Your location",
		//map : map,
		//position : pos,
		//content : 'Here'
		//});
		//var infowindow = new google.maps.InfoWindow({
		//map: map,
		//position: pos,
		//  content: 'Location found using HTML5.'
		//});

		//	map.setCenter(pos);
		//},
		//function() {
		//alert("Your browser supports Geolocation, however you have it disabled!");
		//});

		//} else {
		// Browser doesn't support Geolocatio
		//alert("Your browser does not support geolocation!");
		//	}

		//}
		//google.maps.event.addDomListener(window, 'load', initialize);
	</script> </content>
</g:applyLayout>