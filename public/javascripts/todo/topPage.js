
/*
 * ページ読み込み後に実行される処理
 */
$(function() {
	// ページネーションのオプション
	opt = {
			pager		:$('.pager'),
			prevText		: 'prev',
			nextText		: 'next',
			firstText	: 'first',
			lastText		: 'last',
			ellipsisText: '...',
			viewNum		: 5,
			pagerNum		: 5,
			ellipsis		: false,
			linkInvalid	: true,
			goTop		: false,
			firstLastNav	: true,
			prevNextNav	: true
		}

	$.when(function() {
		var dfd = jQuery.Deferred();
		// タスクデータの取得
		$.get('getTaskData', function(data) {
			taskData = data;
			console.dir(taskData);
			dfd.resolve();
		});
		return dfd.promise();
	}()).done(function() {
		$.when(function() {
			var dfd = jQuery.Deferred();
			(function() {
				renderTaskTable(filterData(taskData, getValidFilterNames()));
				dfd.resolve();
			}());
			return dfd.promise();
		}()).done(function() {
			// ページネーション
			$('.pagination-data').pagination(opt);

			// フィルターボタン
			$('.filter-btn').on('click', 'li > a', function(event) {
				event.preventDefault();
				var li = $(this).parent();
				// ボタンのON/OFFを変更
				if(li.hasClass('on')) {
					console.log('on -> off');
					li
					.removeClass('on')
					.addClass('off');
				}
				else {
					console.log('off -> on');
					li
					.removeClass('off')
					.addClass('on');
				}
				// タスクテーブルの再描画
				renderTaskTable(filterData(taskData, getValidFilterNames()));
				// ページネーション
				$('.pagination-data').pagination(opt);
			});
		});
	});
});
