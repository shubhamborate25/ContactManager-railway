(function($) {

	"use strict";

	$('nav .dropdown').hover(function() {
		var $this = $(this);
		$this.addClass('show');
		$this.find('> a').attr('aria-expanded', true);
		$this.find('.dropdown-menu').addClass('show');
	}, function() {
		var $this = $(this);
		$this.removeClass('show');
		$this.find('> a').attr('aria-expanded', false);
		$this.find('.dropdown-menu').removeClass('show');
	});

})(jQuery);


const closebar = () => {

	document.getElementById("mySidebar").style.left = "-100%";
	document.getElementById("content").style.marginLeft = "3%";
	document.getElementById("openNav").style.display = 'block';
	document.getElementById("openNav").style.transition = "all 2s";
 
};

const openbar = () => {

	document.getElementById("mySidebar").style.left = "0%";
	document.getElementById("content").style.marginLeft = "25%";
	document.getElementById("openNav").style.display = 'none !important';
	document.getElementById("openNav").style.transition = "all 2s";
 
};
 