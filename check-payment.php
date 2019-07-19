<?php
//ini_set('display_errors', 1);
//error_reporting(E_ALL);

header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Headers: *");
header('Access-Control-Allow-Headers: Origin, X-Requested-With,X-Auth-Token, Content-Type, Accept');

include 'lib/class.connect.php';

$dbConnect = new Connect();

$dbConnect = $dbConnect->db();

$data = array(
		'status'	=> 400,
		'HTTPError'	=> 'Bad Request',
		'ServerMessage'=> 'Init Vars'
	);

//Check If Payment Is Made

		

		$trip_id = $_GET['trip_id'];


		$get = mysqli_query($dbConnect,"SELECT id FROM mpesa_transactions WHERE trip_id='$trip_id' AND txn_status = 'COMPLETED' ORDER BY id DESC");

		//AND DATE(timestamp) = DATE(NOW()) ORDER BY id DESC LIMIT 1

		if(mysqli_num_rows($get) == 1){

			//Payment Exists
			$data = array(
				'status'		=>	200,
				'HTTPError'		=> 'Okay',
				'ServerMessage'	=> 'Payment Confirmed!',
			);

		}else{

			$data = array(
				'status'	=> 201,
				'HTTPError'	=> 'Success',
				'ServerMessage'=> 'Payment Not found',
			);
		}


	




header("Content-type: application/json");
echo json_encode($data);
$dbConnect->close();
exit();

?>