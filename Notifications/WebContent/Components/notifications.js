
$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

//on click listner for the save button
$(document).on("click", "#btnSave", function(event) {

	// 1 - clear the alert boxes (success and error)
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();

	$("#alertError").text("");
	$("#alertError").hide();

	// then we need to call the validate method
	var status = validateItemform();
	
	console.log(status);

	// if not properly validated
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// if valid.....submit the form
	$("#formItem").submit();

});

//implementing the update button handler
$(document).on(
		"click",
		".notification .btnUpdate",
		function(event) {

			console.log("inside update event handler");
				
			$("#hidItemIDSave").val(
					$(this).closest("div").find('#hidItemIDUpdate').val());
		
			$("#ntopic").val(
					$(this).closest("div").find('.card-body .ntopic').text());
			$("#ncontent").val(
					$(this).closest("div").find('.card-body .ncontent').text());

		});


//********************************************************
function validateItemform() {
	if ($("#ntopic").val().trim() == "") {
		return "Insert Notification topic.";
	}
	if ($("#ncontent").val().trim() == "") {
		return "Insert Project Description.";
	}

	return true;
}
