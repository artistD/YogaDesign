<?php
    header('Content-Type:text/plain; charset=utf-8');

    $no = $_GET['no'];
    $Completenum = $_GET['Completenum'];

    $conn = mysqli_connect("localhost", "willd88", "messid88!!", "willd88");
    mysqli_query($conn, "set names utf8");

    $sql = "UPDATE WorkItemYogaDesign SET Completenum = $Completenum WHERE no='$no'";

    $result = mysqli_query($conn, $sql);

    if($result) echo "ㄱㅔ시글ㅣ 업로드 됬습니다";
    else echo "ㄷㅏ시시시도" . $no . $isChecked;

    mysqli_close($conn);




?>