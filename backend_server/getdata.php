<?php
$con=mysql_connect("localhost","root","root");  //��������
if(!$con)
  {
   die("Wrong connect:".mysql_error());
}
mysql_select_db("one",$con);  //ѡ���
mysql_query("set names gbk");//������ʾ
$judge=$_GET["judge"];    //��������
$id=$_GET["id"]; 
//$lat=$_GET["lat"]; 
//$lng=$_GET["lng"]; 
//$ordertime=$_GET["ordertime"];  
if($judge!=0) {   //���������ж�,judge��Ϊ0��ִ��
if($id!=null){  //id��Ϊ0���ѯ������id��ͬ�ı��е����һ��   
$sel2="select * from $id a order by a.num desc limit 0,1";  //ѡ��Ҫ��ѯ��id��
$result2=mysql_query($sel2,$con);  //ѡ�����ж�
$arr=array();   //�����������json��ʽ����
$row=mysql_fetch_array($result2); //�����ʾ
$arr['num']=$row[0];
$arr['lat']=$row[1];
$arr['lng']=$row[2];
//$arr['ordertime']=$row[3];
//$arr['datatime']=$row[4];
echo json_encode($arr);
}
else{   //idΪ0���ѯ���б��е����һ��
$res=mysql_query("SHOW TABLES FROM one");  //��ʾ���б������
$i = 0;
while ($i < mysql_num_rows ($res)) {  //���ұ�
    $table[$i] = mysql_tablename ($res, $i);
	//echo $table[$i] . "<BR>";	
	$ins0="select * from $table[$i] a order by a.num desc limit 0,1";  //ѡ��Ҫ��ѯ��id��
	$res0=mysql_query($ins0,$con); 
	$arr=array();    //�����������json��ʽ����
//$j=mysql_num_rows($res);
//for($i=0;$i<$j;$i++){
$row=mysql_fetch_array($res0); //�����ʾ
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
else {   //������ʾ
if(!$id){
 die("Require failed! ".mysql_error());
}
}
mysql_close($con);
?>