-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 23-08-2022 a las 10:15:51
-- Versión del servidor: 5.7.36
-- Versión de PHP: 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `nakpark`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_estacionamiento`
--

DROP TABLE IF EXISTS `detalle_estacionamiento`;
CREATE TABLE IF NOT EXISTS `detalle_estacionamiento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `razon` varchar(50) DEFAULT NULL,
  `ruc` varchar(11) DEFAULT NULL,
  `direccion` varchar(50) DEFAULT NULL,
  `celular` varchar(9) DEFAULT NULL,
  `comentario` varchar(300) DEFAULT NULL,
  `igv` double DEFAULT NULL,
  `capacidad` int(11) DEFAULT NULL,
  `asignarcasillero` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `detalle_estacionamiento`
--

INSERT INTO `detalle_estacionamiento` (`id`, `razon`, `ruc`, `direccion`, `celular`, `comentario`, `igv`, `capacidad`, `asignarcasillero`) VALUES
(1, 'estacionamiento', '35434534534', '151 west 34 street', '916015263', 'xddd', 0.5, 50, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

DROP TABLE IF EXISTS `producto`;
CREATE TABLE IF NOT EXISTS `producto` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) NOT NULL,
  `tarifa` double NOT NULL,
  `horas` double NOT NULL,
  `sobreestadia` double NOT NULL,
  `tolerancia` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`id`, `nombre`, `tarifa`, `horas`, `sobreestadia`, `tolerancia`) VALUES
(3, 'autos', 10, 3, 5, 0),
(11, 'moto 1 hora', 3, 1, 3, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usu` varchar(20) NOT NULL,
  `contra` varchar(20) NOT NULL,
  `rol` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `usu`, `contra`, `rol`) VALUES
(1, 'admin', '123', 'admin');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vehiculo`
--

DROP TABLE IF EXISTS `vehiculo`;
CREATE TABLE IF NOT EXISTS `vehiculo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `placa` varchar(8) NOT NULL,
  `producto` varchar(20) NOT NULL,
  `espacio` int(11) DEFAULT NULL,
  `horaentrada` varchar(50) DEFAULT NULL,
  `horasalida` varchar(50) DEFAULT NULL,
  `valorpagado` double DEFAULT NULL,
  `estado` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
