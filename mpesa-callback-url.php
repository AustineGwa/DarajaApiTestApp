<?php 
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Headers: *");
header('Access-Control-Allow-Headers: Origin, X-Requested-With,X-Auth-Token, Content-Type, Accept');

/*
** Read Response  from mpesa 
*/
  $stkCallbackResponse = file_get_contents('php://input');

  //Log  the mpesa response message to a jsonfile
  // $logFile = "stkPushCallbackResponse.json";
  // $log = fopen($logFile, "a");
  // fwrite($log, $stkCallbackResponse);
  // fclose($log);


  //decode the json response into php associative array.
  // Convert to array 
$array = json_decode($stkCallbackResponse, true);



?>