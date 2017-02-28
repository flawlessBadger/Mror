function write() {
    console.log("start");
    if (window.XMLHttpRequest) {
        // code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else {
        // code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {

            document.getElementById("a").innerHTML = this.responseText;
            document.getElementById("b").innerHTML = this.responseText;
            document.getElementById("c").innerHTML = this.responseText;
            write();
        }
    };
    xmlhttp.open("GET", "loaders/mysql_load.php", true);
    xmlhttp.send();


}

function reloadPage() {
    // alert("watafack");
    // reload(true);
    window.location.href = 'http://localhost';
    // window.location.replace('http://localhost');
}

// var swoosh = new Audio("res/Swooshing.wav");
function playSound() {
    // console.log("sound");
    var sample = document.getElementById("music");
    sample.play();
}

var state = 0;
function mover() {
    console.log("starth");
    if (window.XMLHttpRequest) {
        // code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else {
        // code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var response = parseInt(this.responseText);

    console.log("movement");
            if (response == 0) {
            $('#feed').css('top','0');
            $('#weather').css('top','-2560px');

                // if (state == -1) {

                //     var m = $("#feed").offset().top + 1280;
                //     $("#feed").animate({ top: m }, 2000);
                //     m = $("#weather").offset().top + 1280;
                //     $("#weather").animate({ top: m }, 2000);
                //     state++;
                // } else if (state < -1) {
                //     var m = $("#feed").offset().top + 400;
                //     $("#feed").animate({ top: m }, 700);
                //     m = $("#weather").offset().top + 400;
                //     $("#weather").animate({ top: m }, 700);
                //     state++;
                // }

                // var m = $("#feed").offset().top + 400;
                // $("#feed").animate({ top: m }, 700);
                // console.log(m);
            } else if (response == 2) {
            $('#weather').css('top','-1280px');
            $('#feed').css('top','1280px');

                // if (state == 0) {
                //     var m = $("#feed").offset().top - 1280;
                //     $("#feed").animate({ top: m }, 2000);
                //     m = $("#weather").offset().top - 1280;
                //     $("#weather").animate({ top: m }, 2000);
                //     state--;
                // } else if (state < 0) {
                //     var m = $("#feed").offset().top - 400;
                //     $("#feed").animate({ top: m }, 700);
                //     m = $("#weather").offset().top - 400;
                //     $("#weather").animate({ top: m }, 700);
                //     state--;
                // }
            } else if(response == 3) {
    setTimeout(reloadPage, 300);

            }
            mover();
        }
    };
    xmlhttp.open("GET", "loaders/move_loader.php", true);
    xmlhttp.send();


}

function tempload() {
    console.log("temps");
    if (window.XMLHttpRequest) {
        // code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else {
        // code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {

            var n = this.responseText.indexOf("$");
            var temp = this.responseText.substring(0, n);
            var hum = this.responseText.substring(n + 1, this.responseText.length);
            document.getElementById("intemp").innerHTML = temp + '&deg;C';
            document.getElementById("inhum").innerHTML = hum + '%';


        }
    };
    xmlhttp.open("GET", "loaders/temp_loader.php", true);
    xmlhttp.send();
}

function move() {
    playSound();
    if (window.XMLHttpRequest) {
        // code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else {
        // code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            // var response = parseInt(this.responseText)
            // console.log(this.responseText);
            //     if (this.responseText == "0") {
            //   playSound();
            //         // var m = $("#mockup").offset().top() + 50;
            //         // $("#mockup").animate({ left: m.toString }, 200);
            //     setTimeout(move,2000);
            //     } else if (this.responseText == "2") {
            //          playSound();
            //         // var m = $("#mockup").offset().top() - 50;
            //         // $("#mockup").animate({ left: m.toString }, 200);
            //     setTimeout(move,2000 );
            //     }
            //     else{
            //     setTimeout(move,2000 );
            //     }
        }
    };
    xmlhttp.open("GET", "loaders/move_loader.php", true);
    xmlhttp.send();
    setTimeout(move, 3000);
}


$(document).ready(function () {
    // setTimeout(move, 3000);

    setTimeout(mover, 300);
    // setTimeout(playSound, 500);
    setTimeout(tempload, 2000);
    // $("#mockup").delay(300).animate({ left: '0' }, 1000, "easeOutCubic");
    $('.grid').masonry({
        // options
        originLeft: true,
        itemSelector: '.grid-item',
        columnWidth: 328
    });


    $.simpleWeather({
        woeid: '821782', //2357536
        location: '',
        unit: 'C',
        success: function (weather) {
            console.log(weather.temp + '&deg;' + weather.units.temp);
            var html = '<h2 id="maindeg">' + weather.temp + '&deg;' + weather.units.temp + '</h2>';
            html += '<h2 id="city">' + weather.city + '</h2>';
            html += '<h2 id="intemp"></h2><h2 id="inhum"></h2>'

            html += '<img id="house" src="res/house.png">';
            html += '<div id="weatherline"></div>    <H1 id="a" >Hello!</H1>'
            // $("#maindeg").text(weather.temp + '&deg;' + weather.units.temp);

            $("#weather").html(html);

        },
    });

    // document.getElementById("a").innerHTML = $(window).width() + "<br>" + $(window).height();
});

