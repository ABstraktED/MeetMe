
<html>

<head>
<title><g:message code='spring.security.ui.login.title' /></title>
<meta name='layout' content='register' />
</head>

<body>
	<div class="login s2ui_center ui-corner-all"
		style='text-align: center;'>
		<div class="login-inner">
			<g:if test="${flash.message}">
				<div class="message">
					${flash.message}
				</div>
			</g:if>
			<form action='${postUrl}' method='POST' id="loginForm"
				name="loginForm" autocomplete='off'>
				<div class="sign-in">

					<h1>
						<g:message code='spring.security.ui.login.signin' />
					</h1>

					<table>
						<tr>
							<td><label for="username">Email or phone</label></td>
							<td><input name="j_username" id="username" size="20" /></td>
						</tr>
						<tr>
							<td><label for="password">Password</label></td>
							<td><input type="password" name="j_password" id="password"
								size="20" /></td>
						</tr>
						<tr>
							<td colspan='2'><input type="checkbox" class="checkbox"
								name="${rememberMeParameter}" id="remember_me" checked="checked" />
								<label for='remember_me'><g:message
										code='spring.security.ui.login.rememberme' /></label> | <span
								class="forgot-link"> <g:link controller='register'
										action='forgotPassword'>
										<g:message code='spring.security.ui.login.forgotPassword' />
									</g:link>
							</span></td>
						</tr>
						<tr>
							<td colspan='2'>
							<a href="${createLink(controller:'user', action:'create')}">Register</a>
							<button type="submit">Log in</button></td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</div>

	<script>
		$(document).ready(function() {
			$('#username').focus();
		});
	</script>

</body>
</html>