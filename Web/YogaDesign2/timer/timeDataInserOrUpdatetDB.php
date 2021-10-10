<?php
    header('Content-Type:text/plain; charset=utf-8');

    $identifier = $_GET['identifier'];
    $workItemNo = $_GET['workItemNo'];
    $days = $_GET['days'];
    $time = $_GET['time'];
    $isTimeFirst = $_GET['isTimeFirst'];


    $conn = mysqli_connect("localhost", "willd88", "messid88!!", "willd88");
    mysqli_query($conn, "set names utf8");


    if($identifier=='0'){
        $sql = "INSERT INTO LogDataYogaDesign(WorkItemNo, days, time) VALUES($workItemNo, '$days', '$time')";
        $sql2 = "UPDATE WorkItemYogaDesign SET isTimeFirst = $isTimeFirst WHERE no='$workItemNo'";
    
    }else if($identifier=='1'){
        $sql = "UPDATE LogDataYogaDesign SET WorkItemNo = $workItemNo, days='$days', time='$time' WHERE days='$days' AND WorkItemNo = '$workItemNo'";
        $sql2 = "UPDATE WorkItemYogaDesign SET isTimeFirst = $isTimeFirst WHERE no='$workItemNo'";
    }


    $result = mysqli_query($conn, $sql);
    $result2 = mysqli_query($conn, $sql2);

    
    if($result && $result2) echo "ㄱㅔ시글ㅣ 업로드 됬습니다";
    else echo "다시 시도";

    mysqli_close($conn);

?>