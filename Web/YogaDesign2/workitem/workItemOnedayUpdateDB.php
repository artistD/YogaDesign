<?php
    header('Content-Type:text/plain; charset=utf-8');

    $off = $_GET['off'];

    $conn = mysqli_connect("localhost", "willd88", "messid88!!", "willd88");
    mysqli_query($conn, "set names utf8");

    $sql = "UPDATE WorkItemYogaDesign SET isItemOnOff = $off WHERE isDayOrTodaySelected='[false,true]'";
    $sql2 = "UPDATE WorkItemYogaDesign SET isLogModify = false, isTimeFirst = false";

    $result = mysqli_query($conn, $sql);
    $result2 = mysqli_query($conn, $sql2);

    if($result&&$result2) echo "ㄱㅔ시글ㅣ 업로드 됬습니다";
    else echo "eee";

    mysqli_close($conn);



    
?>