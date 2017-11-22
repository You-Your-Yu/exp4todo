$(function() {
	function joinTeam() {

	}
	function cretateTeam() {

	}
	function leaveTeam() {

	}
	function changePW() {
		var currentPW = prompt('現在のパスワードを入力してください。');
		if(currentPW) {
			var newPW = prompt('新しいパスワードを入力してください。');
			if(newPW) {
				$.ajax({
				    //設定
				    url  : 'changePW',
				    type : "POST",
				    data : {'currentPW': currentPW, 'newPW': newPW},

				    //ajax通信エラー
				    error : function(XMLHttpRequest, textStatus, errorThrown) {
				        console.log('ajax通信に失敗しました');
				        console.log('XMLHttpRequest : ' + XMLHttpRequest.status);
				        console.log('textStatus     : ' + textStatus);
				        console.log('errorThrown    : ' + errorThrown.message);
				    },
				    //ajax通信成功
				    success : function(response) {
				        console.log('changePW');
				        alert(response.msg);
				    }
				});
			}
		}
	}
	function withdraw() {
		var result = confirm('退会しますか?');
		if(result) {
			result = confirm('退会すると全てのタスク情報が削除されますが本当によろしいですか？');
			if(result) {
				location.href = 'withdraw';
			}
		}
	}

	$('.join-team').on('click', function() {
	});
	$('.create-team').on('click', function() {

	});
	$('.leave-team').on('click', function() {

	});
	$('.change-pw').on('click', function() {
		event.preventDefault();
		changePW();
	});
	$('.withdraw').on('click', function(event) {
		event.preventDefault();
		withdraw();
	});
});