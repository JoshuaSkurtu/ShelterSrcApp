<?php

    $dbuser = "id755973_client";
    $password = "abc123";
    $databaseName = "id755973_sheltersrcapp";
	
	
    $connect = mysqli_connect("localhost", $dbuser, $password, $databaseName);
    
    $username = $_POST["username"];
    $password = $_POST["password"];
    
    $statement = mysqli_prepare($connect, "SELECT * FROM user WHERE username = ?");
    mysqli_stmt_bind_param($statement, "s", $username);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    //need to store every single value in the SQL query
    mysqli_stmt_bind_result($statement, $colUserID, $colName, $colLName, $colUsername, $colAge, $colPassword, $colEmail);
    
    $response = array();
    $response["success"] = false;  
    
    //this just needs the information that it is requested by the android in Login
    while(mysqli_stmt_fetch($statement)){
        if (password_verify($password, $colPassword)) {
            $response["success"] = true;  
            $response["name"] = $colName;
            $response["username"] = $colUsername;
            $response["age"] = $colAge;

        }
    }
    echo json_encode($response);
	
?>