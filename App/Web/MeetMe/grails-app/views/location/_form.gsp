<%@ page import="pwr.itapp.meetme.Location" %>



<div class="fieldcontain ${hasErrors(bean: locationInstance, field: 'address', 'error')} ">
	<label for="address">
		<g:message code="location.address.label" default="Address" />
		
	</label>
	<g:textField name="address" value="${locationInstance?.address}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: locationInstance, field: 'events', 'error')} ">
	<label for="events">
		<g:message code="location.events.label" default="Events" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${locationInstance?.events?}" var="e">
    <li><g:link controller="event" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="event" action="create" params="['location.id': locationInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'event.label', default: 'Event')])}</g:link>
</li>
</ul>

</div>

<div class="fieldcontain ${hasErrors(bean: locationInstance, field: 'latitude', 'error')} required">
	<label for="latitude">
		<g:message code="location.latitude.label" default="Latitude" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="latitude" value="${fieldValue(bean: locationInstance, field: 'latitude')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: locationInstance, field: 'longitude', 'error')} required">
	<label for="longitude">
		<g:message code="location.longitude.label" default="Longitude" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="longitude" value="${fieldValue(bean: locationInstance, field: 'longitude')}" required=""/>
</div>

