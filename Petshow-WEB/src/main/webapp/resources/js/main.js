$(document).ready(function() {
				
	$(".texto").on("keyup", function() {
        if (this.value[0] != this.value[0].toUpperCase()) {
            var start = this.selectionStart;
            var end = this.selectionEnd; 
            this.value = this.value[0].toUpperCase() + this.value.substring(1);
            this.setSelectionRange(start, end);
        }
    });
		
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
		
	/*var collapsed = getCookie("collapsed");
	var menuToggle = $("#menu-toggle");
		
    if (collapsed === "false") {    	
    	
    	menuToggle.addClass("active");
    	menuToggle.prop("title", "Recolher Menu?");
    	menuToggle.find("i").removeClass("fa-angle-double-right").addClass("fa-angle-double-left");
    	    	        
    } else {
    	
    	$("#wrapper").addClass("toggled");
    	
    	menuToggle.removeClass("active");
    	menuToggle.prop("title", "Expandir Menu?");
    	menuToggle.find("i").removeClass("fa-angle-double-left").addClass("fa-angle-double-right");
    	
    }*/
    	
	$(".tree-toggle").click(function () {
		
		var active = "";
		var i = "";
		var iActive = "";
						
		$(this).parent().children("ul.tree").slideToggle(300);
		
		i = $(this).find(".fa-plus");
		if (i.length > 0) {
			
			active = $(".active-tree-toggle");
			
			if (active.length > 0) {
				
				active.parent().children("ul.tree").slideToggle(300);
								
				iActive = active.find(".fa-minus");
				
				if (iActive.length > 0) {
					
					iActive.removeClass("fa-minus");
					iActive.addClass("fa-plus");
					active.removeClass("active-tree-toggle");
					
				}
				
			}
			
			$(this).addClass("active-tree-toggle");
			i.removeClass("fa-plus");
			i.addClass("fa-minus");
			
		} else {
			
			i = $(this).find(".fa-minus");
			
			if (i.length > 0) {
				
				$(this).removeClass("active-tree-toggle");
				i.removeClass("fa-minus");
				i.addClass("fa-plus");
				
			}
						
		}
		
		$(this).blur();
		
	});
		
});

function initCookie(cname, cvalue) {
	
    var d = new Date();
    d.setTime(d.getTime() + (1*24*60*60*1000));
    
    newCookie();
    addCookie(cname, cvalue);
    addCookie("expires", d.toUTCString());
    addCookie("path", "/");
    
}

function newCookie() {    
    document.cookie = "";
}

function addCookie(cname, cvalue) {    
    document.cookie += cname + "=" + cvalue + ";";
}

function getCookie(cname) {
	
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    
    for (var i = 0; i <ca.length; i++) {
    	
        var c = ca[i];
        
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
        
    }
    
    return "";
    
}

PrimeFaces.locales['pt'] = {
    closeText: 'Fechar',
    prevText: 'Anterior',
    nextText: 'Pr&oacute;ximo',
    currentText: 'Come&ccedil;o',
    monthNames: ['Janeiro','Fevereiro','Mar&ccedil;o','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun', 'Jul','Ago','Set','Out','Nov','Des'],
    dayNames: ['Domingo','Segunda','Ter&ccedil;a','Quarta','Quinta','Sexta','S&aacute;bado'],
    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','S&aacute;b'],
    dayNamesMin: ['D','S','T','Q','Q','S','S'],
    weekHeader: 'Semana',
    firstDay: 1,
    isRTL: false,
    showMonthAfterYear: false,
    yearSuffix: '',
    timeOnlyTitle: 'S&oacute; Horas',
    timeText: 'Tempo',
    hourText: 'Hora',
    minuteText: 'Minuto',
    secondText: 'Segundo',
    currentText: 'Data Atual',
    ampm: false,
    month: 'M&ecirc;s',
    week: 'Semana',
    day: 'Dia',
    allDayText : 'Todo Dia'
};

