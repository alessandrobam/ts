-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.2.15-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for table dpe_abbott_pb_2020.actions
CREATE TABLE IF NOT EXISTS `actions` (
  `ID` decimal(5,0) DEFAULT NULL,
  `MASTERID` decimal(5,0) DEFAULT NULL,
  `TASKID` decimal(5,0) DEFAULT NULL,
  `NAME` varchar(200) DEFAULT NULL,
  `DEADLINE` datetime DEFAULT NULL,
  `STATUS` decimal(1,0) DEFAULT NULL,
  `NEXT` decimal(1,0) DEFAULT NULL,
  `PRIORITY` decimal(1,0) DEFAULT NULL,
  `CREATED` datetime DEFAULT NULL,
  `FINISHED` datetime DEFAULT NULL,
  `LASTRESCHEDULE` datetime DEFAULT NULL,
  `GOLDEN` decimal(1,0) DEFAULT NULL,
  `actionscol` varchar(45) DEFAULT NULL,
  KEY `Actions_pk` (`MASTERID`,`TASKID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table dpe_abbott_pb_2020.actions: ~62 rows (approximately)
/*!40000 ALTER TABLE `actions` DISABLE KEYS */;
INSERT INTO `actions` (`ID`, `MASTERID`, `TASKID`, `NAME`, `DEADLINE`, `STATUS`, `NEXT`, `PRIORITY`, `CREATED`, `FINISHED`, `LASTRESCHEDULE`, `GOLDEN`, `actionscol`) VALUES
	(2, 2, 1, 'Weekly - Cost Variance Report', '2020-03-18 00:00:00', 1, 0, 2, '2020-03-11 00:00:00', NULL, '2020-03-11 00:00:00', 1, NULL),
	(3, 2, 2, 'Weekly - PBReports Refresh', '2020-03-18 00:00:00', 1, 0, 2, '2020-03-11 00:00:00', NULL, '2020-03-11 00:00:00', 1, NULL),
	(4, 2, 6, 'When?', '2020-03-11 00:00:00', 3, 0, 2, '2020-03-11 00:00:00', '2020-03-11 00:00:00', '2020-03-11 00:00:00', 0, NULL),
	(5, 5, 1, 'Submit Forecast for the Quarter', '2020-03-11 00:00:00', 3, 1, 2, '2020-03-11 00:00:00', '2020-03-11 00:00:00', '2020-03-11 00:00:00', 2, NULL),
	(6, 2, 12, 'Weekly - Publish Cost Allocation Report [1, 5d]', '2020-03-16 00:00:00', 1, 0, 2, '2020-03-11 00:00:00', NULL, '2020-03-13 00:00:00', 0, NULL),
	(7, 5, 1, 'Enviar Arquivo da Promocao do Rene', '2020-03-11 00:00:00', 3, 1, 2, '2020-03-11 00:00:00', '2020-03-11 00:00:00', '2020-03-11 00:00:00', 2, NULL),
	(8, 3, 3, 'w - REP00001 - Cost Allocation Report', '2020-03-11 00:00:00', 1, 0, 2, '2020-03-11 00:00:00', NULL, '2020-03-11 00:00:00', 3, NULL),
	(9, 7, 1, 'a - Analize the what PBReports can provide to this report.', '2020-03-11 00:00:00', 1, 0, 2, '2020-03-11 00:00:00', NULL, '2020-03-11 00:00:00', 1, NULL),
	(10, 7, 1, 'MEETING - Project closure report template', '2020-03-11 00:00:00', 1, 0, 2, '2020-03-11 00:00:00', NULL, '2020-03-11 00:00:00', 1, NULL),
	(11, 2, 12, 'Feel sagars format with planner data', '2020-03-11 00:00:00', 3, 1, 2, '2020-03-11 00:00:00', '2020-03-11 00:00:00', '2020-03-11 00:00:00', 1, NULL),
	(12, 5, 1, 'f - Flavio, any updates on Rene?', '2020-03-12 00:00:00', 3, 0, 2, '2020-03-11 00:00:00', '2020-03-12 00:00:00', '2020-03-11 00:00:00', 1, NULL),
	(13, 2, 6, 'f - Sagar, do we have an updated file?', '2020-03-12 00:00:00', 1, 0, 2, '2020-03-11 00:00:00', NULL, '2020-03-11 00:00:00', 1, NULL),
	(14, 3, 4, 'w - Who the top Planner user?', '2020-03-11 00:00:00', 3, 0, 2, '2020-03-11 00:00:00', '2020-03-11 00:00:00', '2020-03-11 00:00:00', 1, NULL),
	(15, 8, 3, 'MEETING - Org Change Annoucement - PJ', '2020-03-11 00:00:00', 3, 0, 2, '2020-03-11 00:00:00', '2020-03-11 00:00:00', '2020-03-11 00:00:00', 2, NULL),
	(16, 8, 4, 'MEETING - Org Change Annoucement - Enda', '2020-03-12 00:00:00', 3, 1, 2, '2020-03-11 00:00:00', '2020-03-13 00:00:00', '2020-03-11 00:00:00', 2, NULL),
	(17, 1, 12, 'Where is my access?', '2020-04-22 00:00:00', 1, 0, 2, '2020-03-11 00:00:00', NULL, '2020-03-11 00:00:00', 2, NULL),
	(18, 2, 13, 'Find Mukts PD EWO Tracker on Box', '2020-03-11 00:00:00', 1, 0, 2, '2020-03-11 00:00:00', NULL, '2020-03-11 00:00:00', 0, NULL),
	(19, 2, 13, 'MEETING - With Mutki', '2020-03-11 00:00:00', 1, 0, 2, '2020-03-11 00:00:00', NULL, '2020-03-11 00:00:00', 0, NULL),
	(20, 6, 4, 'w - Air tickets bought', '2020-03-11 00:00:00', 1, 0, 2, '2020-03-11 00:00:00', NULL, '2020-03-11 00:00:00', 2, NULL),
	(21, 6, 3, 'w - HCAM documentation complete', '2020-03-11 00:00:00', 1, 0, 2, '2020-03-11 00:00:00', NULL, '2020-03-11 00:00:00', 2, NULL),
	(22, 6, 4, 'a - Call Flytour and book the flight for me and my wife', '2020-03-12 00:00:00', 3, 1, 2, '2020-03-11 00:00:00', '2020-03-12 00:00:00', '2020-03-11 00:00:00', 2, NULL),
	(23, 2, 10, 'a - Save Email from Vinay as an example', '2020-03-12 00:00:00', 1, 0, 2, '2020-03-11 00:00:00', NULL, '2020-03-11 00:00:00', 0, NULL),
	(24, 5, 1, 'Responder ao Email do ICA do Rene', '2020-03-12 00:00:00', 1, 0, 2, '2020-03-11 00:00:00', NULL, '2020-03-11 00:00:00', 0, NULL),
	(25, 10, 1, 'e - FEA00001 - Leveled Access', '2020-03-12 00:00:00', 3, 0, 2, '2020-03-12 00:00:00', '2020-03-12 00:00:00', '2020-03-12 00:00:00', 3, NULL),
	(26, 5, 1, 'r - ICAs for Google and Abbott', '2020-03-12 00:00:00', 1, 1, 2, '2020-03-12 00:00:00', NULL, '2020-03-12 00:00:00', 1, NULL),
	(27, 11, 1, 'Rocha trabalha de WeWork na Argentina?', '2020-03-12 00:00:00', 1, 0, 2, '2020-03-12 00:00:00', NULL, '2020-03-12 00:00:00', 0, NULL),
	(28, 5, 1, 'Share Brazil contract actuals', '2020-03-12 00:00:00', 3, 0, 2, '2020-03-12 00:00:00', '2020-03-12 00:00:00', '2020-03-12 00:00:00', 2, NULL),
	(29, 5, 1, 'Call Bernardo and understand Renes Situation', '2020-03-12 00:00:00', 3, 1, 2, '2020-03-12 00:00:00', '2020-03-13 00:00:00', '2020-03-12 00:00:00', 2, NULL),
	(30, 5, 1, 'Agendar passagem de Bastam com o Time', '2020-03-12 00:00:00', 3, 1, 2, '2020-03-12 00:00:00', '2020-03-13 00:00:00', '2020-03-12 00:00:00', 2, NULL),
	(32, 2, 13, 'MEETING - Review P/B opportunity pipeline and align on key solutioning directions', '2020-03-19 00:00:00', 1, 0, 2, '2020-03-12 00:00:00', NULL, '2020-03-12 00:00:00', 1, NULL),
	(33, 2, 13, 'd - Have a solid backlog grooming process in place', '2020-03-11 00:00:00', 1, 0, 2, '2020-03-12 00:00:00', NULL, '2020-03-12 00:00:00', 3, NULL),
	(34, 3, 5, 'w - REP00002 - Project Going Live in the next 4 weeks', '2020-03-12 00:00:00', 1, 0, 2, '2020-03-12 00:00:00', NULL, '2020-03-12 00:00:00', 3, NULL),
	(35, 5, 1, 'Share Brazil contract Actuals [1, -3d]', '2020-03-16 00:00:00', 1, 0, 2, '2020-03-12 00:00:00', NULL, '2020-03-13 00:00:00', 2, NULL),
	(36, 5, 1, 'Move hours to projects', '2020-03-19 00:00:00', 3, 0, 2, '2020-03-12 00:00:00', '2020-03-13 00:00:00', '2020-03-12 00:00:00', 2, NULL),
	(37, 2, 15, 'Weekly - PB Weekly Internal Status Report - Review', '2020-03-16 00:00:00', 1, 0, 2, '2020-03-12 00:00:00', NULL, '2020-03-12 00:00:00', 1, NULL),
	(38, 11, 1, 'r - Respond to Satyava', '2020-03-12 00:00:00', 3, 0, 2, '2020-03-12 00:00:00', '2020-03-12 00:00:00', '2020-03-12 00:00:00', 2, NULL),
	(39, 11, 1, 'w - BCP Test for Latin America', '2020-03-12 00:00:00', 1, 0, 2, '2020-03-12 00:00:00', NULL, '2020-03-12 00:00:00', 1, NULL),
	(40, 9, 2, 'a - Nimai needs to send estimates to Myriam', '2020-03-12 00:00:00', 1, 0, 2, '2020-03-12 00:00:00', NULL, '2020-03-12 00:00:00', 2, NULL),
	(41, 6, 4, 'f - Flight confirmed?', '2020-03-13 00:00:00', 3, 0, 2, '2020-03-12 00:00:00', '2020-03-13 00:00:00', '2020-03-12 00:00:00', 2, NULL),
	(42, 6, 6, 'a - Please provide the requested details ASAP', '2020-03-12 00:00:00', 1, 0, 2, '2020-03-12 00:00:00', NULL, '2020-03-12 00:00:00', 2, NULL),
	(43, 2, 12, 'a - Address Tapan remarks on Planner Extract', '2020-03-11 00:00:00', 3, 0, 2, '2020-03-13 00:00:00', '2020-03-13 00:00:00', '2020-03-13 00:00:00', 1, NULL),
	(44, 2, 1, 'Go thru Actuals Variance inputs from the team', '2020-03-13 00:00:00', 1, 0, 2, '2020-03-13 00:00:00', NULL, '2020-03-13 00:00:00', 1, NULL),
	(45, 3, 6, 'e - BUG00001 - Resource Name is coming from from Actuals', '2020-03-13 00:00:00', 1, 0, 2, '2020-03-13 00:00:00', NULL, '2020-03-13 00:00:00', 3, NULL),
	(46, 3, 7, 'e - FEA00002 - Work item to PD mapping to address incorrect tittle', '2020-03-13 00:00:00', 1, 0, 2, '2020-03-13 00:00:00', NULL, '2020-03-13 00:00:00', 3, NULL),
	(47, 2, 3, 'deliverable - Document the Process in a High Quality Deck', '2020-03-11 00:00:00', 1, 0, 2, '2020-03-13 00:00:00', NULL, '2020-03-13 00:00:00', 3, NULL),
	(48, 15, 1, 'r - Harish - Needs Ann to be 15 hours a week in PB projects', '2020-03-13 00:00:00', 1, 0, 2, '2020-03-13 00:00:00', NULL, '2020-03-13 00:00:00', 2, NULL),
	(50, 3, 8, 'w - REP00002 - Planner Info Extract', '2020-03-13 00:00:00', 3, 0, 2, '2020-03-13 00:00:00', '2020-03-13 00:00:00', '2020-03-13 00:00:00', 3, NULL),
	(51, 8, 14, 'd - Read and Undersand the SOP base for the SLC BTSQ5', '2020-03-12 00:00:00', 1, 0, 2, '2020-03-13 00:00:00', NULL, '2020-03-13 00:00:00', 3, NULL),
	(52, 2, 8, 'verify - Did PD-004187 Feb invoice was issued?', '2020-03-13 00:00:00', 3, 1, 2, '2020-03-13 00:00:00', '2020-03-13 00:00:00', '2020-03-13 00:00:00', 2, NULL),
	(53, 12, 2, 'd - New Reports and Cadences in Place', '2020-03-13 00:00:00', 1, 0, 2, '2020-03-13 00:00:00', NULL, '2020-03-13 00:00:00', 3, NULL),
	(54, 2, 16, 'w - Create a stakeholder map on the workbook', '2020-03-13 00:00:00', 1, 0, 2, '2020-03-13 00:00:00', NULL, '2020-03-13 00:00:00', 3, NULL),
	(55, 2, 12, 'w - Establish the Automated Process for Distributing the Planner Data', '2020-03-16 00:00:00', 1, 0, 2, '2020-03-13 00:00:00', NULL, '2020-03-13 00:00:00', 3, NULL),
	(56, 5, 1, 'Monthly Assessments', '2020-03-13 00:00:00', 1, 1, 2, '2020-03-13 00:00:00', NULL, '2020-03-13 00:00:00', 2, NULL),
	(57, 3, 8, 'e - Add the Billing Schedule Tab on it', '2020-03-14 00:00:00', 1, 1, 2, '2020-03-13 00:00:00', NULL, '2020-03-13 00:00:00', 2, NULL),
	(58, 9, 3, 'MEETING - MLD - IBM PB and KTLO Sync up ', '2020-03-13 00:00:00', 3, 0, 2, '2020-03-13 00:00:00', '2020-03-13 00:00:00', '2020-03-13 00:00:00', 0, NULL),
	(59, 5, 1, 'Distribuir meus bluepoints', '2020-03-13 00:00:00', 3, 0, 2, '2020-03-13 00:00:00', '2020-03-13 00:00:00', '2020-03-13 00:00:00', 0, NULL),
	(60, 2, 8, 'Update PD-004187 on the tracker', '2020-03-14 00:00:00', 1, 0, 2, '2020-03-13 00:00:00', NULL, '2020-03-13 00:00:00', 1, NULL),
	(61, 5, 1, 'MEETING - Mudanca Organizacional', '2020-03-16 00:00:00', 1, 1, 2, '2020-03-13 00:00:00', NULL, '2020-03-13 00:00:00', 2, NULL),
	(62, 8, 14, 'MEETING - IBM team questions re RFT reports', '2020-03-19 00:00:00', 1, 1, 2, '2020-03-13 00:00:00', NULL, '2020-03-13 00:00:00', 2, NULL),
	(63, 10, 3, 'e - Add a Go-To-Market Category', '2020-03-14 00:00:00', 1, 0, 2, '2020-03-13 00:00:00', NULL, '2020-03-13 00:00:00', 3, NULL),
	(64, 16, 1, 'add slide after IBM MSP for Japan EDI', '2020-03-14 00:00:00', 1, 0, 2, '2020-03-13 00:00:00', NULL, '2020-03-13 00:00:00', 3, NULL),
	(65, 2, 12, 'a - Respond to Nimais', '2020-03-14 00:00:00', 3, 0, 2, '2020-03-14 00:00:00', '2020-03-14 00:00:00', '2020-03-14 00:00:00', 1, NULL);
/*!40000 ALTER TABLE `actions` ENABLE KEYS */;

-- Dumping structure for table dpe_abbott_pb_2020.cats
CREATE TABLE IF NOT EXISTS `cats` (
  `ID` decimal(3,0) DEFAULT NULL,
  `CATNAME` varchar(100) DEFAULT NULL,
  `PATH` varchar(300) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table dpe_abbott_pb_2020.cats: ~7 rows (approximately)
/*!40000 ALTER TABLE `cats` DISABLE KEYS */;
INSERT INTO `cats` (`ID`, `CATNAME`, `PATH`) VALUES
	(1, 'Client Support', 'C:\\AlessandroBAM\\2020m03 - Abbott PB DPE\\Client Support\\'),
	(2, 'Staffing', 'C:\\AlessandroBAM\\2020m03 - Abbott PB DPE\\Staffing\\'),
	(3, 'Governance', 'C:\\AlessandroBAM\\2020m03 - Abbott PB DPE\\Governance'),
	(4, 'Others', 'C:\\AlessandroBAM\\2020m03 - Abbott PB DPE\\Others'),
	(5, 'Innovation', 'C:\\AlessandroBAM\\2020m03 - Abbott PB DPE\\Innovation'),
	(6, 'Personal', 'C:\\AlessandroBAM\\2020m03 - Abbott PB DPE\\Others\\Personal'),
	(7, 'Compliance', 'C:\\AlessandroBAM\\2020m03 - Abbott PB DPE\\Compliance'),
	(8, 'HR', 'C:\\AlessandroBAM\\2020m03 - Abbott PB DPE\\IBM Brazil\\RH');
/*!40000 ALTER TABLE `cats` ENABLE KEYS */;

-- Dumping structure for table dpe_abbott_pb_2020.mastertasks
CREATE TABLE IF NOT EXISTS `mastertasks` (
  `ID` decimal(5,0) DEFAULT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `CATEGORY` varchar(100) DEFAULT NULL,
  `COUNTDOWN` decimal(4,0) DEFAULT NULL,
  `PERCCOMPLETE` decimal(5,2) DEFAULT NULL,
  `MINUTES` decimal(10,0) DEFAULT NULL,
  `REFERENCE` varchar(300) DEFAULT NULL,
  `WORKCOUNT` decimal(3,0) DEFAULT NULL,
  `WAITCOUNT` decimal(3,0) DEFAULT NULL,
  `DONECOUNT` decimal(3,0) DEFAULT NULL,
  `OPENCOUNT` decimal(3,0) DEFAULT NULL,
  `NEXTTASK` datetime DEFAULT NULL,
  `LASTUPDATE` datetime DEFAULT NULL,
  `CREATED` datetime DEFAULT NULL,
  `role_id` decimal(5,0) DEFAULT NULL,
  KEY `idx_mastertasks_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table dpe_abbott_pb_2020.mastertasks: ~16 rows (approximately)
/*!40000 ALTER TABLE `mastertasks` DISABLE KEYS */;
INSERT INTO `mastertasks` (`ID`, `NAME`, `CATEGORY`, `COUNTDOWN`, `PERCCOMPLETE`, `MINUTES`, `REFERENCE`, `WORKCOUNT`, `WAITCOUNT`, `DONECOUNT`, `OPENCOUNT`, `NEXTTASK`, `LASTUPDATE`, `CREATED`, `role_id`) VALUES
	(1, 'Lauching Pad', 'Others', NULL, NULL, 0, NULL, 0, 0, 0, 0, NULL, '2020-03-11 05:24:00', '2020-03-11 05:24:00', 1),
	(2, 'KPE - Key Process Execution', 'Governance', NULL, NULL, 0, NULL, 0, 0, 0, 0, NULL, '2020-03-11 05:26:00', '2020-03-11 05:26:00', 2),
	(3, 'PBReports', 'Innovation', NULL, NULL, 0, NULL, 0, 0, 0, 0, NULL, '2020-03-11 05:29:00', '2020-03-11 05:29:00', 3),
	(4, 'Data Capturing', 'Governance', NULL, NULL, 0, NULL, 0, 0, 0, 0, NULL, '2020-03-11 05:31:00', '2020-03-11 05:31:00', 4),
	(5, '2020m03 - Transition', 'Others', NULL, NULL, 0, NULL, 0, 0, 0, 0, NULL, '2020-03-11 06:11:00', '2020-03-11 06:11:00', 5),
	(6, 'HCAM', 'HR', NULL, NULL, 0, NULL, 0, 0, 0, 0, NULL, '2020-03-11 09:43:00', '2020-03-11 09:43:00', 6),
	(7, '2020m03 - Process Improvement', 'Innovation', NULL, NULL, 0, NULL, 0, 0, 0, 0, NULL, '2020-03-11 11:39:00', '2020-03-11 11:39:00', 3),
	(8, 'Note Taking', 'Others', NULL, NULL, 0, NULL, 1, 0, 0, 0, NULL, '2020-03-11 14:22:00', '2020-03-11 14:21:00', 1),
	(9, 'Watch List', 'Client Support', NULL, NULL, 0, NULL, 0, 0, 0, 0, NULL, '2020-03-11 15:12:00', '2020-03-11 15:12:00', 9),
	(10, 'Timesheet', 'Innovation', NULL, NULL, 0, NULL, 0, 0, 0, 0, NULL, '2020-03-12 05:41:00', '2020-03-12 05:41:00', 3),
	(11, 'BCP - Business Continuity Plan', 'Compliance', NULL, NULL, 0, NULL, 0, 0, 0, 0, NULL, '2020-03-12 08:44:00', '2020-03-12 08:43:00', 1),
	(12, 'Key Goals', 'Compliance', NULL, NULL, 0, NULL, 0, 0, 0, 0, NULL, '2020-03-12 12:49:00', '2020-03-12 12:49:00', 1),
	(13, 'Data Security & Privacy - DSP', 'Compliance', NULL, NULL, 0, NULL, 0, 0, 0, 1, NULL, '2020-03-12 17:50:00', '2020-03-12 17:50:00', 1),
	(14, 'Landed Resources', 'Governance', NULL, NULL, 0, NULL, 0, 0, 0, 1, NULL, '2020-03-13 06:28:00', '2020-03-13 06:27:00', 1),
	(15, 'Resource Allocation', 'Governance', NULL, NULL, 0, NULL, 0, 0, 0, 0, NULL, '2020-03-13 07:27:00', '2020-03-13 07:27:00', 15),
	(16, 'Opps', NULL, NULL, NULL, 0, NULL, 0, 0, 0, 0, NULL, '2020-03-13 15:48:00', '2020-03-13 15:48:00', 16);
/*!40000 ALTER TABLE `mastertasks` ENABLE KEYS */;

-- Dumping structure for view dpe_abbott_pb_2020.mastertasks_v
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `mastertasks_v` (
	`id` DECIMAL(5,0) NULL,
	`name` VARCHAR(100) NULL COLLATE 'latin1_swedish_ci',
	`category` VARCHAR(100) NULL COLLATE 'latin1_swedish_ci',
	`reference` VARCHAR(300) NULL COLLATE 'latin1_swedish_ci',
	`created` DATETIME NULL,
	`role_name` VARCHAR(100) NULL COLLATE 'latin1_swedish_ci',
	`role_id` DECIMAL(5,0) NULL,
	`workcount` BIGINT(21) NOT NULL,
	`opencount` BIGINT(21) NOT NULL,
	`waitcount` BIGINT(21) NOT NULL,
	`donecount` BIGINT(21) NOT NULL,
	`totalcount` BIGINT(21) NOT NULL,
	`minutes` DECIMAL(32,2) NULL,
	`lastupdate` DATETIME NULL,
	`inactivity` INT(7) NULL,
	`countdown` INT(7) NULL,
	`perccomplete` DECIMAL(24,0) NULL,
	`status` VARCHAR(100) NULL COLLATE 'latin1_swedish_ci'
) ENGINE=MyISAM;

-- Dumping structure for table dpe_abbott_pb_2020.milestones
CREATE TABLE IF NOT EXISTS `milestones` (
  `ID` decimal(5,0) DEFAULT NULL,
  `MASTERID` decimal(5,0) DEFAULT NULL,
  `TASKID` decimal(5,0) DEFAULT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `DEADLINE` datetime DEFAULT NULL,
  `STATUS` decimal(1,0) DEFAULT NULL,
  `GOLDEN` decimal(1,0) DEFAULT NULL,
  `CREATED` datetime DEFAULT NULL,
  `FINISHED` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table dpe_abbott_pb_2020.milestones: ~0 rows (approximately)
/*!40000 ALTER TABLE `milestones` DISABLE KEYS */;
/*!40000 ALTER TABLE `milestones` ENABLE KEYS */;

-- Dumping structure for table dpe_abbott_pb_2020.roles
CREATE TABLE IF NOT EXISTS `roles` (
  `ID` decimal(5,0) DEFAULT NULL,
  `ROLE_NAME` varchar(100) DEFAULT NULL,
  `PRODUCTIVE` varchar(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table dpe_abbott_pb_2020.roles: ~5 rows (approximately)
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`ID`, `ROLE_NAME`, `PRODUCTIVE`) VALUES
	(1, 'DPE', '1'),
	(2, 'People Manager', '1'),
	(3, 'Value Add', '0'),
	(4, 'Pesonal', '0'),
	(5, 'Employee', '1'),
	(6, 'Other', '0');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;

-- Dumping structure for table dpe_abbott_pb_2020.tasks
CREATE TABLE IF NOT EXISTS `tasks` (
  `MASTERID` decimal(5,0) DEFAULT NULL,
  `ID` decimal(5,0) DEFAULT NULL,
  `NAME` varchar(500) DEFAULT NULL,
  `CATEGORY` varchar(100) DEFAULT NULL,
  `DEADLINE` datetime DEFAULT NULL,
  `TOVERDUE` decimal(3,0) DEFAULT NULL,
  `STARTED` datetime DEFAULT NULL,
  `FINISHED` datetime DEFAULT NULL,
  `WAITING` datetime DEFAULT NULL,
  `STATUS` varchar(100) DEFAULT NULL,
  `MINUTES` decimal(10,2) DEFAULT NULL,
  `REFERENCE` varchar(300) DEFAULT NULL,
  `CHANGEDON` datetime DEFAULT NULL,
  `CREATED` datetime DEFAULT NULL,
  `RESTARTED` datetime DEFAULT NULL,
  `PINNED` varchar(1) DEFAULT NULL,
  `MARKED` varchar(1) DEFAULT NULL,
  `GOLDEN` decimal(1,0) DEFAULT NULL,
  `ROLE_ID` decimal(5,0) DEFAULT NULL,
  KEY `idx_tasks_ROLE_ID` (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table dpe_abbott_pb_2020.tasks: ~85 rows (approximately)
/*!40000 ALTER TABLE `tasks` DISABLE KEYS */;
INSERT INTO `tasks` (`MASTERID`, `ID`, `NAME`, `CATEGORY`, `DEADLINE`, `TOVERDUE`, `STARTED`, `FINISHED`, `WAITING`, `STATUS`, `MINUTES`, `REFERENCE`, `CHANGEDON`, `CREATED`, `RESTARTED`, `PINNED`, `MARKED`, `GOLDEN`, `ROLE_ID`) VALUES
	(2, 1, '01 - Cost Variance Management', 'Weekly', '2020-03-13 00:00:00', 0, '2020-03-11 06:13:00', NULL, '2020-03-11 06:38:00', '2', 53.00, NULL, '2020-03-11 06:38:00', '2020-03-11 05:26:00', '2020-03-11 06:13:00', '0', '0', 1, 0),
	(1, 1, 'My Webex', '', '2020-03-11 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 05:28:00', '2020-03-11 05:28:00', NULL, '0', '0', 0, 0),
	(1, 2, 'PB Reports Abbott pbr', '', '2020-03-11 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 05:30:00', '2020-03-11 05:30:00', NULL, '0', '0', 0, 0),
	(4, 1, 'PowerSteering Reports', 'AUTO', '2020-03-11 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 05:32:00', '2020-03-11 05:32:00', NULL, '0', '0', 0, 0),
	(2, 2, '02 - PB Reports Weekly Refresh', 'Weekly', '2020-03-18 00:00:00', 6, '2020-03-11 05:35:00', NULL, '2020-03-11 06:13:00', '2', 10.00, NULL, '2020-03-12 11:48:00', '2020-03-11 05:33:00', '2020-03-11 05:35:00', '0', '0', 1, 0),
	(4, 2, 'ILC Actuals', '', '2020-03-11 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 05:35:00', '2020-03-11 05:35:00', NULL, '0', '0', 0, 0),
	(2, 3, '03 - IPPF Forecast', 'Monthly', '2020-03-11 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 05:43:00', '2020-03-11 05:40:00', NULL, '0', '0', 0, 0),
	(2, 4, '04 - Cost Overrun Process', 'As needed', '2020-03-11 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 05:43:00', '2020-03-11 05:40:00', NULL, '0', '0', 0, 0),
	(2, 5, '05 - Internal GPE ( Pricing )', 'As needed', '2020-03-11 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 05:43:00', '2020-03-11 05:40:00', NULL, '0', '0', 0, 0),
	(2, 6, '06 - Status report on PD profitability and CNB', 'Weekly', '2020-03-12 00:00:00', 1, '2020-03-11 14:19:00', NULL, '2020-03-11 14:21:00', '2', 2.00, NULL, '2020-03-11 14:21:00', '2020-03-11 05:40:00', '2020-03-11 14:19:00', '0', '0', 0, 0),
	(2, 7, '07 - EWO Review and Signature', 'As needed', '2020-03-11 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 05:43:00', '2020-03-11 05:41:00', NULL, '0', '0', 0, 0),
	(2, 8, '08 - Revenue / Invoice Variance Management', 'Weekly', '2020-03-14 00:00:00', 1, '2020-03-13 11:49:00', NULL, '2020-03-13 12:33:00', '2', 30.00, NULL, '2020-03-13 12:33:00', '2020-03-11 05:41:00', '2020-03-13 12:06:00', '0', '0', 0, 0),
	(2, 9, '09 - Rolloff Management', 'As needed', '2020-03-11 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 05:42:00', '2020-03-11 05:41:00', NULL, '0', '0', 0, 0),
	(2, 10, '10 - IPPF Roadmap', 'Monthly', '2020-03-12 00:00:00', 1, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 05:42:00', '2020-03-11 05:41:00', NULL, '0', '0', 0, 0),
	(2, 11, '11 - Overtime Level Check', 'As needed', '2020-03-11 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 05:42:00', '2020-03-11 05:41:00', NULL, '0', '0', 0, 0),
	(3, 1, 'INC00001 - Refresh is taking very long', '', '2020-03-11 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 05:44:00', '2020-03-11 05:44:00', NULL, '0', '0', 0, 0),
	(3, 2, 'FEA00001 - Report Paging System', '', '2020-03-11 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 05:48:00', '2020-03-11 05:48:00', NULL, '0', '0', 0, 0),
	(1, 3, 'Forecast File - W', '', '2020-03-11 00:00:00', -1, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-12 09:50:00', '2020-03-11 05:48:00', NULL, '0', '0', 0, 0),
	(5, 1, 'Brazil Activities', '', '2020-03-16 00:00:00', 3, '2020-03-11 14:10:00', NULL, '2020-03-13 15:44:00', '2', 40.00, NULL, '2020-03-13 15:44:00', '2020-03-11 06:11:00', '2020-03-13 15:33:00', '0', '0', 0, 0),
	(1, 4, 'Box', '', '2020-03-11 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 06:15:00', '2020-03-11 06:14:00', NULL, '0', '0', 0, 0),
	(1, 5, 'Workbook', '', '2020-03-11 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 06:16:00', '2020-03-11 06:16:00', NULL, '0', '0', 0, 0),
	(1, 6, 'Forecast File - Committed', '', '2020-03-11 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 06:23:00', '2020-03-11 06:23:00', NULL, '0', '0', 0, 0),
	(1, 7, 'Verse', '', '2020-03-11 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 06:32:00', '2020-03-11 06:32:00', NULL, '0', '0', 0, 0),
	(2, 12, '12 - Cost Allocation', 'Weekly', '2020-03-14 00:00:00', 0, '2020-03-11 06:39:00', NULL, '2020-03-14 10:18:00', '2', 219.00, NULL, '2020-03-14 10:18:00', '2020-03-11 06:39:00', '2020-03-14 09:47:00', '0', '0', 1, 0),
	(3, 3, 'REP00001 - Cost Allocation Report', '', '2020-03-11 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 06:55:00', '2020-03-11 06:55:00', NULL, '0', '0', 0, 0),
	(6, 1, 'MEETING - Pre-Travel Orientation - HCAM - Brazil', '', '2020-03-11 00:00:00', 0, '2020-03-11 09:43:00', '2020-03-11 10:39:00', NULL, '3', 69.00, NULL, '2020-03-11 10:39:00', '2020-03-11 09:43:00', '2020-03-11 09:43:00', '0', '0', 0, 0),
	(1, 8, 'Bluepages', '', '2020-03-11 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 09:49:00', '2020-03-11 09:49:00', NULL, '0', '0', 0, 0),
	(7, 1, '2020m03 - Project closure report template', 'Lead: Mukt', '2020-03-11 00:00:00', 0, '2020-03-11 11:41:00', NULL, '2020-03-11 11:41:00', '2', 12.00, NULL, '2020-03-11 11:41:00', '2020-03-11 11:40:00', '2020-03-11 11:41:00', '0', '0', 0, 0),
	(1, 9, 'Abbott Outlook', '', '2020-03-11 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 12:03:00', '2020-03-11 12:03:00', NULL, '0', '0', 0, 0),
	(8, 1, 'Maximo', '', '2020-03-11 00:00:00', 0, '2020-03-11 14:22:00', NULL, '2020-03-11 14:25:00', '2', 3.00, NULL, '2020-03-11 14:25:00', '2020-03-11 14:22:00', '2020-03-11 14:22:00', '0', '0', 0, 0),
	(8, 2, 'Harish', '', '2020-03-11 00:00:00', 0, '2020-03-11 14:25:00', NULL, '2020-03-11 14:26:00', '2', 1.00, NULL, '2020-03-11 14:26:00', '2020-03-11 14:25:00', '2020-03-11 14:25:00', '0', '0', 0, 0),
	(6, 2, 'Brazil LOA', '', '2020-03-11 00:00:00', 0, '2020-03-11 14:26:00', NULL, '2020-03-11 14:46:00', '2', 20.00, NULL, '2020-03-11 14:46:00', '2020-03-11 14:26:00', '2020-03-11 14:26:00', '0', '0', 0, 0),
	(6, 3, 'HCAM documentation', '', '2020-03-11 00:00:00', -1, '2020-03-12 06:51:00', NULL, '2020-03-12 07:09:00', '2', 18.00, NULL, '2020-03-12 07:09:00', '2020-03-11 14:26:00', '2020-03-12 06:51:00', '0', '0', 0, 0),
	(3, 4, 'Planners', '', '2020-03-11 00:00:00', 0, '2020-03-11 14:47:00', '2020-03-11 18:18:00', NULL, '3', 37.00, NULL, '2020-03-11 18:18:00', '2020-03-11 14:38:00', '2020-03-11 17:59:00', '0', '0', 0, 0),
	(1, 10, 'Planners', '', '2020-03-11 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 14:48:00', '2020-03-11 14:48:00', NULL, '0', '0', 0, 0),
	(8, 3, 'Pradip Desai - PJ', 'Abbott SCM Lead', '2020-03-11 00:00:00', 0, '2020-03-11 15:05:00', NULL, '2020-03-11 15:17:00', '2', 12.00, NULL, '2020-03-11 15:17:00', '2020-03-11 14:51:00', '2020-03-11 15:05:00', '0', '0', 0, 0),
	(8, 4, 'Enda Brennan', 'Abbott PMO Lead', '2020-03-12 00:00:00', 1, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 14:52:00', '2020-03-11 14:52:00', NULL, '0', '0', 0, 0),
	(8, 5, 'Daniel Stratiev', 'Abbott Symphony Lead', '2020-03-11 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 14:53:00', '2020-03-11 14:53:00', NULL, '0', '0', 0, 0),
	(8, 6, 'Raj Iyer', 'Abbott Manufacturing and ERP Lead', '2020-03-11 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 14:54:00', '2020-03-11 14:54:00', NULL, '0', '0', 0, 0),
	(8, 7, 'David Stefani', 'Abbott RTR Lead', '2020-03-11 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 14:54:00', '2020-03-11 14:54:00', NULL, '0', '0', 0, 0),
	(9, 1, 'PD-004104 - ADD SAP Remediation of Batch Note audit trail', '', '2020-03-11 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 15:13:00', '2020-03-11 15:12:00', NULL, '0', '0', 0, 0),
	(1, 11, 'PowerSteering - PS', '', '2020-03-11 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 15:12:00', '2020-03-11 15:12:00', NULL, '0', '0', 0, 0),
	(1, 12, 'GovernX', '', '2020-04-22 00:00:00', 11, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 15:42:00', '2020-03-11 15:42:00', NULL, '0', '0', 0, 0),
	(2, 13, '13 - Work Itake', 'Weekly', '2020-03-19 00:00:00', 7, '2020-03-12 11:02:00', NULL, '2020-03-12 12:13:00', '2', 71.00, NULL, '2020-03-12 12:13:00', '2020-03-11 15:51:00', '2020-03-12 11:02:00', '0', '0', 1, 0),
	(6, 4, '2020m03_23 - Flight Ticket', '', '2020-03-13 00:00:00', 0, '2020-03-11 18:18:00', '2020-03-13 15:31:00', NULL, '3', 235.00, NULL, '2020-03-13 15:31:00', '2020-03-11 18:18:00', '2020-03-13 14:50:00', '0', '0', 0, 0),
	(1, 13, 'Concur', '', '2020-03-11 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-11 18:18:00', '2020-03-11 18:18:00', NULL, '0', '0', 0, 0),
	(10, 1, 'FEA00001 - Leveled Access', '', '2020-03-12 00:00:00', 0, '2020-03-12 05:44:00', '2020-03-12 06:50:00', NULL, '3', 66.00, NULL, '2020-03-12 06:50:00', '2020-03-12 05:42:00', '2020-03-12 05:44:00', '0', '0', 0, 0),
	(10, 2, 'IMP00001 - Add enhance (e) to deliverable panel', '', '2020-03-12 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-12 05:46:00', '2020-03-12 05:46:00', NULL, '0', '0', 0, 0),
	(11, 1, 'BCP 2020', 'Corona Virus', '2020-03-12 00:00:00', 0, '2020-03-12 08:44:00', NULL, '2020-03-12 17:09:00', '2', 67.00, NULL, '2020-03-12 17:09:00', '2020-03-12 08:44:00', '2020-03-12 17:06:00', '0', '0', 0, 0),
	(8, 8, 'Account Forecast', '', '2020-03-12 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-12 09:11:00', '2020-03-12 09:10:00', NULL, '0', '0', 0, 0),
	(8, 9, 'Invoicing', '', '2020-03-12 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-12 09:16:00', '2020-03-12 09:16:00', NULL, '0', '0', 0, 0),
	(2, 14, '14 - Signinig Backlog Management', '', '2020-03-12 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-12 09:34:00', '2020-03-12 09:34:00', NULL, '0', '0', 0, 0),
	(8, 10, 'Claim Waiting', '', '2020-03-12 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-12 09:54:00', '2020-03-12 09:54:00', NULL, '0', '0', 0, 0),
	(1, 14, 'ILC Calendar', '', '2020-03-12 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-12 09:57:00', '2020-03-12 09:57:00', NULL, '0', '0', 0, 0),
	(3, 5, 'REP00002 - Project Going Live in the next 4 weeks', '', '2020-03-12 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-12 11:18:00', '2020-03-12 11:18:00', NULL, '0', '0', 0, 0),
	(2, 15, '15 - PB Weekly Internal Status Report', 'Weekly, Delivery, Sagar', '2020-03-16 00:00:00', 4, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-12 11:48:00', '2020-03-12 11:45:00', NULL, '0', '0', 0, 0),
	(8, 11, 'Role Tower Leads', '', '2020-03-12 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-12 11:54:00', '2020-03-12 11:54:00', NULL, '0', '0', 0, 0),
	(8, 12, 'Companion Agreements', '', '2020-03-12 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-12 11:54:00', '2020-03-12 11:54:00', NULL, '0', '0', 0, 0),
	(8, 13, 'Red Hat', '', '2020-03-12 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-12 11:57:00', '2020-03-12 11:56:00', NULL, '0', '0', 0, 0),
	(8, 14, 'SLC', '', '2020-03-19 00:00:00', 6, '2020-03-12 12:13:00', NULL, '2020-03-12 12:50:00', '2', 37.00, NULL, '2020-03-12 12:50:00', '2020-03-12 12:13:00', '2020-03-12 12:13:00', '0', '0', 0, 0),
	(8, 15, 'Right First Time - RFT', '', '2020-03-12 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-12 12:18:00', '2020-03-12 12:18:00', NULL, '0', '0', 0, 0),
	(8, 16, 'Presentations', '', '2020-03-12 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-12 12:36:00', '2020-03-12 12:36:00', NULL, '0', '0', 0, 0),
	(12, 1, '2020m03 - Improve the RFT (Right First Time) Metric', '', '2020-03-12 00:00:00', 0, '2020-03-12 12:50:00', NULL, '2020-03-12 12:51:00', '2', 1.00, NULL, '2020-03-12 12:51:00', '2020-03-12 12:50:00', '2020-03-12 12:50:00', '0', '0', 0, 0),
	(9, 2, 'PD-004211 - Lighthouse - Global BI for Supply Chain', '', '2020-03-12 00:00:00', 0, '2020-03-12 17:03:00', NULL, '2020-03-12 17:06:00', '2', 3.00, NULL, '2020-03-12 17:25:00', '2020-03-12 17:03:00', '2020-03-12 17:03:00', '0', '0', 0, 0),
	(6, 5, 'EMAIL - Welcome Kit', '', '2020-03-12 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-12 17:47:00', '2020-03-12 17:39:00', NULL, '0', '0', 0, 0),
	(6, 6, 'EMAIL - IBM US HR', '', '2020-03-12 00:00:00', -1, '2020-03-13 06:16:00', NULL, '2020-03-13 06:52:00', '2', 36.00, NULL, '2020-03-13 06:52:00', '2020-03-12 17:47:00', '2020-03-13 06:16:00', '0', '0', 0, 0),
	(13, 1, '2020m03 - DS&P report Tracking', '', '2020-03-12 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-12 17:50:00', '2020-03-12 17:50:00', NULL, '0', '0', 0, 0),
	(9, 3, 'PD-004022 - International Multi-Location Deployment 2019', 'MLD', '2020-03-13 00:00:00', 0, '2020-03-13 12:33:00', NULL, '2020-03-13 14:23:00', '2', 47.00, NULL, '2020-03-13 14:23:00', '2020-03-13 06:22:00', '2020-03-13 12:33:00', '0', '0', 0, 0),
	(9, 4, 'PD-003537 - 2019 - Serialization - Russian Fed - All Sites - Symphony', '', '2020-03-13 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-13 07:15:00', '2020-03-13 06:22:00', NULL, '0', '0', 0, 0),
	(14, 1, 'Senthil Kumar Kasturi Subramanian', '', '2020-03-13 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-13 06:27:00', '2020-03-13 06:27:00', NULL, '0', '0', 0, 0),
	(3, 6, 'BUG00001 - Resource Name is coming from from Actuals', '', '2020-03-13 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-13 07:20:00', '2020-03-13 07:20:00', NULL, '0', '0', 0, 0),
	(3, 7, 'FEA00002 - Work item to PD mapping to address incorrect tittle', '', '2020-03-13 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-13 07:22:00', '2020-03-13 07:22:00', NULL, '0', '0', 0, 0),
	(15, 1, 'Ana Robertson', '', '2020-03-13 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-13 07:27:00', '2020-03-13 07:27:00', NULL, '0', '0', 0, 0),
	(8, 17, 'Tapan', '', '2020-03-13 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-13 09:36:00', '2020-03-13 09:36:00', NULL, '0', '0', 0, 0),
	(3, 8, 'REP00002 - Planner Info Extract', '', '2020-03-14 00:00:00', 1, '2020-03-13 09:38:00', '2020-03-13 11:40:00', NULL, '3', 122.00, NULL, '2020-03-13 11:40:00', '2020-03-13 09:38:00', '2020-03-13 09:38:00', '0', '0', 0, 0),
	(8, 18, 'Abbott SOPs', '', '2020-03-13 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-13 09:45:00', '2020-03-13 09:45:00', NULL, '0', '0', 0, 0),
	(12, 2, '2020m03 - Better Cost Management', '', '2020-03-13 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-13 10:51:00', '2020-03-13 10:40:00', NULL, '0', '0', 0, 0),
	(2, 16, '16 - Stakeholder Management', '', '2020-03-13 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-13 10:49:00', '2020-03-13 10:49:00', NULL, '0', '0', 0, 0),
	(12, 3, '2020m03 - Leadership hours should be in the EWO', '', '2020-03-13 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-13 10:51:00', '2020-03-13 10:51:00', NULL, '0', '0', 0, 0),
	(1, 15, 'Invoice Release Trackers', '', '2020-03-13 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-13 11:49:00', '2020-03-13 11:49:00', NULL, '0', '0', 0, 0),
	(8, 19, 'PB to KTLO Transition', '', '2020-03-13 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-13 12:48:00', '2020-03-13 12:48:00', NULL, '0', '0', 0, 0),
	(16, 1, 'IBM MSP for Japan EDI', '', '2020-03-14 00:00:00', 1, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-13 15:48:00', '2020-03-13 15:48:00', NULL, '0', '0', 0, 0),
	(10, 3, 'Setup', '', '2020-03-14 00:00:00', 1, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-13 15:48:00', '2020-03-13 15:48:00', NULL, '0', '0', 0, 0),
	(5, 2, 'Computer Backup', '', '2020-03-14 00:00:00', 0, '2020-03-14 13:46:00', NULL, NULL, '0', 0.00, NULL, '2020-03-14 13:46:00', '2020-03-14 13:46:00', '2020-03-14 13:46:00', '0', '0', 0, 0),
	(10, 4, 'SOURCE CODE', '', '2020-03-14 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-14 14:23:00', '2020-03-14 14:23:00', NULL, '0', '0', 0, 0),
	(10, 5, 'Instalation Guide', '', '2020-03-14 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-14 14:25:00', '2020-03-14 14:25:00', NULL, '0', '0', 0, 0),
	(10, 6, 'Database Backup', '', '2020-03-14 00:00:00', 0, NULL, NULL, NULL, '1', 0.00, NULL, '2020-03-14 14:26:00', '2020-03-14 14:26:00', NULL, '0', '0', 0, 0);
/*!40000 ALTER TABLE `tasks` ENABLE KEYS */;

-- Dumping structure for table dpe_abbott_pb_2020.waits
CREATE TABLE IF NOT EXISTS `waits` (
  `ID` decimal(5,0) DEFAULT NULL,
  `MASTERID` decimal(5,0) DEFAULT NULL,
  `TASKID` decimal(5,0) DEFAULT NULL,
  `STARTED` datetime DEFAULT NULL,
  `FINISHED` datetime DEFAULT NULL,
  `MINUTES` decimal(10,0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table dpe_abbott_pb_2020.waits: ~42 rows (approximately)
/*!40000 ALTER TABLE `waits` DISABLE KEYS */;
INSERT INTO `waits` (`ID`, `MASTERID`, `TASKID`, `STARTED`, `FINISHED`, `MINUTES`) VALUES
	(0, 2, 2, '2020-03-11 05:35:00', '2020-03-11 05:45:00', 10),
	(1, 2, 1, '2020-03-11 05:45:00', '2020-03-11 06:13:00', 28),
	(2, 2, 1, '2020-03-11 06:13:00', '2020-03-11 06:38:00', 25),
	(3, 2, 12, '2020-03-11 06:39:00', '2020-03-11 09:02:00', 143),
	(4, 6, 1, '2020-03-11 09:30:00', '2020-03-11 09:43:00', 13),
	(5, 6, 1, '2020-03-11 09:43:00', '2020-03-11 10:39:00', 56),
	(6, 7, 1, '2020-03-11 11:29:00', '2020-03-11 11:41:00', 12),
	(7, 2, 12, '2020-03-11 11:42:00', '2020-03-11 12:20:00', 38),
	(8, 5, 1, '2020-03-11 14:10:00', '2020-03-11 14:19:00', 9),
	(9, 2, 6, '2020-03-11 14:19:00', '2020-03-11 14:21:00', 2),
	(10, 8, 1, '2020-03-11 14:22:00', '2020-03-11 14:25:00', 3),
	(11, 8, 2, '2020-03-11 14:25:00', '2020-03-11 14:26:00', 1),
	(12, 6, 2, '2020-03-11 14:26:00', '2020-03-11 14:46:00', 20),
	(13, 3, 4, '2020-03-11 14:47:00', '2020-03-11 15:05:00', 18),
	(14, 8, 3, '2020-03-11 15:05:00', '2020-03-11 15:17:00', 12),
	(15, 5, 1, '2020-03-11 15:17:00', '2020-03-11 15:37:00', 20),
	(16, 3, 4, '2020-03-11 17:59:00', '2020-03-11 18:18:00', 19),
	(17, 6, 4, '2020-03-11 18:18:00', '2020-03-11 18:43:00', 25),
	(18, 10, 1, '2020-03-12 05:44:00', '2020-03-12 06:50:00', 66),
	(19, 6, 3, '2020-03-12 06:51:00', '2020-03-12 07:09:00', 18),
	(20, 11, 1, '2020-03-12 08:44:00', '2020-03-12 09:05:00', 21),
	(21, 11, 1, '2020-03-12 10:56:00', '2020-03-12 11:02:00', 6),
	(22, 2, 13, '2020-03-12 11:02:00', '2020-03-12 12:13:00', 71),
	(23, 8, 14, '2020-03-12 12:13:00', '2020-03-12 12:50:00', 37),
	(24, 12, 1, '2020-03-12 12:50:00', '2020-03-12 12:51:00', 1),
	(25, 11, 1, '2020-03-12 13:06:00', '2020-03-12 13:33:00', 27),
	(26, 11, 1, '2020-03-12 14:58:00', '2020-03-12 15:08:00', 10),
	(27, 6, 4, '2020-03-12 15:08:00', '2020-03-12 17:02:00', 114),
	(28, 9, 2, '2020-03-12 17:03:00', '2020-03-12 17:06:00', 3),
	(29, 11, 1, '2020-03-12 17:06:00', '2020-03-12 17:09:00', 3),
	(30, 6, 4, '2020-03-12 17:09:00', '2020-03-12 17:39:00', 30),
	(31, 6, 4, '2020-03-12 17:57:00', '2020-03-12 18:22:00', 25),
	(32, 6, 6, '2020-03-13 06:16:00', '2020-03-13 06:52:00', 36),
	(33, 2, 12, '2020-03-13 09:31:00', '2020-03-13 09:38:00', 7),
	(34, 3, 8, '2020-03-13 09:38:00', '2020-03-13 11:40:00', 122),
	(35, 2, 8, '2020-03-13 11:49:00', '2020-03-13 11:52:00', 3),
	(36, 5, 1, '2020-03-13 11:52:00', '2020-03-13 11:52:00', 0),
	(37, 2, 8, '2020-03-13 12:06:00', '2020-03-13 12:33:00', 27),
	(38, 9, 3, '2020-03-13 12:33:00', '2020-03-13 13:20:00', 47),
	(39, 6, 4, '2020-03-13 14:50:00', '2020-03-13 15:31:00', 41),
	(40, 5, 1, '2020-03-13 15:33:00', '2020-03-13 15:44:00', 11),
	(41, 2, 12, '2020-03-14 09:47:00', '2020-03-14 10:18:00', 31);
/*!40000 ALTER TABLE `waits` ENABLE KEYS */;

-- Dumping structure for view dpe_abbott_pb_2020.mastertasks_v
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `mastertasks_v`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` VIEW `mastertasks_v` AS select `m`.`ID` AS `id`,`m`.`NAME` AS `name`,`m`.`CATEGORY` AS `category`,`m`.`REFERENCE` AS `reference`,`m`.`CREATED` AS `created`,`r`.`ROLE_NAME` AS `role_name`,`m`.`role_id` AS `role_id`,count((case `t`.`STATUS` when 0 then 1 else NULL end)) AS `workcount`,count((case `t`.`STATUS` when 1 then 1 else NULL end)) AS `opencount`,count((case `t`.`STATUS` when 2 then 1 else NULL end)) AS `waitcount`,count((case `t`.`STATUS` when 3 then 1 else NULL end)) AS `donecount`,count(0) AS `totalcount`,sum(`t`.`MINUTES`) AS `minutes`,max(`t`.`CHANGEDON`) AS `lastupdate`,(to_days(max(`t`.`CHANGEDON`)) - to_days(sysdate())) AS `inactivity`,(to_days(min((case `t`.`STATUS` when 3 then NULL else `t`.`DEADLINE` end))) - to_days(sysdate())) AS `countdown`,round((100 * (count((case `t`.`STATUS` when 3 then 1 else NULL end)) / count(0))),0) AS `perccomplete`,min(ifnull(`t`.`STATUS`,3)) AS `status` from ((`mastertasks` `m` left join `tasks` `t` on((`t`.`MASTERID` = `m`.`ID`))) left join `roles` `r` on((`r`.`ID` = `m`.`role_id`))) group by `m`.`ID` order by `status`,`countdown`,`lastupdate` ;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
