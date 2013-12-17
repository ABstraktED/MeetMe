
<%@ page import="pwr.itapp.meetme.Event"%>
<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<g:set var="entityName"
	value="${message(code: 'event.label', default: 'Event')}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>
<script type="text/javascript"
	src="http://maps.googleapis.com/maps/api/js?libraries=places&sensor=false"></script>
</head>
<body>
	<div style="display: inline-block;">
		<div style="float: left; padding-right: 100px">
			Title:
			${eventInstance.title }
			<br /> Date:
			<g:formatDate format="dd/MM/yyyy" date="${eventInstance.date }" />
			<br /> Time:
			<g:formatDate format="hh:mm" date="${eventInstance.date }" />
			<br /> Description:
			${eventInstance.description }
			<br /> Location:
			${eventInstance.location.address }
			<br /> Created by:
			${eventInstance.user.username }
			<br />
		</div>
		<div style="float: right">
			<g:if test="${userInvited > 0}">
				<div id="answerInvite">
					<g:form controller="invitation" action="acceptInvitation">
						<g:hiddenField name="eventId" value="${eventInstance.id }" />
						<input type="submit" value="Accept"/>
					</g:form>
					<g:form controller="invitation" action="rejectInvitation">
						<g:hiddenField name="eventId" value="${eventInstance.id }" />
						<input type="submit" value="reject"/>
					</g:form>
				</div>
			</g:if>
			<div id="invite">
				<h3>Invite</h3>
				<g:form controller="Invitation" action="inviteByPortal">
					<g:textField name="email" />
					<g:hiddenField name="eventId" value="${eventInstance.id }" />
					<input type="submit" value="Invite" />
				</g:form>
			</div>
			<div id="googleContact">
				<a href="<g:createLink controller="invitation" action="inviteFromGoogleContacts"/>?eventId=${eventInstance.id}">Invite from google contacts</a>
			</div>
			<div id="invited">
				<h3>Invited</h3>
				<g:each in="${invited }" var="inv">
					Username: ${inv.user.username }
					<br />
					Email: ${inv.user.email }
				</g:each>
			</div>
		</div>
	</div>
	<div style="display: inline-block;">
		<div id="map_canvas" style="height: 350px; width: 600px; float: left;"></div>
		<hr />
	</div>
	<div>
		<h3>Discussion</h3>
		<br />
		<g:if test="${discussion != null }">
			<g:each in="${discussion}" var="disc">
				<br />
				${disc.content}
				<br />
				<g:formatDate format="dd/MM/yyyy hh:mm" date="${disc.date }" />
				<br />
				${disc.user.username }
				<hr />
			</g:each>
		</g:if>
		<g:else>
					No comments yet.
				</g:else>
		<br />
		<g:form action="newComment">
			<g:textArea name="content" placeholder="Insert comment here" />
			<g:hiddenField name="eventId" value="${eventInstance.id }" />
			<input type="submit" value="Submit" />
		</g:form>
	</div>

	<script type="text/javascript">
		$(document).ready(function(){
				$("#email").autocomplete({
							source: "${createLink(controller: 'Invitation', action: 'userList')}"
						});
					});
			
      			function initialize() {
      				var pos = new google.maps.LatLng(${eventInstance.location.lat}, ${eventInstance.location.lng});
       				var mapOptions = {
         		 		center: pos,
          		 		zoom: 16,
          				mapTypeId : google.maps.MapTypeId.ROADMAP,
						streetViewControl : true
       		 		};
        			var map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
        			
					var marker = new google.maps.Marker({
						title : "Your location",
						map : map,
						position : pos,
						content : 'Here'
					});
     		 	}
      		google.maps.event.addDomListener(window, 'load', initialize);
   		 </script>
	</div>
</body>
</html>
