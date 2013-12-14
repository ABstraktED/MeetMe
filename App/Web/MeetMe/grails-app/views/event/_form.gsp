<%@ page import="pwr.itapp.meetme.Event" %>



<div class="fieldcontain ${hasErrors(bean: eventInstance, field: 'date', 'error')} ">
	<label for="date">
		<g:message code="event.date.label" default="Date" />
		
	</label>
	
</div>

<div class="fieldcontain ${hasErrors(bean: eventInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="event.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${eventInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eventInstance, field: 'invitations', 'error')} ">
	<label for="invitations">
		<g:message code="event.invitations.label" default="Invitations" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${eventInstance?.invitations?}" var="i">
    <li><g:link controller="invitation" action="show" id="${i.id}">${i?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="invitation" action="create" params="['event.id': eventInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'invitation.label', default: 'Invitation')])}</g:link>
</li>
</ul>

</div>

<div class="fieldcontain ${hasErrors(bean: eventInstance, field: 'location', 'error')} ">
	<label for="location">
		<g:message code="event.location.label" default="Location" />
		
	</label>
	<g:select id="location" name="location.id" from="${pwr.itapp.meetme.Location.list()}" optionKey="id" value="${eventInstance?.location?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eventInstance, field: 'title', 'error')} ">
	<label for="title">
		<g:message code="event.title.label" default="Title" />
		
	</label>
	<g:textField name="title" value="${eventInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eventInstance, field: 'user', 'error')} ">
	<label for="user">
		<g:message code="event.user.label" default="User" />
		
	</label>
	<g:select id="user" name="user.id" from="${pwr.itapp.meetme.auth.User.list()}" optionKey="id" value="${eventInstance?.user?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

