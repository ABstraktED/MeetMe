
<%@ page import="pwr.itapp.meetme.Event"%>
<g:applyLayout name="event_layout">
<head>
<g:set var="entityName"
	value="${message(code: 'event.label', default: 'Event')}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>
<script type="text/javascript"
	src="http://maps.googleapis.com/maps/api/js?libraries=places&sensor=false"></script>
</head>
<content tag="event_content">
	<div style="display: inline-block;">
		<div style="float: left; padding-right: 100px">
			<g:message code="lbl.event.title"/>
			${eventInstance.title }
			<br /> <g:message code="lbl.event.date"/>
			<g:formatDate format="dd/MM/yyyy" date="${eventInstance.date }" />
			<br /> <g:message code="lbl.event.time"/>
			<g:formatDate format="hh:mm" date="${eventInstance.date }" />
			<br /> <g:message code="lbl.event.description"/>
			${eventInstance.description }
			<br /> <g:message code="lbl.event.location"/>
			${eventInstance.location.address }
			<br /> <g:message code="lbl.event.creator"/>
			${eventInstance.user.username }
			<br />
		</div>
		<div style="float: right">
			<g:if test="${userInvited != null && !userInvited.confirmation}">
				<div id="answerInvite">
					<g:message code="msg.event.invitedToAttend"/>
					<g:form controller="invitation" action="acceptInvitation">
						<g:hiddenField name="eventId" value="${eventInstance.id }" />
						<input type="submit" value="Accept" />
					</g:form>
				</div>
			</g:if>
			<g:if test="${userInvited != null && userInvited.confirmation }">
				<g:message code="msg.event.willAttend"/>
			</g:if>
			<div id="invite">
				<h3><g:message code="lbl.event.invite"/></h3>
				<g:form controller="Invitation" action="inviteByPortal">
					<g:textField name="email" />
					<g:hiddenField name="eventId" value="${eventInstance.id }" />
					<input type="submit" value="Invite" />
				</g:form>
			</div>
			<div id="googleContact">
				<a
					href="<g:createLink controller="invitation" action="inviteFromGoogleContacts"/>?eventId=${eventInstance.id}">
					<g:message code="value.event.inviteGoogleContact"/></a>
			</div>
			<div id="invited">
				<h3><g:message code="lbl.event.invited"/></h3>
				<g:each in="${invited }" var="inv">
					<g:message code="lbl.event.userName"/> ${inv.user.username }
					<br />
					<g:message code="lbl.event.email"/> ${inv.user.email }
				</g:each>
			</div>
		</div>
	</div>
	<div style="display: inline-block;">
		<div id="map_canvas" style="height: 350px; width: 600px; float: left;"></div>
		<hr />
	</div>
	<div>
		<h3><g:message code="lbl.event.discussion"/></h3>
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
			<g:message code="value.event.noComment"/>
		</g:else>
		<br />
		<g:form action="newComment">
			<g:set var="pholder" value="${message(code: 'placeholder.event.insertComment')}"/>
			<g:textArea name="content" placeholder="[pholder]" />
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
</content>
</g:applyLayout>
