<?php
    header('Content-Type:text/plain; charset=utf-8');

    $id = $_POST['id']; 
    
    $name = $_POST['name'];
    $nickName = $_POST['nickName'];
    $weeksDataJsonStr = $_POST['weeksDataJsonStr'];
    $isGoalChecked = $_POST['isGoalChecked'];
    $goalSet = $_POST['goalSet'];
    $isPreNotificationChecked = $_POST['isPreNotificationChecked'];
    $preNotificationTime = $_POST['preNotificationTime'];
    $isLocalNotificationChecked = $_POST['isLocalNotificationChecked'];
    $placeName = $_POST['placeName'];
    $latitude = $_POST['latitude'];
    $longitude = $_POST['longitude'];
    $isItemOnOff = $_POST['isItemOnOff'];
    $isItemPrivate = $_POST['isItemPrivate'];
    $completeNum = $_POST['completeNum'];
    $todoistBooleanState = $_POST['todoistBooleanState'];
    $isDayOrTodaySelected = $_POST['isDayOrTodaySelected'];

    $isLogModify = $_POST['isLogModify'];
    $isTimeFirst = $_POST['isTimeFirst'];
    $timeSum = $_POST['timeSum'];
    

    // $id = "dqw";
    // $name = "dqwdwq";
    // $nickName = "dwq";
    // $weeksDataJsonStr ="dqw";
    // $isGoalChecked = "true"; 
    // $goalSet = "dqdwq";
    // $isPreNotificationChecked = "true";
    // $preNotificationTime = "dqwqdw";
    // $isLocalNotificationChecked = "true";
    // $placeName = "dqwqw";
    // $latitude = "1";
    // $longitude = "1";
    // $isItemOnOff = "true";



    $file = $_FILES['img'];

    $srcName = $file['name'];
    $tmpName = $file['tmp_name'];
    $size = $file['size'];

    $dstName = "./uploaded/" . date('YmdHis') . $srcName;
    move_uploaded_file($tmpName, $dstName);

    $name = addslashes($name);
    $nickName  = addslashes($nickName);
    $placeName = addslashes($placeName);

    $now = date('Y-m-d H:i:s');

    $conn = mysqli_connect("localhost", "willd88", "messid88!!", "willd88");
    mysqli_query($conn, "set names utf8");

    $sql = "INSERT INTO WorkItemYogaDesign(id, indexNo, name, dstName, nickName, weeksData, isGoalChecked, goalSet, isPreNotificationChecked, preNotificationTime, isLocalNotificationChecked, placeName, latitude, longitude, isItemOnOff, isItemPrivate, todoistBooleanState, Completenum, isDayOrTodaySelected, isLogModify, isTimeFirst, timeSum, now) VALUES('$id', 0, '$name', '$dstName', '$nickName', '$weeksDataJsonStr', $isGoalChecked, '$goalSet', $isPreNotificationChecked, '$preNotificationTime', $isLocalNotificationChecked, '$placeName', '$latitude', '$longitude', $isItemOnOff, $isItemPrivate, '$todoistBooleanState', '$completeNum', '$isDayOrTodaySelected', $isLogModify, $isTimeFirst, '$timeSum' , '$now')";
    $result = mysqli_query($conn, $sql);

    if($result) echo "ㄱㅔ시글ㅣ 업로드 됬습니다";
    else echo "다시 시도";

    mysqli_close($conn);




?>