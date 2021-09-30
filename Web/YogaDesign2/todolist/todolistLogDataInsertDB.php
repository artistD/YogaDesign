<?php
    header('Content-Type:text/plain; charset=utf-8');

    $identifier = $_POST['identifier'];
    $workItemNo = $_POST['workItemNo'];
    $days = $_POST['days'];
    $log = $_POST['log'];

    $isLogModify = $_POST['isLogModify'];

    $log = addslashes($log);

    $conn = mysqli_connect("localhost", "willd88", "messid88!!", "willd88");
    mysqli_query($conn, "set names utf8");


    if($identifier=='0'){
        $sql = "INSERT INTO LogDataYogaDesign(WorkItemNo, days, log) VALUES($workItemNo, '$days', '$log')";
        $sql2 = "UPDATE WorkItemYogaDesign SET isLogModify = $isLogModify WHERE no='$workItemNo'";

        $result = mysqli_query($conn, $sql);
        $result2 = mysqli_query($conn, $sql2);

        
    if($result && $result2) echo "ㄱㅔ시글ㅣ 업로드 됬습니다";
    else echo "다시 시도" . $workItemNo . " : " . $days . " : " . $log . " : " . $isLogModify;
    
    }else if($identifier=='1'){
        $sql = "UPDATE LogDataYogaDesign SET WorkItemNo = $workItemNo, days='$days', log='$log' WHERE days='$days' AND WorkItemNo = '$workItemNo'";

        $result = mysqli_query($conn, $sql);

        
    if($result) echo "ㄱㅔ시글ㅣ 업로드 됬습니다";
    else echo "다시 시도" . $workItemNo . " : " . $days . " : " . $log . " : " . $isLogModify;
    }

    mysqli_close($conn);

?>