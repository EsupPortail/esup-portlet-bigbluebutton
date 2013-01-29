/* French initialisation for the jQuery UI date picker plugin. */
/* Written by Keith Wood (kbwood{at}iinet.com.au),
St�phane Nahmani (sholby@sholby.net),
St�phane Raimbault <stephane.raimbault@gmail.com> */
jQuery(function($){
$.datepicker.regional['fr'] = {
closeText: 'Fermer',
prevText: 'Pr�c�dent',
nextText: 'Suivant',
currentText: 'Aujourd\'hui',
monthNames: ['Janvier','F�vrier','Mars','Avril','Mai','Juin',
'Juillet','Ao�t','Septembre','Octobre','Novembre','D�cembre'],
monthNamesShort: ['Janv.','F�vr.','Mars','Avril','Mai','Juin',
'Juil.','Ao�t','Sept.','Oct.','Nov.','D�c.'],
dayNames: ['Dimanche','Lundi','Mardi','Mercredi','Jeudi','Vendredi','Samedi'],
dayNamesShort: ['Dim.','Lun.','Mar.','Mer.','Jeu.','Ven.','Sam.'],
dayNamesMin: ['D','L','M','M','J','V','S'],
weekHeader: 'Sem.',
dateFormat: 'dd/mm/yy',
firstDay: 1,
isRTL: false,
showMonthAfterYear: false,
yearSuffix: ''};
$.datepicker.setDefaults($.datepicker.regional['fr']);
});