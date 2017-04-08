<?php

    $con = mysqli_connect("localhost", "id755973_client", "abc123", "id755973_sheltersrcapp");
	
	//Creates username and password variable from the database
	$code = $_POST["code"];
	
	$statement = mysqli_prepare($con, "SELECT * FROM authentication WHERE code = ?");
	mysqli_stmt_bind_param($statement, "ss", $code);
    mysqli_stmt_execute($statement);
	
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $authenID, $providerID, $code);
	
	$response = array();
	$response["success"] = false;
	
	//Gets all the data
	while(mysqli_stmt_fetch($statement)){
		$response["success"] = true;
		$response["authentication_id"] = $authenID;
		$response["provider_id"] = $providerID;
		$response["code"] = $code;
	}
	
	//encode it all in json parameter
	echo json_encode($response);
?>