PrimeFaces.widget.DataTable = PrimeFaces.widget.DataTable.extend({
	
    clearCustomFilters: function() {
    	
    	this.thead.find('> tr > th.ui-filter-column > .ui-column-customfilter :input').val('');
    	this.thead.find('> tr > th.ui-filter-column > .ui-column-title :input').val('');
    	//this.filter();
        
    },

	initSort: function() {
		
		//var $this = this;
    	this.sortableColumns = this.thead.find('> tr > th.ui-sortable-column');
    			
		for(var i = 0; i < this.sortableColumns.length; i++) {
			
            var columnHeader = this.sortableColumns.eq(i);
            /*var sortIcon = columnHeader.children('span.ui-sortable-column-icon');
            var sortOrder = null;*/
            
            if(columnHeader.hasClass('ui-state-active')) {
            	
            	columnHeader.removeClass('ui-state-active');
            	/*sortOrder = this.SORT_ORDER.UNSORTED;
                this.sortableColumns.eq(0).attr('aria-sort', 'other');
                
                columnHeader.data('sortorder', sortOrder);
                
                $this.sort(columnHeader, $this.SORT_ORDER.ASCENDING);*/
                
            }
                                    
		}
		
	}

});
/*
PrimeFaces.widget.DataTable.prototype.setupStickyHeader = function() {
	
	var table = this.thead.parent(),
	offset = table.offset(),
	win = $(window),
	$this = this,
	stickyNS = 'scroll.' + this.id,
	resizeNS = 'resize.sticky-' + this.id,
	layoutHeaderHeight = $('#layout-header').height();

	this.cloneContainer = $('<div class="ui-datatable ui-datatable-sticky ui-widget"><table></table></div>');
	this.clone = this.thead.clone(true);
	this.cloneContainer.children('table').append(this.clone);
	this.cloneContainer.hide();

	this.cloneContainer.css({position : 'absolute', top : offset.top}).appendTo(this.jq);

	win.off(stickyNS).on(stickyNS, function() {
		
		var scrollTop = win.scrollTop(), tableOffset = table.offset();

		if (scrollTop > tableOffset.top) {
			
			$this.cloneContainer.css('top', scrollTop).addClass('ui-shadow ui-sticky');
	
			if (scrollTop >= (tableOffset.top + $this.tbody.height())) {
				$this.cloneContainer.hide();
			} else {
				$this.cloneContainer.show();
			}
			
		} else {
			
			$this.cloneContainer.css('top', scrollTop).removeClass('ui-shadow ui-sticky');
			$this.cloneContainer.hide();
			
		}
		
	}).off(resizeNS).on(resizeNS, function() {
		$this.cloneContainer.width(table.outerWidth());
	});

	//filter support
	this.thead.find('.ui-column-filter').prop('disabled', true);
	
};
*/

var origPostShow = PrimeFaces.widget.Dialog.prototype.postShow;

PrimeFaces.widget.Dialog.prototype.postShow = function () {
	
    this.initSize();
    origPostShow.apply( this );
    
};

PrimeFaces.widget.Dialog.prototype.fitViewport = function () {
	
    var winHeight = $( window ).height();
    var contentPadding = this.content.innerHeight() - this.content.height();
    
    // 57 px é a altura do componente de Mensagem
    this.content.css("max-height", (winHeight - 57 - this.titlebar.outerHeight() - contentPadding - this.footer.outerHeight()) + "px" );
    this.content.css("overflow", "auto");
    
};

function onEnter(event) {
	
	var enterCode = event.keyCode || event.which || event.charCode;
	
	if (enterCode === 13) {
		return /textarea/i.test((event.target || event.srcElement).tagName);
	}	
	return true;
	
}

function resetLiveScroll(widgetVar){
		
	dt = PF(widgetVar);
	
	if (dt !== null) {
		
		dt.restoreScrollState();
		
        if(dt.cfg.liveScroll) {
        	
        	dt.scrollOffset = 0;
        	dt.cfg.liveScrollBuffer = (100 - dt.cfg.liveScrollBuffer) / 100;
        	dt.shouldLiveScroll = true;       
        	dt.loadingLiveScroll = false;
        	dt.allLoadedLiveScroll = dt.cfg.scrollStep >= dt.cfg.scrollLimit;
            
        }
	    
	    dt.scrollBody.scrollTop(1);
	    
	}
    
}

function sair() {
	window.parent.$('.ui-dialog-titlebar-close').click();
}

