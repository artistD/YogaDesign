<?php
    header('Content-Type:text/plain; charset=utf-8');

    $userId = $_GET['userId'];
    
    $conn = mysqli_connect("localhost", "willd88", "messid88!!", "willd88");
    mysqli_query($conn, "set names utf8");

    
        $sql = "SELECT favoriteNum FROM MemberYogaDesign WHERE id='$userId'";
        $result = mysqli_query($conn, $sql);
        $row_num = mysqli_num_rows($result);
        $row = mysqli_fetch_array($result, MYSQLI_ASSOC);
        $favoriteNum = $row['favoriteNum'];

    
    if($result){
        echo $favoriteNum;
    }else{
        echo "다시 시도";
    }
    

    mysqli_close($conn);

?>