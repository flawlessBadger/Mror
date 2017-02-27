<?php
// Connecting, selecting database
$link = mysql_connect('127.0.0.1', 'root', 'raspberry')
    or die('Could not connect: ' . mysql_error());
mysql_select_db('mror') or die('Could not select database');

// Performing SQL query
$query = 'SELECT * FROM Text';
$result = mysql_query($query) or die('Query failed: ' . mysql_error());

// Printing results in HTML
while ($line = mysql_fetch_array($result, MYSQL_ASSOC)) {
    foreach ($line as $col_value) {
        echo $col_value;
    }
}

// Free resultset
mysql_free_result($result);

// Closing connection
mysql_close($link);