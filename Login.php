<?php
	$con = mysqli_connect("localhost", "id755973_client", "abc123", "id755973_sheltersrcapp");
	
	//Creates username and password variable from the database
	$username = $_POST["username"];
	$password = $_POST["password"];
	
	$statement = mysqli_prepare($con, "SELECT * FROM user WHERE username = ? AND password = ?");
	mysqli_stmt_bind_param($statement, "ss", $username, $password);
        mysqli_stmt_execute($statement);
	
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $userID, $name, $username, $age, $password);
	
	$response = array();
	$response["success"] = false;
	
	//Gets all the data
	while(mysqli_stmt_fetch($statement)){
		$response["success"] = true;
		$response["name"] = $name;
		$response["username"] = $username;
		$response["age"] = $age;
		$response["password"] = $password;
	}
	
	//encode it all in json parameter
	echo json_encode($response);
	
?>