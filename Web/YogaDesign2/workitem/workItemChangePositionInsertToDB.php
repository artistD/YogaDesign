<?php
    header('Content-Type:text/plain; charset=utf-8');


    $fromNo = $_GET['fromNo'];
    $fromSortationNo = $_GET['fromSortationNo'];
    $toNo = $_GET['toNo'];
    $toSortationNo = $_GET['toSortationNo'];

    $conn = mysqli_connect("localhost", "willd88", "messid88!!", "willd88");
    mysqli_query($conn, "set names utf8");

    $sql = "UPDATE WorkItemYogaDesign SET sortationNo = $toSortationNo WHERE no=$fromNo";
    $sql2 = "UPDATE WorkItemYogaDesign SET sortationNo = $fromSortationNo WHERE no=$toNo";

    $result = mysqli_query($conn, $sql);
    $result2 = mysqli_query($conn, $sql2);

    if($result&&$result2) echo "ㄱㅔ시글ㅣ 업로드 됬습니다";
    else echo "ㄷㅏ시시시도";

    mysqli_close($conn);

?>