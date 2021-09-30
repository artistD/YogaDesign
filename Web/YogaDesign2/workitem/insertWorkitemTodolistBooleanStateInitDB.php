<?php

    header('Content-Type:text/plain; charset=utf-8');

    $todoistBooleanStateInit = $_GET['todoistBooleanStateInit'];

    $conn = mysqli_connect("localhost", "willd88", "messid88!!", "willd88");
    mysqli_query($conn, "set names utf8");

    $sql = "UPDATE WorkItemYogaDesign SET todoistBooleanState = '$todoistBooleanStateInit', isLogModify = false";

    $result = mysqli_query($conn, $sql);

    if($result) echo "ㄱㅔ시글ㅣ 업로드 됬습니다";
    else echo "ㄷㅏ시시시도";

    mysqli_close($conn);

?>