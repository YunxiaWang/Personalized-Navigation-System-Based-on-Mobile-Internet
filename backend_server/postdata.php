<?php
$con=mysql_connect("localhost","root","root");  //建立连接
if(!$con)
  {
   die("Wrong connect:".mysql_error());
}
mysql_select_db("one",$con);  //选择库
mysql_query("set names gbk");//中文显示
$judge=$_POST["judge"]; 
$id=$_POST["id"]; 
$lat=$_POST["lat"]; 
$lng=$_POST["lng"]; 
$ordertime=$_POST["ordertime"];  //参数传递
if($judge!=0) {   //接收请求判断
//$res1 =mysql_list_tables("one");
$res=mysql_query("SHOW TABLES FROM one");  //显示库中表的名称
$i = 0;
while ($i < mysql_num_rows ($res)) {  //查找表
    $table[$i] = mysql_tablename ($res, $i);	
    if($table[$i]==$id){  //当输入id名与现有表名相同，则将新数据插入该表
    //echo $table[$i] . "<BR>";
	$ins0="insert into $table[$i] (lat,lng) values('$lat','$lng')";//插入表中
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
	)";      //当输入id名与现有所有表名不同，则以该id名为表名新建一个表，并将新数据插入该表
	
    $res1=mysql_query($ins1,$con);
	$ins2="insert into $id (lat,lng,ordertime) values('$lat','$lng','$ordertime')";  //插入表中
	$res2=mysql_query($ins2,$con);
	if($res2!=0) {echo "1";}
	else {echo "0";}
}
else { die("Require failed!");}   //报错提示
mysql_close($con);
?>
