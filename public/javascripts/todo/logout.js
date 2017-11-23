
function logout() {
	var conf = confirm('ログアウトしますか？');
	if(conf) {
		location.href = '/todo.login/logout';
	}
}