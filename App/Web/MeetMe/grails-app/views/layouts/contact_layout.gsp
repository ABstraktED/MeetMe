<!doctype html>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<html>
<head>
<head>
<meta name="layout" content="basic_layout" />
<title><g:layoutTitle /></title>
<link rel="stylesheet"
	href="${resource(dir: 'css', file: 'meetme-event-layout.css')}"
	type="text/css">
<g:set var="userId" value="${sec.loggedInUserInfo(field:'id')}" />

<g:layoutHead />
</head>

<body>

	<div id="content-left">
		<sec:ifLoggedIn>
			<a href="<g:createLink controller="contact" action="list"/>">My
				Contacts</a>
			<br />
			<a href="<g:createLink controller="contact" action="addContact"/>">Find contact</a>
			<br />
			
		</sec:ifLoggedIn>
		<sec:ifNotLoggedIn>
			<a href="<g:createLink controller="authentication" action="login"/>">Login</a>
		</sec:ifNotLoggedIn>

	</div>

	<div id="content-right">
		<g:pageProperty name="page.event_content" />
	</div>

	<script>
		$(document).ready(function() {

			var t = 100;

			$('#a1').hover(function() {
				$(this).stop(true, false).animate({
					color : "rgb(255,255,255)"
				}, t);
				$('#menu-h-1').stop(true, false).animate({
					backgroundColor : "rgb(56,186,90)"
				}, t);
			}, function() {
				$(this).stop(true, false).animate({
					color : "rgba(0,0,0,0.4)"
				}, t);
				$('#menu-h-1').stop(true, false).animate({
					backgroundColor : "rgba(0,0,0,0.0)"
				}, t);
			});

			$('#a2').hover(function() {
				$(this).stop(true, false).animate({
					color : "rgb(255,255,255)"
				}, t);
				$('#menu-h-2').stop(true, false).animate({
					backgroundColor : "rgb(56,186,90)"
				}, t);
			}, function() {
				$(this).stop(true, false).animate({
					color : "rgba(0,0,0,0.4)"
				}, t);
				$('#menu-h-2').stop(true, false).animate({
					backgroundColor : "rgba(0,0,0,0.0)"
				}, t);
			});

			$('#a3').hover(function() {
				$(this).stop(true, false).animate({
					color : "rgb(255,255,255)"
				}, t);
				$('#menu-h-3').stop(true, false).animate({
					backgroundColor : "rgb(56,186,90)"
				}, t);
			}, function() {
				$(this).stop(true, false).animate({
					color : "rgba(0,0,0,0.4)"
				}, t);
				$('#menu-h-3').stop(true, false).animate({
					backgroundColor : "rgba(0,0,0,0.0)"
				}, t);
			});

			$('#a4').hover(function() {
				$(this).stop(true, false).animate({
					color : "rgb(255,255,255)"
				}, t);
				$('#menu-h-4').stop(true, false).animate({
					backgroundColor : "rgb(56,186,90)"
				}, t);
			}, function() {
				$(this).stop(true, false).animate({
					color : "rgba(0,0,0,0.4)"
				}, t);
				$('#menu-h-4').stop(true, false).animate({
					backgroundColor : "rgba(0,0,0,0.0)"
				}, t);
			});

		});
	</script>

</body>
</html>