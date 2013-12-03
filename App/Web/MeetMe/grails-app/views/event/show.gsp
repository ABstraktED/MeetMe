
<%@ page import="pwr.itapp.meetme.Event" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'event.label', default: 'Event')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?libraries=places&sensor=false"></script>
	</head>
	<body>
		<div>
			Title: ${eventInstance.title }
			<br/>
			Date: <g:formatDate format="dd/MM/yyyy" date="${eventInstance.date }"/>
			<br/>
			Time: <g:formatDate format="hh:mm" date="${eventInstance.date }"/>
			<br/>
			Description: ${eventInstance.description }
			<br/>
			Location: ${eventInstance.location.address }
			<br/>
			Created by: ${eventInstance.user.username }
			<br/>
			<div id="map_canvas" style="height: 350px; width: 600px"></div>
			
			<hr/>
			<div>
				<h3>Discussion</h3>
				<br/>
				<g:if test="${discussion != null }">
				<g:each in="${discussion}" var="disc">
					<br/>
					${disc.content}
					<br/>
					<g:formatDate format="dd/MM/yyyy hh:mm" date="${disc.date }"/>
					<br/>
					${disc.user.username }
					<hr/>
				</g:each>
				</g:if>
				<g:else>
					No comments yet.
				</g:else>
				<br/>
				<g:form action="newComment">
					<g:textArea name="content" placeholder="Insert comment here"/>
					<g:hiddenField name="eventId" value="${eventInstance.id }"/>
					<input type="submit" value="Submit"/>
				</g:form>
			</div>
			
			<script type="text/javascript">
      			function initialize() {
       				var mapOptions = {
         		 		center: new google.maps.LatLng(${eventInstance.location.lat}, ${eventInstance.location.lng}),
          		 		zoom: 8,
          				mapTypeId : google.maps.MapTypeId.ROADMAP,
						streetViewControl : true
       		 		};
        			var map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
     		 	}
      		google.maps.event.addDomListener(window, 'load', initialize);
   		 </script>
		</div>
	</body>
</html>