jQuery(function($) {
	/*
	$('input').on("keypress", function(e) {

	    if (e.keyCode == 13) {

	        var inputs = $(this).parents("form").eq(0).find(":input");
	        var idx = inputs.index(this);
	
	        if (idx == inputs.length - 1) {
	            inputs[0].select();
	        } else {
	            inputs[idx + 1].focus();
	            inputs[idx + 1].select();
	        }
	        return false;
	    }
	});
*/
	/*
	$('input').keypress( function(e) {  
	    
		var key = e.charCode ? e.charCode : e.keyCode ? e.keyCode : 0;  
	    
		if(key == 13) {
			
	        e.preventDefault();  
	        var inputs = $(this).closest('form').find(':input:visible');
	        
	        for (var index = 1; index < inputs.length; index++) {
	        	
	        	nextInput = inputs.eq(inputs.index(this) + index);
	        	
	        	if (!nextInput[0].disabled) {
	        		
	        		nextInput[0].focus();
	        		return false;
	        		
	        	}
	        	
	        }
	        
	        
	    }
		
	});*/
		
});
/*
var nextFocus = function(e, el) {  
    
	var key = e.charCode ? e.charCode : e.keyCode ? e.keyCode : 0;  
    
	if(key == 13) {
		
        e.preventDefault();
        var inputs = $(el).closest('form').find(':input:visible');
        
        for (var index = 1; index < inputs.length; index++) {
        	
        	nextInput = inputs.eq(inputs.index(el) + index);
        	
        	if (!nextInput[0].disabled) {
        		
        		nextInput[0].focus();
        		return false;
        		
        	}
        	
        }
        
    }

};
*/

var blockEnter = function(e) {  
    
	var key = e.charCode ? e.charCode : e.keyCode ? e.keyCode : 0;  
    
	if(key == 13) {
		
		e.preventDefault();		
        return false;
        
    }

};

var focusTo = function(e, toElId) {  
    
	var key = e.charCode ? e.charCode : e.keyCode ? e.keyCode : 0;  
    
	if(key == 9) {
		
		e.preventDefault();
		$("#" + toElId).focus();
		
        //return false;
        
    }

};

var uppercaseFirstLetter = function(el) {
	
    if (el.value !== "") {
    	
    	if (el.value[0] != el.value[0].toUpperCase()) {
    		
	        var start = el.selectionStart;
	        var end = el.selectionEnd; 
	        el.value = el.value[0].toUpperCase() + el.value.substring(1);
	        el.setSelectionRange(start, end);
	        
    	}
    	    	
    }
    
};

var uppercase = function(el) {
	
    if (el.value !== "") {
    	
    	var start = el.selectionStart;
        var end = el.selectionEnd;
    	
    	el.value = el.value.toUpperCase();	
    	
    	el.setSelectionRange(start, end);
    	
    }
    
};

var capitalize = function(el) {
	
	if (el.value !== "") {
		
		var start = el.selectionStart;
        var end = el.selectionEnd;
        
		el.value = el.value.replace(/(?:^|\s)\S/g, function(a) { return a.toUpperCase(); });
		
		el.setSelectionRange(start, end);
		
	}
	
};

function isCapslock(e) {

    e = (e) ? e : window.event;

    var charCode = false;
    if (e.which) {
        charCode = e.which;
    } else if (e.keyCode) {
        charCode = e.keyCode;
    }

    var shifton = false;
    if (e.shiftKey) {
        shifton = e.shiftKey;
    } else if (e.modifiers) {
        shifton = !!(e.modifiers & 4);
    }

    if (charCode >= 97 && charCode <= 122 && shifton) {
        return true;
    }

    if (charCode >= 65 && charCode <= 90 && !shifton) {
        return true;
    }

    return false;

}

var setFocusEnd = function(el) {	
	
	setCaretPosition(el, el.value.length);
	
};

var setCaretPosition = function(el, caretPos) {
		
	if (el !== null) {
	    
	    if (el.createTextRange) {
	        
	    	var range = el.createTextRange();
	        range.move('character', caretPos);
	        range.select();
	        return true;
	        
	    } else {
	    	
	        if (el.selectionStart || el.selectionStart === 0) {
	        	
	            el.focus();
	            el.setSelectionRange(caretPos, caretPos);
	            return true;
	            
	        } else  {
	        	
	            el.focus();
	            return false;
	            
	        }
	        
	    }
	    
	}
	
};


function autocompleteItemSelect(ele) {
	
	var autocomplete = $(document.getElementById(ele));	
    var input = autocomplete.find('.ui-autocomplete-input-token input');
    
    input.attr('placeholder', '');
    	
}

function autocompleteItemUnselect(ele) {
	
	var autocomplete = $(document.getElementById(ele));
	var li = autocomplete.find('ul > li');
    var input = autocomplete.find('.ui-autocomplete-input-token input');
    
    if (li.length == 1) {
    	input.attr('placeholder', 'Todos');
	}
    	
}

