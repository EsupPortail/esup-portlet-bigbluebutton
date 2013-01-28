	  (function ($) {
	
		  $('a#help').hover(function(e) {
		    $(this).next('span.help').css('display','inline');
		  });
		  
		  $('a#help').mouseout(function(e) {
			    $(this).next('span.help').css('display','none');
			  });
		  
		  $('#date_picker').datetimepicker({ dateFormat: 'dd-mm-yy' });
	            
	   })(jQuery);