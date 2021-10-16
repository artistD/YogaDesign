<?php

    header('Content-Type:text/plain; charset=utf-8');

    
    $id = $_POST['id'];
    $userCheckedId = $_POST['userCheckedId'];
    $myNickName = $_POST['myNickName'];
    $myMsg = $_POST['myMsg'];
    $day = $_POST['day'];




    $file = $_FILES['img'];
    
    $srcName = $file['name'];
    $tmpName = $file['tmp_name'];
    $size = $file['size'];

    $dstName= "./uploaded/" . date('YmdHis') . $srcName;
    move_uploaded_file($tmpName, $dstName);

    $myNickName = addslashes($myNickName);
    $myMsg = addslashes($myMsg);

    $conn = mysqli_connect("localhost","willd88","messid88!!","willd88");
    mysqli_query($conn, "set names utf8");

    $sql ="INSERT INTO ChattingYogaDesign(id, userCheckedId, myNickName, dstName, myMsg, day) VALUES('$id', '$userCheckedId', '$myNickName', '$dstName', '$myMsg' ,'$day')";
    $result = mysqli_query($conn, $sql);

    if($result) echo "게시글 업로드 되었습니다.";
    else echo "게시글 업로드에 실패하였습니다. \n 다시시도해주세요.";

    mysqli_close($conn);

?>