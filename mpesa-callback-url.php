<?php

header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Headers: *");
header('Access-Control-Allow-Headers: Origin, X-Requested-With,X-Auth-Token, Content-Type, Accept');


$stkCallbackResponse = file_get_contents('php://input');

/*
$logFile = "logs/IPN-".time().".json";
$log = fopen($logFile, "a");
fwrite($log, $stkCallbackResponse);
fclose($log);
*/

$ipn_data = json_decode($stkCallbackResponse,true);

$new_status = 'COMPLETED';

$checkout_request_id = $ipn_data['Body']['stkCallback']['CheckoutRequestID'];

$result_code = $ipn_data['Body']['stkCallback']['ResultCode'];

$mpesa_confirmation_code = $ipn_data['Body']['stkCallback']['CallbackMetadata']['Item']['1']['Value'];

if($result_code != 0){

	$new_status = 'CANCELLED';
}


//Process Database Saving of data
include 'lib/class.connect.php';


$dbConnect = new Connect();

$dbConnect = $dbConnect->db();


//Check If signature matches


		  			
$save = mysqli_query($dbConnect,
"UPDATE
  mpesa_transactions
SET
  txn_status = '$new_status',
  mpesa_confirmation_id = '$mpesa_confirmation_code'
WHERE
  checkout_request_id = '$checkout_request_id'") or mysqli_error($dbConnect);


$dbConnect->close();
exit();
?>