/* login */
$(function() {
	$('.login').on('click', function() {
		//alert('login');
		$('#login-form').attr('action', '/login')
	});
	$('.register').on('click', function() {
		//alert('register');
		$('#login-form').attr('action', '/register');
	});
});
