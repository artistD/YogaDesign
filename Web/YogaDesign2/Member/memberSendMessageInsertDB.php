<?php

    header('Content-Type:text/plain; charset=utf-8');

    $isImgKakao = $_POST['isImgKakao'];
    $id = $_POST['id'];
    $userCheckedId = $_POST['userCheckedId'];
    $myNickName = $_POST['myNickName'];
    $myMsg = $_POST['myMsg'];
    $day = $_POST['day'];



    if($isImgKakao == 'true'){
       $myKaKaoHttpStr = $_POST['myKaKaoHttpStr'];
    }else{
        $file = $_FILES['img'];
    
        $srcName = $file['name'];
        $tmpName = $file['tmp_name'];
        $size = $file['size'];
    
        $dstName= "./uploaded/" . date('YmdHis') . $srcName;
        move_uploaded_file($tmpName, $dstName);
    }


    $myNickName = addslashes($myNickName);
    $myMsg = addslashes($myMsg);

    $conn = mysqli_connect("localhost","willd88","messid88!!","willd88");
    mysqli_query($conn, "set names utf8");

    if($isImgKakao =='true'){
        $sql ="INSERT INTO ChattingYogaDesign(id, userCheckedId, myNickName, dstName, myMsg, day) VALUES('$id', '$userCheckedId', '$myNickName', '$myKaKaoHttpStr', '$myMsg' ,'$day')";
    
    }else{
        $sql ="INSERT INTO ChattingYogaDesign(id, userCheckedId, myNickName, dstName, myMsg, day) VALUES('$id', '$userCheckedId', '$myNickName', '$dstName', '$myMsg' ,'$day')";
        
    }

    $result = mysqli_query($conn, $sql);
    $sql2 = "SELECT no FROM ChattingYogaDesign WHERE id ='$id' ORDER BY no ASC";
    $result2 = mysqli_query($conn, $sql2);
    
    $row_num = mysqli_num_rows($result2);

    $rows = array();
    for($i=0; $i<$row_num; $i++){
        $row = mysqli_fetch_array($result2, MYSQLI_ASSOC);
        $rows[$i] = $row;
    }

    if($result) echo json_encode($rows);
    else echo "게시글 업로드에 실패하였습니다. \n 다시시도해주세요.";

    mysqli_close($conn);

?>