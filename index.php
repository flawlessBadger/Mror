<!DOCTYPE html PUBLIC "-//IETF//DTD HTML 2.0//EN">
<HTML>

    <HEAD>
        <link href="https://fonts.googleapis.com/css?family=Quicksand" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Raleway:300,400,500,700" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Oxygen:300,400,700&amp;subset=latin-ext" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="styles/style.css">
        <link rel="stylesheet" type="text/css" href="styles/icons.css">
        <script src="scripts/jquery.min.js"></script>
        <script src="scripts/jquery-ui.min.js"></script>
        <script src="scripts/jquery.simpleWeather.min.js"></script>
        <script src="scripts/masonry.js"></script>
        <script src="scripts/script.js"></script>


    </HEAD>

    <BODY>
        <div class="vignette-top">
        </div>
        <div class="vignette-bottom">
        </div>
        <audio id="music" src="res/Swooshing.wav" preload="auto"></audio>
        <div id="weather">

        </div>
        <div id="feed">
            <div class="grid">
                <?php   include_once "loaders/rss_loader.php";    ?>
            </div>
        </div>
    </BODY>

</HTML>