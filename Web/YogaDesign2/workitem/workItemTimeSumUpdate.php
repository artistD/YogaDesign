<?php
    header('Content-Type:text/plain; charset=utf-8');

    $no = $_GET['no'];
    $timeSum = $_GET['timeSum'];

    $conn = mysqli_connect("localhost", "willd88", "messid88!!", "willd88");
    mysqli_query($conn, "set names utf8");

    $sql = "UPDATE WorkItemYogaDesign SET timeSum = '$timeSum' WHERE no='$no'";

    $result = mysqli_query($conn, $sql);

    if($result) echo "ㄱㅔ시글ㅣ 업로드 됬습니다";
    else echo "eee";

    mysqli_close($conn);




?>