<?php
    header('Content-Type:text/plain; charset=utf-8');



    $id = $_POST['id'];
    $name = $_POST['name'];
    $userStateMsg = $_POST['userStateMsg'];
    $isPhotoChecked = $_POST['isPhotoChecked'];


    if($isPhotoChecked=='true'){
        $file = $_FILES['img'];

        $srcName = $file['name'];
        $tmpName = $file['tmp_name'];
        $size = $file['size'];

        $dstName = "./uploaded/" . date('YmdHis') . $srcName;
        move_uploaded_file($tmpName, $dstName);    
    }
    
    


    $name = addslashes($name);
    $userStateMsg = addslashes($userStateMsg);

    $now = date('Y-m-d H:i:s');

    $conn = mysqli_connect("localhost", "willd88", "messid88!!", "willd88");
    mysqli_query($conn, "set names utf8");

    if($isPhotoChecked=='true'){
        $sql = "UPDATE MemberYogaDesign SET name = '$name', frofile='$dstName', stateMsg='$userStateMsg', date='$now' WHERE id='$id'";
        $sql2 = "UPDATE ChattingYogaDesign SET myNickName = '$name', dstName='$dstName' WHERE id='$id'";

    }else{
        $sql = "UPDATE MemberYogaDesign SET name = '$name', stateMsg='$userStateMsg', date='$now' WHERE id='$id'";
        $sql2 = "UPDATE ChattingYogaDesign SET myNickName = '$name' WHERE id='$id'";
    }

    $result = mysqli_query($conn, $sql);
    $result2 = mysqli_query($conn, $sql2);

    if($result) echo "게시글이 업로드됬습니다";
    else echo "다시 시도";

    mysqli_close($conn);

?>