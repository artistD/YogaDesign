<?php

    header('Content-Type:text/plain; charset=utf-8');

    
    $isPhotoChecked = $_POST['isPhotoChecked'];
    $id =$_POST['id'];
    $nickName = $_POST['nickName'];
    $stateMsg = $_POST['stateMsg'];
    $isLogin = $_POST['isLogin'];
    $isUserPublic = $_POST['isUserPublic'];
    $favoriteNum = $_POST['favoriteNum'];
    $favoriteCheckedUserList = $_POST['favoriteCheckedUserList'];

    if($isPhotoChecked == 'true'){

        $file = $_FILES['img'];
    
        $srcName = $file['name'];
        $tmpName = $file['tmp_name'];
        $size = $file['size'];
    
        $dstName= "./uploaded/" . date('YmdHis') . $srcName;
        move_uploaded_file($tmpName, $dstName);

    }else{
        $myKaKaoHttpStr = $_POST['myKaKaoHttpStr'];
    }

    $nickName = addslashes($nickName);
    $stateMsg = addslashes($stateMsg);

    $now = date('Y-m-d');

    $conn = mysqli_connect("localhost","willd88","messid88!!","willd88");
    mysqli_query($conn, "set names utf8");

    if($isPhotoChecked == 'true'){
        $sql ="INSERT INTO MemberYogaDesign(id, name, frofile, stateMsg, isLogin, isUserPublic, favoriteNum, favoriteCheckedUserList, date) VALUES('$id', '$nickName', '$dstName', '$stateMsg', $isLogin, $isUerPublic, $favoriteNum, '$favoriteCheckedUserList', '$now')";
        $result = mysqli_query($conn, $sql);

    }else{
        $sql ="INSERT INTO MemberYogaDesign(id, name, frofile, stateMsg, isLogin, isUserPublic, favoriteNum, favoriteCheckedUserList, date) VALUES('$id', '$nickName', '$myKaKaoHttpStr', '$stateMsg', $isLogin, $isUerPublic, $favoriteNum, '$favoriteCheckedUserList', '$now')";
        $result = mysqli_query($conn, $sql);
    }


    if($result) echo "게시글 업로드 되었습니다.";
    else echo "게시글 업로드에 실패하였습니다. \n 다시시도해주세요.";

    mysqli_close($conn);

?>