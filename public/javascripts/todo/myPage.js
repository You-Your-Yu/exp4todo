$(function() {
	function joinTeam() {
		var tid = prompt('チームIDを入力してください。');
		if(tid) {
			$.ajax({
			    //設定
			    url  : 'findTeam',
			    type : "POST",
			    data : {'tid': tid},

			    //ajax通信エラー
			    error : function(XMLHttpRequest, textStatus, errorThrown) {
			        console.log('ajax通信に失敗しました');
			        console.log('XMLHttpRequest : ' + XMLHttpRequest.status);
			        console.log('textStatus     : ' + textStatus);
			        console.log('errorThrown    : ' + errorThrown.message);
			        alert('ajax通信に失敗しました');
			    },
			    //ajax通信成功
			    success : function(response) {
			        console.log('findTeam');
			        if(response.result) {
			        		var msg = '次のチームが見つかりました。参加しますか？\n'
			        			+ 'チームID: ' + response.tid + '\n'
			        			+ 'チーム名: ' + response.name;
			        		var conf = confirm(msg);
			        		if(conf) {
			        			$.post('joinTeam', {'tid': response.tid})
			        			.done(function() {
			        				alert('参加しました。')
			        			});
			        		}
			        }
			        else {
			        		alert('チームが見つかりませんでした。')
			        }
			    }
		    });
		}
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
				        alsert('ajax通信に失敗しました');
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

	$('.join-team').on('click', function(event) {
		event.preventDefault();
		joinTeam();
	});
	$('.leave-team').on('click', function(event) {
		event.preventDefault();
	});
	$('.change-pw').on('click', function(event) {
		event.preventDefault();
		changePW();
	});
	$('.withdraw').on('click', function(event) {
		event.preventDefault();
		withdraw();
	});
});