var db;

$(document).ready(function(){
	var openRequest = indexedDB.open("Wydatki",1);
	openRequest.onupgradeneeded = function(event){
		var thisDB = event.target.result;
		if(!thisDB.objectStoreNames.contains("wpisy")) {
		var objectStores = thisDB.createObjectStore("wpisy", {keyPath: "id", autoIncrement: true});
		objectStores.createIndex("data","data", {unique:false});
		}
	}
	openRequest.onsuccess = function(event){
		db = event.target.result;
		console.log("Sukces");
	}
	openRequest.onerror = function(event){
		console.log("Error ",event.target.error.name);
	}	
	
	$("#settings form").submit(saveSettings);
	$("#settings").bind("pagebeforeshow", loadSettings);
	$("#new form").submit(addEntries);

	function addEntries(){
	var wpis = {
	data:$("#dat_zak").val(),
	zakup:$("#zakup").val(),
	kwota:$("#kwota").val(),
	}
	var transaction = db.transaction(["wpisy"], "readwrite");
	var store = transaction.objectStore("wpisy");
	var request = store.add(wpis);
	history.back();
	$("#dat_zak").value = '';
	$("#kwota").val('');
	$("#zakup").val('');
	return false;
	}
});