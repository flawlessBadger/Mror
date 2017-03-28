        <?php
        //Feed URLs
        $link = mysql_connect('127.0.0.1', 'root', 'raspberry')
    or die('Could not connect: ' . mysql_error());
mysql_select_db('mror') or die('Could not select database');

// Performing SQL query
$query = 'SELECT link FROM feeds';
$result = mysql_query($query) or die('Query failed: ' . mysql_error());
$feeds = array();
while ($line = mysql_fetch_array($result, MYSQL_ASSOC)) {
    foreach ($line as $col_value) {
        $feeds[] = $col_value;
    }
}
        // $feeds = mysql_fetch_array($result, MYSQL_ASSOC);
        // $feeds = array(
        //     "http://maxburstein.com/rss",
        //     "http://www.engadget.com/rss.xml",
        //     "http://www.reddit.com/r/programming/.rss"
        // );
        
        //Read each feed's items
        $entries = array();
        foreach($feeds as $feed) {
            $xml = simplexml_load_file($feed);
            $entries = array_merge($entries, $xml->xpath("//item"));
        }
        
        //Sort feed entries by pubDate
        usort($entries, function ($feed1, $feed2) {
            return strtotime($feed2->pubDate) - strtotime($feed1->pubDate);
        });
        
        ?>
        
        <?php
        $breaker = 0;
        //Print all the entries
        foreach($entries as $entry){
            
            ?><div class="grid-item"><div class="line"></div>
            <h2 class="rss-title"><?= $entry->title ?></h2>
            <p><?= $entry->description ?></p></div>
            <?php
            if($breaker>30){
                break;
            }
            $breaker++;
        }
        ?>