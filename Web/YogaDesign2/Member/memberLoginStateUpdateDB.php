<?php

    header('Content-Type:text/plain; charset=utf-8');

    $myId = $_POST['myId'];
    $isLogin = $_POST['isLogin'];

    $conn = mysqli_connect("localhost", "willd88", "messid88!!", "willd88");
    mysqli_query($conn, "set names utf8");

    $sql = "UPDATE MemberYogaDesign SET isLogin = $isLogin WHERE id = '$myId'";

    $result = mysqli_query($conn, $sql);

    if($result) echo "ㄱㅔ시글ㅣ 업로드 됬습니다";
    else echo "ㄷㅏ시시시도";

    mysqli_close($conn);

?>
