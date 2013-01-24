jQuery(document).ready(function($){
  var moveLeft = 20;
  var moveDown = 10;

  $('a#help').hover(function(e) {
    $(this).next('span.help').css('display','inline').fadeOut(1000);
      //.css('top', e.pageY + moveDown)
      //.css('left', e.pageX + moveLeft)
      //.appendTo('body');
 /* }, function() {
    $('div#pop-up').hide();*/
  });

 /* $('a#help').mousemove(function(e) {
    $("div#pop-up").css('top', e.pageY + moveDown).css('left', e.pageX + moveLeft);
  });*/

});

/*
$('a#help').hover(function () {
$(this).next("span.help").css('display','inline').fadeOut(1000);
});*/
