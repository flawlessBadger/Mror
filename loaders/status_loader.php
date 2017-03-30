<?php
// Connecting, selecting database
$link = mysql_connect('127.0.0.1', 'root', 'raspberry')
or die('Could not connect: ' . mysql_error());
mysql_select_db('mror') or die('Could not select database');

// Performing SQL query
$query = 'SELECT name, status FROM modules ORDER  BY name DESC';
$result = mysql_query($query) or die('Query failed: ' . mysql_error());

// Printing results in HTML
while ($line = mysql_fetch_array($result, MYSQL_ASSOC)) {
	$b = true;
	foreach ($line as $col_value) {
		echo $col_value;
		if($b){
			echo '$';
			$b=false;
		} else {
			$b=true;
		}
		
	}
	echo '#';
}

// Free resultset
mysql_free_result($result);

// Closing connection
mysql_close($link);