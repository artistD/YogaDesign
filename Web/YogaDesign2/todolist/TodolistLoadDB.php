<?php

    header('Content-Type:application/json; charset=utf-8');

    $id = $_POST['id'];

    $conn = mysqli_connect('localhost', 'willd88', 'messid88!!', 'willd88');

    mysqli_query($conn, "set names utf8");


    $a = '1';
    $sql = "SELECT * FROM WorkItemYogaDesign WHERE id='$id' and isItemOnOff='$a' ORDER BY no ASC";
    $result = mysqli_query($conn, $sql);

    $row_num = mysqli_num_rows($result);

    $rows = array();
    for($i=0; $i<$row_num; $i++){
        $row = mysqli_fetch_array($result, MYSQLI_ASSOC);
        $rows[$i] = $row;
    }

    echo json_encode($rows);

    mysqli_close($conn);

?>