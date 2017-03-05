$(document).ready(function(){
	
	$('.owl-carousel').owlCarousel({
		
		loop: true,
		nav: true,
		navText: ['<i class="fa fa-angle-left" aria-hidden="true"></i>','<i class="fa fa-angle-right" aria-hidden="true"></i>'],
		animateOut: 'fadeOut',
	    items: 1,
	    margin: 10,
	    autoplay: true
		
	});
  
});