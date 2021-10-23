<?php
    header('Content-Type:text/plain; charset=utf-8');

    $identifier = $_POST['identifier'];
    $workItemNo = $_POST['workItemNo'];
    $days = $_POST['days'];

    $conn = mysqli_connect("localhost", "willd88", "messid88!!", "willd88");
    mysqli_query($conn, "set names utf8");


    if($identifier=='0'){
        $sql = "INSERT INTO CalendarYogaDesign(WorkItemNo, days) VALUES($workItemNo, '$days')";
    
    }else if($identifier=='1'){
        $sql = "DELETE FROM CalendarYogaDesign WHERE days='$days' AND WorkItemNo = '$workItemNo'";
    }


    $result = mysqli_query($conn, $sql);

    if($result) echo "ㄱㅔ시글ㅣ 업로드 됬습니다";
    else echo "다시 시도";


    mysqli_close($conn);

?>