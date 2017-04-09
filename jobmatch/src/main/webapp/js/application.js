!function (jQuery ){
	var $ = jQuery;	
	
	//chamado toda vez que � feito um reRender
	//ideal para colocar mascaras e componentes do jquery
	$.reRender = function(){
		
		//aplica o tooltip
		$('body').tooltip({
			selector: "a[rel=tooltip]"
	    });
		
	    //impede a submissao de formulario pela tecla enter
	    $('form').bind('keypress', function(e) {
	    	if ((!$(this).hasClass('allowEnter'))&&(e.keyCode == 13)) {
	    		return false;
	    	}
	    });
	    
	    
	    $('input.disablePaste').keydown(function(event) {
	        var forbiddenKeys = new Array('v');
	        var keyCode = (event.keyCode) ? event.keyCode : event.which;
	        var isCtrl;
	        isCtrl = event.ctrlKey;
	        if (isCtrl) {
	            for (var i = 0; i < forbiddenKeys.length; i++) {
	                if (forbiddenKeys[i] == String.fromCharCode(keyCode).toLowerCase()) {
	                     return false;
	                }
	            }
	        }
	        return true;
	    });
	    
	    $(".search-menu").focus(function () {
            $(this).animate({           
                width: '150px'
                },
               "fast"
            );
        });
	    	    
	    $(".search-menu").focusout(function () {
            $(this).animate({           
                width: '100px'
                },
               "fast"
            );
        });
	    
	    //carrega as mascaras
	    $('input.telefone').mask('99-9999-9999', {placeholder: "__-____-____"});
	    $('input.cpf').mask('999.999.999-99', {placeholder: "___.___.___-__"});
	    $('input.cnpj').mask('99.999.999/9999-99', {placeholder: "__.___.___/____-__"});
	    $('input.cep').mask('99999-999', {placeholder: "_____-___"});
	    $('input.data').mask('99/99/9999', {placeholder: "__/__/____"});
	    $('input.mesano').mask('99/9999', {placeholder: "__/____"});
	    $('input.hora').mask('99:99', {placeholder: "__:__"});
	    $('input.protocolo').mask('00000000/0000', {placeholder: "________/____",reverse: true});
	    $('input.iptu').mask('99.99.999.9999.999-9', {placeholder: "__.__.___.____.___-_"});
	    
	    $('input.numero').numeric();
	    $('input.numeroFormatado').numeric({allow:"./-"});
	    $('input.letra').alpha();
	    $('input.alfanumerico').alphanumeric({allow:".,/- "});
	    $('input.email').alphanumeric({allow:"._-@"});
	    
	    $('input.moeda').priceFormat({prefix: '',centsSeparator: ',',thousandsSeparator: '.',allowNegative: true}); 
	    
	    $('#subnav').affix('refresh');
	};
  
	$(function(){
		
		
	  
		$(document).ready(function() {			
		    // To Top Button
			
			$.reRender();
			$('.focus').focus();
			
			
		});		
	});
}(window.jQuery);