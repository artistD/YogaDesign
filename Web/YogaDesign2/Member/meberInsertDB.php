<?php

    header('Content-Type:text/plain; charset=utf-8');

    
    $id = $_POST['id'];
    $name = $_POST['name'];
    $favoriteNum = $_POST['favoriteNum'];
    $isUerPublic = $_POST['isUserPublic'];



    $file = $_FILES['img'];
    
    $srcName = $file['name'];
    $tmpName = $file['tmp_name'];
    $size = $file['size'];

    $dstName= "./uploaded/" . date('YmdHis') . $srcName;
    move_uploaded_file($tmpName, $dstName);

    $id = addslashes($id);
    $name = addslashes($name);

    $now = date('Y-m-d');

    $conn = mysqli_connect("localhost","willd88","messid88!!","willd88");
    mysqli_query($conn, "set names utf8");

    $sql ="INSERT INTO MemberYogaDesign(id, name, isUserPublic, frofile, favoriteNum, date) VALUES('$id', '$name', $isUerPublic, '$dstName', '$favoriteNum' ,'$now')";
    $result = mysqli_query($conn, $sql);

    if($result) echo "게시글 업로드 되었습니다.";
    else echo "게시글 업로드에 실패하였습니다. \n 다시시도해주세요.";

    mysqli_close($conn);

?>