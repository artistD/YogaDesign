<?php
    header('Content-Type:application/json; charset=utf-8');

    $conn = mysqli_connect('localhost', 'willd88', 'messid88!!', 'willd88');

    mysqli_query($conn, "set names utf8");

    //원하는 쿼리문 작성 (market테이블의 모든칸[column]의 모든 데이터들(레코드들)을 가져오기)
    $sql = "SELECT * FROM MemberYogaDesign";
    $result = mysqli_query($conn, $sql);//쿼리문의 맞는 결과표를 리턴

    //결과표로부터 총 레코드(row:줄) 수
    $row_num = mysqli_num_rows($result);

    //여로줄을 읽어야 하므로 각 줄 ($row 배열)을 요소로 가질 빈 인덱스 배열 준비
    $rows= array();
    for($i=0; $i<$row_num; $i++){
        $row=mysqli_fetch_array($result, MYSQLI_ASSOC);//한줄데이터를 연관배열로 가져오기
        $rows[$i] = $row;
    }

    //연관배열을 --> json객체 문자열로 변환
    //$rows는 2차원 배열임. --> json array 문자열로 변환

    echo json_encode($rows);

    mysqli_close($conn); //DB 닫기

?>