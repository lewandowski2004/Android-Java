$(document).ready(function(){
	$("#settings form").submit(saveSettings);
	$("#settings").bind("pagebeforshow", loadSettings);
});

	function saveSettings(){
	localStorage.limit=$("#limit").val();
	history.back();
	return false;
	}
	
	function loadSettings(){
	if (!localStorage.limit)
	{ localStorage.limit="";}
		{ $("#limit").val(localStorage.limit).slider("refresh");}
}