Pace.options = {
	minTime: 60000
};

Pace.on("start", function() {
	//PF("lightbox").show();
	paceStart();
});
Pace.on("restart", function() {
	//PF("lightbox").show();
	paceStart();
});
Pace.on("done", function() {
	//PF("lightbox").hide();
	paceDone();	
});

var paceStart = function() {
	
	var lightbox = $(document).find("#lightbox");
	
	lightbox.removeClass("ui-lightbox ui-widget ui-helper-hidden ui-corner-all ui-shadow");
	lightbox.addClass("ui-widget-overlay");
	
};

var paceDone = function() {
	
	var lightbox = $(document).find("#lightbox");
	
	lightbox.removeClass("ui-widget-overlay");
	lightbox.addClass("ui-lightbox ui-widget ui-helper-hidden ui-corner-all ui-shadow");
	
};

var paceDoneParent = function() {
	
	var lightbox = $(parent.document).find("#lightbox");
	
	lightbox.removeClass("ui-widget-overlay");
	lightbox.addClass("ui-lightbox ui-widget ui-helper-hidden ui-corner-all ui-shadow");
	
};

function donutChart() {
	
	this.cfg.shadow = false;
         
	this.cfg.seriesDefaults = {
        renderer: $.jqplot.DonutRenderer,
        rendererOptions: {
        	sliceMargin: 1,
        	showDataLabels: false,
        	totalLabel: true,
        	dataLabelPositionFactor: 0.5
        },
    	shadow : false
    };
    
    this.cfg.grid = {
        background: 'transparent',
        drawBorder: false,
        drawGridlines: false,
        shadow: false
    };
    
   this.cfg.legend = {
    	show: true,
    	location: 'e',
    	border: 'none',
    	fontSize: '1em',
    	showLabels: true
    };
        
}

function horizontalBarChart() {
		
	/*this.cfg.shadow = false;
	this.cfg.stackSeries = true;
    
	this.cfg.seriesDefaults = {
        renderer: $.jqplot.BarRenderer,        
        pointLabels: {
            show: true,
            location: 's'
        },
        rendererOptions: {
        	showDataLabels: true,
        	barMargin: 40,
        	barWidth: 10,
        	barDirection: 'horizontal'
        },
    	shadow: false
    };
	
	this.cfg.axesDefaults = { 
	    min: 0,
	    tickInterval: 10,
	    tickOptions: {
	    	formatString: '%d'
	    }
	};
		
    this.cfg.grid = {
        background: 'transparent',
        drawBorder: false,
        shadow: false
    };
    
    this.cfg.legend = {
    	renderer: $.jqplot.EnhancedLegendRenderer,
    	rendererOptions: {
            numberRows: 1
        },
        placement: 'outside',
    	show: true,
    	location: 's',
    	border: 'none',
    	marginTop: '40px',
    	fontSize: '1em'
    };*/
	
	this.cfg.shadow = false;
	this.cfg.stackSeries = false;
    
	this.cfg.seriesDefaults = {
        renderer: $.jqplot.BarRenderer,        
        pointLabels: {
        	/*stackedValue: false,*/
            show: true,
            hideZeros: true
        },
        rendererOptions: {
        	showDataLabels: true,
        	barMargin: 1,
        	barWidth: 10,
        	barPadding: 1,
        	barDirection: 'horizontal'
        },
    	shadow: false
    };
	
	/*this.cfg.axes = {
		yaxis: {
			autoscale: true,
			show: true,
			tickInterval: 10,
			renderer: $.jqplot.CategoryAxisRenderer,
	        tickRenderer: $.jqplot.CanvasAxisTickRenderer,
	        tickOptions: {
	        	showGridline: true,
	            angle: -30,
	            fontFamily: 'Courier New',
	            fontSize: '9pt'
	        }
        }
	};
	
	this.cfg.axesDefaults = { 
	    min: 0,
	    tickInterval: 10,
	    tickOptions: {
	    	formatString: '%d'
	    }
	};*/
		
    this.cfg.grid = {
        background: 'transparent',
        drawBorder: false,
        shadow: false
    };
    
    this.cfg.legend = {
    	renderer: $.jqplot.EnhancedLegendRenderer,
    	rendererOptions: {
            numberRows: 1
        },
        placement: 'outside',
    	show: true,
    	location: 's',
    	border: 'none',
    	marginTop: '40px',
    	fontSize: '1em'
    };
        
}