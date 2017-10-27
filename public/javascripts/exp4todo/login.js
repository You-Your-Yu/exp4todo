/* login */
// TODO: トラバースによる参照に書き換え
$(function() {
	$('.login').on('click', function() {
		$('#login-form').attr('action', 'todo.Login/login')
	});
	$('.register').on('click', function() {
		$('#login-form').attr('action', '/todo.Login/register');
	});
});
