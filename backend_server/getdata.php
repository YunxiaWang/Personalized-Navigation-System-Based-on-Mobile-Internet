<?php
$con=mysql_connect("localhost","root","root");  //建立连接
if(!$con)
  {
   die("Wrong connect:".mysql_error());
}
mysql_select_db("one",$con);  //选择库
mysql_query("set names gbk");//中文显示
$judge=$_GET["judge"];    //参数传递
$id=$_GET["id"]; 
//$lat=$_GET["lat"]; 
//$lng=$_GET["lng"]; 
//$ordertime=$_GET["ordertime"];  
if($judge!=0) {   //接收请求判断,judge不为0则执行
if($id!=null){  //id不为0则查询表名与id相同的表中的最后一行   
$sel2="select * from $id a order by a.num desc limit 0,1";  //选择要查询的id行
$result2=mysql_query($sel2,$con);  //选择结果判断
$arr=array();   //建立数组输出json格式数据
$row=mysql_fetch_array($result2); //输出显示
$arr['num']=$row[0];
$arr['lat']=$row[1];
$arr['lng']=$row[2];
//$arr['ordertime']=$row[3];
//$arr['datatime']=$row[4];
echo json_encode($arr);
}
else{   //id为0则查询所有表中的最后一行
$res=mysql_query("SHOW TABLES FROM one");  //显示库中表的名称
$i = 0;
while ($i < mysql_num_rows ($res)) {  //查找表
    $table[$i] = mysql_tablename ($res, $i);
	//echo $table[$i] . "<BR>";	
	$ins0="select * from $table[$i] a order by a.num desc limit 0,1";  //选择要查询的id行
	$res0=mysql_query($ins0,$con); 
	$arr=array();    //建立数组输出json格式数据
//$j=mysql_num_rows($res);
//for($i=0;$i<$j;$i++){
$row=mysql_fetch_array($res0); //输出显示
$arr['num']=$row[0];
$arr['lat']=$row[1];
$arr['lng']=$row[2];
//$arr['ordertime']=$row[3];
//$arr['datatime']=$row[4];
echo json_encode($arr) . "<BR>";
//}
	$i++;
	}
}
}
else {   //报错提示
if(!$id){
 die("Require failed! ".mysql_error());
}
}
mysql_close($con);
?>