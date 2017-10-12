-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 27, 2017 at 12:45 PM
-- Server version: 5.7.14
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `shamman`
--

-- --------------------------------------------------------

--
-- Table structure for table `hosts`
--

CREATE TABLE `hosts` (
  `ID` bigint(5) NOT NULL COMMENT 'ID',
  `UUID` longtext CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `addDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `LastTimeOnline` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OS` text NOT NULL,
  `hostName` longtext CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
  `CPU` float NOT NULL,
  `RAM` int(2) NOT NULL,
  `IP` longtext NOT NULL,
  `myJobs_0` text NOT NULL,
  `myJobs_1` text NOT NULL,
  `myJobs_2` text NOT NULL,
  `errorLevel` int(2) NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hosts`
--

INSERT INTO `hosts` (`ID`, `UUID`, `addDate`, `LastTimeOnline`, `OS`, `hostName`, `CPU`, `RAM`, `IP`, `myJobs_0`, `myJobs_1`, `myJobs_2`, `errorLevel`) VALUES
(1, '', '2017-01-23 15:30:08', '2017-03-13 16:28:32', '', 'WIN', 1, 12, '192.168.122.123', 'u/test/test/', '', '', 0),
(2, '', '2017-01-23 15:32:59', '2017-03-13 16:28:32', '', 'Linux', 1, 12, '252.13.45.432', '', '', '', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `hosts`
--
ALTER TABLE `hosts`
  ADD UNIQUE KEY `ID_2` (`ID`),
  ADD KEY `ID` (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `hosts`
--
ALTER TABLE `hosts`
  MODIFY `ID` bigint(5) NOT NULL AUTO_INCREMENT COMMENT 'ID', AUTO_INCREMENT=5;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
