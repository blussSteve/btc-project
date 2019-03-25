$(function() {
	$("#data-search-arrow").click(function(){
		if($("#data-search-arrow").hasClass("fa-angle-down")){
			$('#data-search-panel').collapse("hide");
			$("#data-search-arrow").removeClass("fa-angle-down").addClass("fa-angle-up")
			
		}else{
			$('#data-search-panel').collapse("show");
			$("#data-search-arrow").removeClass("fa-angle-up").addClass("fa-angle-down")
			
		}
	})
		
})