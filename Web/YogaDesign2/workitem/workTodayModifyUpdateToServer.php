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
    $isItemPublic = $_POST['isItemPublic'];
    $completeNum = $_POST['completeNum'];
    $todoistBooleanState = $_POST['todoistBooleanState'];
    $isDayOrTodaySelected = $_POST['isDayOrTodaySelected'];


    $isPhotoChecekd = $_POST['isPhotoChecekd'];
    $no = $_POST['no'];
    

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


    if($isPhotoChecekd == 'true'){
        $file = $_FILES['img'];

        $srcName = $file['name'];
        $tmpName = $file['tmp_name'];
        $size = $file['size'];

        $dstName = "./uploaded/" . date('YmdHis') . $srcName;
        move_uploaded_file($tmpName, $dstName);    
    }
    
    


    $name = addslashes($name);
    $nickName  = addslashes($nickName);
    $placeName = addslashes($placeName);

    $now = date('Y-m-d H:i:s');

    $conn = mysqli_connect("localhost", "willd88", "messid88!!", "willd88");
    mysqli_query($conn, "set names utf8");

    if($isPhotoChecekd == 'true'){
        $sql = "UPDATE WorkItemYogaDesign SET name='$name', dstName='$dstName', nickName='$nickName', weeksData='$weeksDataJsonStr', isGoalChecked=$isGoalChecked, goalSet='$goalSet', isPreNotificationChecked=$isPreNotificationChecked, preNotificationTime='$preNotificationTime', isLocalNotificationChecked=$isLocalNotificationChecked, placeName='$placeName', latitude=$latitude, longitude=$longitude, isDayOrTodaySelected='$isDayOrTodaySelected', now='$now'  WHERE no='$no'";

    }else{
        $sql = "UPDATE WorkItemYogaDesign SET name='$name', nickName='$nickName', weeksData='$weeksDataJsonStr', isGoalChecked=$isGoalChecked, goalSet='$goalSet', isPreNotificationChecked=$isPreNotificationChecked, preNotificationTime='$preNotificationTime', isLocalNotificationChecked=$isLocalNotificationChecked, placeName='$placeName', latitude=$latitude, longitude=$longitude, isDayOrTodaySelected='$isDayOrTodaySelected', now='$now'  WHERE no='$no'";
    }

    $result = mysqli_query($conn, $sql);

    if($result) echo "??????????????? ????????? ????????????";
    else echo "?????? ??????";

    mysqli_close($conn);




?>