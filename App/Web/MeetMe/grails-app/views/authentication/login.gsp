
<html>

<head>
<title><g:message code='spring.security.ui.login.title' /></title>
<meta name='layout' content='register' />

<link rel="stylesheet" href="${resource(dir: 'css', file: 'meetme-auth.css')}" type="text/css">

</head>

<body>
	
	<div class="container">
	
		<div id="logo">
		</div>
		<div id="title">
			too fast, too easy
		</div>
		<div id="subtitle">
			Meet anyone, anywhere, without any effort.
		</div>
		
		<div id="form-cont">
		
			<form action='${postUrl}' method='POST'
				name="loginForm" autocomplete='off'>
				
				<div>

					<g:if test="${flash.message}">
						<div class="login-message">
							${flash.message}
						</div>
					</g:if>

					<input type="text" name="j_username" class="form-control clearfix" placeholder="username" required="" autofocus="">
					<input type="password" name="j_password" class="form-control clearfix" placeholder="password" required="">
					<button type="submit" id="login-btn" class="form-control clearfix">login</button>	
				
					<div id="login-help">
						<input type="checkbox" class="checkbox"
								name="${rememberMeParameter}" id="remember_me" checked="checked" />
						<label for='remember_me'>
							<g:message code='spring.security.ui.login.rememberme' />
						</label> | <span class="forgot-link"> 
							<g:link controller='register' action='forgotPassword'>
								<g:message code='spring.security.ui.login.forgotPassword' />
							</g:link>
						</span>
					</div>
					
					<div class="link-container">
						<a href="${createLink(controller:'user', action:'create')}" id="join-us">join us</a>
					</div>
						
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