<?php
    header('Content-Type:text/plain; charset=utf-8');

    $workItemIndetJsonStr = $_GET['workItemIndetJsonStr'];
    $workItemIndexSize = $_GET['workItemIndexSize'];

    $arr = json_decode($workItemIndetJsonStr);

    $conn = mysqli_connect("localhost", "willd88", "messid88!!", "willd88");
    mysqli_query($conn, "set names utf8");


    $sql = array();
    for($i=0; $i<$workItemIndexSize; $i++){
        $sql[$i] = "UPDATE WorkItemYogaDesign SET indexNo = '$i' WHERE no='$arr[$i]'";
    }


    $result = array();
    for($i=0; $i<$workItemIndexSize; $i++){
        $result[$i] = mysqli_query($conn, $sql[$i]);
    }

    if($result) echo "ㄱㅔ시글ㅣ 업로드 됬습니다";
    else echo "ㄷㅏ시시시도";

    mysqli_close($conn);




?>