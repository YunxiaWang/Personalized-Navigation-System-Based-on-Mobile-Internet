<?php
$con=mysql_connect("localhost","root","root");  //��������
if(!$con)
  {
   die("Wrong connect:".mysql_error());
}
mysql_select_db("one",$con);  //ѡ���
mysql_query("set names gbk");//������ʾ
$judge=$_POST["judge"]; 
$id=$_POST["id"]; 
$lat=$_POST["lat"]; 
$lng=$_POST["lng"]; 
$ordertime=$_POST["ordertime"];  //��������
if($judge!=0) {   //���������ж�
//$res1 =mysql_list_tables("one");
$res=mysql_query("SHOW TABLES FROM one");  //��ʾ���б������
$i = 0;
while ($i < mysql_num_rows ($res)) {  //���ұ�
    $table[$i] = mysql_tablename ($res, $i);	
    if($table[$i]==$id){  //������id�������б�����ͬ���������ݲ���ñ�
    //echo $table[$i] . "<BR>";
	$ins0="insert into $table[$i] (lat,lng) values('$lat','$lng')";//�������
	$res0=mysql_query($ins0,$con); 
	}
	$i++;
}
    $ins1="create table $id (    
	num int(200) NOT NULL AUTO_INCREMENT, 
    PRIMARY KEY(num),
	lat varchar(200),
	lng varchar(200),
	ordertime datetime NOT NULL, 
	datatime timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP 
	)";      //������id�����������б�����ͬ�����Ը�id��Ϊ�����½�һ�������������ݲ���ñ�
	
    $res1=mysql_query($ins1,$con);
	$ins2="insert into $id (lat,lng,ordertime) values('$lat','$lng','$ordertime')";  //�������
	$res2=mysql_query($ins2,$con);
	if($res2!=0) {echo "1";}
	else {echo "0";}
}
else { die("Require failed!");}   //������ʾ
mysql_close($con);
?>
