<?php
    $dbuser = "id755973_client";
    $password = "abc123";
    $databaseName = "id755973_sheltersrcapp";

    $connect = mysqli_connect("localhost", $dbuser, $password, $databaseName);

    $name = $_POST["name"];
    $lname = $_POST["lname"];
    $email = $_POST["email"];
    $age = $_POST["age"];
    $username = $_POST["username"];
    $password = $_POST["password"];
    $password2 = $_POST["password2"];


    function checkAge($a){
       if($a > 12 && $a < 106){
          return true;
       } else{
          return false;
       }
    }

    function checkPassword($pw1, $pw2){
       if(($pw1==$pw2) && (strlen($pw1)>6)){
          return true;
       } else{
          return false;
       }
    }
 
    function checkUser($username){
       if(preg_match('/^\w{5,20}$/',$username)){
          return true;
       }else{
          return false;
       }
    }

    function registerUser() {
        global $connect, $name, $age, $username, $password, $lname, $email;
        $passwordHash = password_hash($password, PASSWORD_BCRYPT);
        $statement = mysqli_prepare($connect, "INSERT INTO user (name, lname, username, age, password, email) VALUES (?, ?, ?, ?,?,?)");
        mysqli_stmt_bind_param($statement, "sssiss", $name, $lname, $username, $age, $passwordHash, $email);
        mysqli_stmt_execute($statement);
        mysqli_stmt_close($statement);     
    }
    function usernameAvailable() {
        global $connect, $username;
        $statement = mysqli_prepare($connect, "SELECT * FROM user WHERE username = ?"); 
        mysqli_stmt_bind_param($statement, "s", $username);
        mysqli_stmt_execute($statement);
        mysqli_stmt_store_result($statement);
        $count = mysqli_stmt_num_rows($statement);
        mysqli_stmt_close($statement); 
        if ($count < 1){
            return true; 
        }else {
            return false; 
        }
    }
    $response = array();
    $response["success"] = false;  

    if (usernameAvailable() && 
        checkUser($username) && 
        filter_var($email, FILTER_VALIDATE_EMAIL) && 
        checkPassword($password, $password2) &&
        checkAge($age)){
        registerUser();
        $response["success"] = true;  
    } 

    echo json_encode($response);

?>