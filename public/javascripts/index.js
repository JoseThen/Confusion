 $("#login-button").click(function(event){
		event.preventDefault();
	
	 $('form').fadeOut(500);
	 $('.wrapper').addClass('form-success');
	 
	 setTimeout(function(){ document.getElementById("hope").submit(); }, 2000);
});

function sortName($i){
    if ($i == "1"){
        var s = '/sort/name/asc';
    }else{
        var s = '/sort/name/desc';
    }
    window.open (s,'_self',false);
}


function sortPrice($i){
    if ($i == "1"){
        var s = '/sort/price/asc';
    }else{
        var s = '/sort/price/desc';
    }
    window.open (s,'_self',false);
}