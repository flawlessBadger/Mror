-- phpMyAdmin SQL Dump
-- version 4.2.12deb2+deb8u2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 01, 2017 at 09:23 PM
-- Server version: 5.5.54-0+deb8u1
-- PHP Version: 5.6.29-0+deb8u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `mror`
--

-- --------------------------------------------------------

--
-- Table structure for table `errors`
--

CREATE TABLE IF NOT EXISTS `errors` (
`ID` int(11) NOT NULL,
  `error` varchar(140) DEFAULT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `feeds`
--

CREATE TABLE IF NOT EXISTS `feeds` (
`ID` int(11) NOT NULL,
  `link` varchar(100) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `feeds`
--

INSERT INTO `feeds` (`ID`, `link`) VALUES
(11, 'http://www.zive.sk/rss/mobilmania/'),
(5, 'http://www.zive.sk/rss/najnovsie/'),
(4, 'https://www.theguardian.com/uk-news/rss');

-- --------------------------------------------------------

--
-- Table structure for table `indoor_weather`
--

CREATE TABLE IF NOT EXISTS `indoor_weather` (
`ID` int(11) NOT NULL,
  `temp` int(11) NOT NULL,
  `hum` int(11) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `indoor_weather`
--

INSERT INTO `indoor_weather` (`ID`, `temp`, `hum`, `timestamp`) VALUES
(1, 19, 51, '2017-05-01 19:21:33');

-- --------------------------------------------------------

--
-- Table structure for table `message_buffer`
--

CREATE TABLE IF NOT EXISTS `message_buffer` (
`ID` int(11) NOT NULL,
  `ID_sender` int(11) NOT NULL,
  `priority` smallint(6) NOT NULL DEFAULT '3',
  `message` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `modules`
--

CREATE TABLE IF NOT EXISTS `modules` (
`ID` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `mac` char(17) NOT NULL,
  `is_looped` tinyint(1) DEFAULT '1',
  `last_comm` timestamp NULL DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `modules`
--

INSERT INTO `modules` (`ID`, `name`, `mac`, `is_looped`, `last_comm`, `status`) VALUES
(1, 'Smoke detector', '98:D3:31:FC:1A:E9', 2, '2017-04-27 06:12:03', 1),
(2, 'Alarm', '98:D3:31:B3:7C:13', 1, '2017-04-27 11:38:24', 1),
(3, 'Light sensor', '98:D3:31:FB:21:5E', 2, '2017-04-26 22:18:27', 1),
(4, 'Blinds', '98:D3:31:FD:1F:52', 2, '2017-04-27 11:22:34', 1),
(5, 'Socket', '98:D3:31:FC:32:0A', 2, '2017-04-27 11:31:57', 1),
(6, 'Rain sensor', '98:D3:31:FD:1B:26', 2, '2017-04-26 22:19:01', 1);

-- --------------------------------------------------------

--
-- Table structure for table `move`
--

CREATE TABLE IF NOT EXISTS `move` (
  `move` tinyint(4) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `move`
--

INSERT INTO `move` (`move`) VALUES
(1);

-- --------------------------------------------------------

--
-- Table structure for table `Sessions`
--

CREATE TABLE IF NOT EXISTS `Sessions` (
`ID` int(11) NOT NULL,
  `Session` varchar(255) DEFAULT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `errors`
--
ALTER TABLE `errors`
 ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `feeds`
--
ALTER TABLE `feeds`
 ADD PRIMARY KEY (`ID`), ADD UNIQUE KEY `link` (`link`);

--
-- Indexes for table `indoor_weather`
--
ALTER TABLE `indoor_weather`
 ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `message_buffer`
--
ALTER TABLE `message_buffer`
 ADD PRIMARY KEY (`ID`), ADD KEY `message_buffer_fk0` (`ID_sender`);

--
-- Indexes for table `modules`
--
ALTER TABLE `modules`
 ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `Sessions`
--
ALTER TABLE `Sessions`
 ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `errors`
--
ALTER TABLE `errors`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `feeds`
--
ALTER TABLE `feeds`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `indoor_weather`
--
ALTER TABLE `indoor_weather`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `message_buffer`
--
ALTER TABLE `message_buffer`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `modules`
--
ALTER TABLE `modules`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `Sessions`
--
ALTER TABLE `Sessions`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=119;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
