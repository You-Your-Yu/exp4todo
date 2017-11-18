$(function() {
	var tabId = 1;
	$('.tab-contents').children().each(function() {
		$this = $(this);
		if($this.data('contentid') != tabId) {
			$this.hide();
		}
	});

	$('.tab-menu')
	.on('click', 'li > a', function(event) {
		event.preventDefault();
		var $this = $(this);

		// ボタンのアピアランスを変更する
		$this.parent().siblings()
		.removeClass('selected')
		.end()
		.addClass('selected');

		// コンテンツを切り替える
		var tabId = $this.data('tabid');
		$this.closest('.tab').find('.tab-contents').children()
		.each(function() {
			var $content = $(this);
			if($content.data('contentid') == tabId) {
				$content.show();
			}
			else {
				$content.hide();
			}
		});
	});
});