<?php 
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Headers: *");
header('Access-Control-Allow-Headers: Origin, X-Requested-With,X-Auth-Token, Content-Type, Accept');


  $stkCallbackResponse = file_get_contents('php://input');
  $logFile = "stkPushCallbackResponse.json";
  $log = fopen($logFile, "a");
  fwrite($log, $stkCallbackResponse);
  fclose($log);


// $callbackData = $mpesa->getDataFromCallback();
// $my_file = 'response.txt';
// $handle = fopen($my_file, 'w') or die('Cannot open file:  '.$my_file);
// fwrite($handle, $callbackData);
// If validation fails, pass false to finishTransaction()
// $mpesa= new \Safaricom\Mpesa\Mpesa();
// $callbackData=$mpesa->finishTransaction(false
// $mpesa= new \Safaricom\Mpesa\Mpesa();
// If validation fails, pass false to finishTransaction()
// $mpesa= new \Safaricom\Mpesa\Mpesa();
// $callbackData=$mpesa->finishTransaction(false)
?>