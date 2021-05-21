/**
 * 
 */

$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

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
		".btnUpdate",
		function(event) {

			// getting the hidden column value of which the clicked update
			// button exist
			$("#hidItemIDSave").val($(this).data("mailId"));
			// loading the data to the form again
			$("#to").val($(this).closest("tr").find('td:eq(0)').text());
			$("#subject").val($(this).closest("tr").find('td:eq(1)').text());
			$("#message").val($(this).closest("tr").find('td:eq(2)').text());

		});


function validateItemform() {

	if ($("#to").val().trim() == "") {
		return "Insert Receiptionist mail Address.";
	}

	if ($("#subject").val().trim() == "") {
		return "Insert Subject.";
	}

	if ($("#message").val().trim() == "") {
		return "Insert Message Body.";
	}

	return true;
}
