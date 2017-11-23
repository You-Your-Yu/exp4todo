$.fn.pagination = function(config) {

	// オプション
	var o = $.extend({
		pager			: $('.pager'),
		prevText			: $('prev'),
		nextText			: $('next'),
		firstText		: $('first'),
		lastText			: $('last'),
		ellipsisText		: $('…'),
		viewNum			: 8,
		pagerNum			: 3,
		ellipsis			: true,
		linkInvalid		: false,
		goTop			: true,
		firstLastNav		: true,
		prevNextNav		: true
	}, config);

	// セレクタ
	var $element = $(this);
	var $pager = o.pager;
	if(o.ellipsis) {
		var $ellipsisFirst = $('<span class="ellipsis"/>').text(o.ellipsisText);
		var $ellipsisLast = $('<span class="ellipsis"/>').text(o.ellipsisText);
	}

	// 値取得
	var totalItemNum = $element.length;
	var totalPageNum = Math.ceil(totalItemNum / o.viewNum);
	var ellipsisFlag = false;

	console.log(' >>> pagination: $element.length = ' + $element.length);

	// 変数設定
	var currentIndex = 0;

	// 省略記号フラグ判定
	if(totalPageNum > o.pagerNum) {
		ellipsisFlag = true;
	}

	// ページャーの生成
	for (var i = 0; i < totalPageNum; i++) {
		$('<span/>').text(i + 1).appendTo($pager);
	};
	if(o.prevNextNav) {
		$('<span class="prev"/>').text(o.prevText).prependTo($pager);
		$('<span class="next"/>').text(o.nextText).appendTo($pager);
	}
	if(o.firstLastNav) {
		$('<span class="first"/>').text(o.firstText).prependTo($pager);
		$('<span class="last"/>').text(o.lastText).appendTo($pager);
	}
	var $pagerInner = $pager.find('span').not('.prev, .next, .first, .last');

	// コンテンツの初期表示
	$element.hide();
	for(var i = 0; i < o.viewNum; i++) {
		$($element[i]).show();
	};

	// ページャーの初期表示
	$pagerInner.hide();
	for(var i = 0; i < o.pagerNum; i++) {
		$($pagerInner[i]).show();
	};
	$($pagerInner[0]).addClass('current');
	if(o.linkInvalid) {
		$('.prev').addClass('invalid');
		$('.first').addClass('invalid');
		if($pagerInner.length <= 1) {
			$('.next').addClass('invalid');
			$('.last').addClass('invalid');
		}
	}
	if(o.ellipsis) {
		if(ellipsisFlag) {
			if(totalPageNum - o.pagerNum > 1) {
				$($pagerInner[totalPageNum - 1]).before(function() {return $ellipsisLast;});
			}
			$($pagerInner[totalPageNum - 1]).show();
		}
	}

	// 最初のボタンをクリック
	$('.first').on('click', function() {
		var current = 0;
		if(currentIndex == 0) {
			if(o.linkInvalid) {
				return false;
			}
		}
		change(current);
	});

	// 最後のボタンをクリック
	$('.last').on('click', function() {
		var current = totalPageNum - 1;
		if(currentIndex == $pagerInner.length - 1) {
			if(o.linkInvalid) {
				return false;
			}
		}
		change(current);
	});

	// 前のボタンをクリック
	$('.prev').on('click', function() {
		var current = currentIndex - 1;
		if(currentIndex == 0) {
			if(o.linkInvalid) {
				return false;
			}
			else {
				current = currentIndex;
			}
		}
		change(current);
	});

	// 次のボタンをクリック
	$('.next').on('click', function() {
		var current = currentIndex + 1;
		if(currentIndex == $pagerInner.length - 1) {
			if(o.linkInvalid) {
				return false;
			}
			else {
				current = currentIndex;
			}
		}
		change(current);
	});

	// 番号をクリック
	$pagerInner.each(function(current) {
		$(this).on('click', function(){
			if($(this).hasClass('current')) {
				if(o.linkInvalid) {
					return false;
				}
			}
			change(current);
		});
	});

	// 切り替え実行
	var change = function(current) {
		// コンテンツの表示
		$element.hide();
		for(var i = (current * o.viewNum); i < ((current + 1) * o.viewNum); i++) {
			$($element[i]).show();
		}
		// ページャーの表示
		$pagerInner.hide();
		if(o.ellipsis) {
			if(ellipsisFlag) {
				$ellipsisFirst.remove();
				$ellipsisLast.remove();
			}
		}
		var num = current - 1;
		if(num > totalPageNum - o.pagerNum) {
			num = totalPageNum - o.pagerNum;
		}
		else if(num < 0) {
			num = 0;
		}
		for(var i = num; i < (num + o.pagerNum); i++) {
			$($pagerInner[i]).show();
		};
		// 省略記号の表示
		if(o.ellipsis) {
			if(ellipsisFlag) {
				// 前の省略記号
				$($pagerInner[0]).show();
				if(num > 1) {
					$($pagerInner[0]).after(function() {return $ellipsisFirst;});
				}
				// 後ろの省略記号
				$($pagerInner[0]).show();
				if(num < (totalPageNum - o.pagerNum - 1)) {
					$($pagerInner[totalPageNum - 1]).before(function() {return $ellipsisLast;});
				}
				$($pagerInner[totalPageNum - 1]).show();
			}
		}
		// 現在位置設定
		currentIndex = current;
		// デザイン
		$pagerInner.removeClass('current');
		$($pagerInner[current]).addClass('current');
		if(o.linkInvalid) {
			if(current == 0) {
				$('.prev').addClass('invalid');
				$('.first').addClass('invalid');
			}
			else {
				$('.prev').removeClass('invalid');
				$('.first').removeClass('invalid');
			}
			if(current == totalPageNum - 1) {
				$('.next').addClass('invalid');
				$('.last').addClass('invalid');
			}
			else {
				$('.next').removeClass('invalid');
				$('.last').removeClass('invalid');
			}
		}
		if(o.goTop) {
			$('html, body').scrollTop(0);
		}
	}
}
