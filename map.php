<?php
	//this connects to the local xampp local database
	$servername = "localhost";
	$dbuser = "id755973_client";
	$dbpassword = "abc123";
	$databaseName = "id755973_sheltersrcapp";
	
	//Make connection to database
	$conn = mysqli_connect($servername, $dbuser, $dbpassword, $databaseName);

        //data from android studios
	$provider_id = $_POST["providerId"];
	
	//make query to draw from info from database
	$sql = mysqli_prepare($conn, "SELECT * FROM Provider WHERE provider_id = ?");
    mysqli_stmt_bind_param($sql, "i", $provider_id);
	mysqli_stmt_execute($sql);
	mysqli_stmt_store_result($sql);
	
	//need to store every value in the sql query
	mysqli_stmt_bind_result($sql, $provider_id, $provider_name, $provider_address, $housing_number, $housing, $food, $clothing, $code);

	//Create array to store information from the bind_results
	$response = array();
	$response["success"] = false;
	
	//this grabs the data to send back to android studios
	while(mysqli_stmt_fetch($sql)){
			$response["success"] = true;
			$response["provider_name"] = $provider_name;
			$response["provider_address"] = $provider_address;
			$response["housing_number"] = $housing_number;
			$response["housing"] = $housing;
			$response["food"] = $food;
			$response["clothing"] = $clothing;
	}
	
	echo json_encode($response);
	
?>