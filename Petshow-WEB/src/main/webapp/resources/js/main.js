$(document).ready(function() {
			
	$("#menu-toggle").click(function(e) {
        
		e.preventDefault();
        $("#wrapper").toggleClass("toggled");
        
        var menuToggle = $(this);
        var active = menuToggle.hasClass("active");
                
		if (active) {
			
			menuToggle.removeClass("active");
			menuToggle.prop("title", "Expandir Menu?");
			menuToggle.find("i").removeClass("fa-angle-double-left").addClass("fa-angle-double-right");
			
			//addCookie("collapsed", "true");
			
		} else {
			
			menuToggle.addClass("active");
			menuToggle.prop("title", "Recolher Menu?");
			menuToggle.find("i").removeClass("fa-angle-double-right").addClass("fa-angle-double-left");
			
			//addCookie("collapsed", "false");
			
		}
				
		menuToggle.blur();
        
    });
	
	var menuToggle = $("#menu-toggle");
	menuToggle.addClass("active");
	menuToggle.prop("title", "Recolher Menu?");
	menuToggle.find("i").removeClass("fa-angle-double-right").addClass("fa-angle-double-left");
    		
});


function setFocus(idFormElement) {
    document.getElementById(idFormElement).focus();
}
