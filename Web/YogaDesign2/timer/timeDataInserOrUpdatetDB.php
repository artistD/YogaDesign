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
        $sql3 = "SELECT * FROM WorkItemYogaDesign WHERE no='$workItemNo'";
    
    }else if($identifier=='1'){
        $sql = "UPDATE LogDataYogaDesign SET WorkItemNo = $workItemNo, days='$days', time='$time' WHERE days='$days' AND WorkItemNo = '$workItemNo'";
        $sql2 = "UPDATE WorkItemYogaDesign SET isTimeFirst = $isTimeFirst WHERE no='$workItemNo'";
        $sql3 = "SELECT * FROM WorkItemYogaDesign WHERE no='$workItemNo'";
    }


    $result = mysqli_query($conn, $sql);
    $result2 = mysqli_query($conn, $sql2);
    $result3 = mysqli_query($conn, $sql3);

    $row_num = mysqli_num_rows($result3);

    $rows = array();
    for($i=0; $i<$row_num; $i++){
        $row = mysqli_fetch_array($result3, MYSQLI_ASSOC);
        $rows[$i] = $row;
    }
    
    echo json_encode($rows);

    mysqli_close($conn);

?>