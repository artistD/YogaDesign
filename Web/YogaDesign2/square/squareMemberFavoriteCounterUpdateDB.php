<?php
    header('Content-Type:text/plain; charset=utf-8');

    $favoriteCheckedId = $_GET['favoriteCheckedId'];
    $isFavorite = $_GET['isFavorite'];
    $favoriteNum=0;
    $favoriteCheckedUserList = $_GET['favoriteCheckedUserList'];


    $conn = mysqli_connect("localhost", "willd88", "messid88!!", "willd88");
    mysqli_query($conn, "set names utf8");

    if($isFavorite=='true'){
        $sql = "SELECT favoriteNum FROM MemberYogaDesign WHERE id='$favoriteCheckedId'";
        $result = mysqli_query($conn, $sql);
        $row_num = mysqli_num_rows($result);
        $row = mysqli_fetch_array($result, MYSQLI_ASSOC);
        $favoriteNum = ($row['favoriteNum'] + 1);
        $sql2 = "UPDATE MemberYogaDesign SET favoriteNum ='$favoriteNum', favoriteCheckedUserList = '$favoriteCheckedUserList' WHERE id='$favoriteCheckedId'";
        $result2 = mysqli_query($conn, $sql2);
    }else{
        $sql = "SELECT favoriteNum FROM MemberYogaDesign WHERE id='$favoriteCheckedId'";
        $result = mysqli_query($conn, $sql);
        $row_num = mysqli_num_rows($result);
        $row = mysqli_fetch_array($result, MYSQLI_ASSOC);
        $favoriteNum = ($row['favoriteNum'] - 1);
        $sql2 = "UPDATE MemberYogaDesign SET favoriteNum ='$favoriteNum', favoriteCheckedUserList = '$favoriteCheckedUserList' WHERE id='$favoriteCheckedId'";
        $result2 = mysqli_query($conn, $sql2);

    }

    if($result2){
        echo $favoriteNum;
    }else{
        echo "다시 시도";
    }
    

    mysqli_close($conn);

?>