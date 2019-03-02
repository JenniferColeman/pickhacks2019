-- MySQL Script generated by MySQL Workbench
-- Sat Mar  2 10:13:21 2019
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`MY_GARDEN`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`MY_GARDEN` (
  `u_name` VARCHAR(16) NOT NULL,
  `pref_diet` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`u_name`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`PLANT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`PLANT` (
  `freq_water` INT NOT NULL,
  `harvest_time` INT NOT NULL,
  `gen_advice` VARCHAR(250) NULL,
  `planting_advice` VARCHAR(250) NULL,
  `harvest_advice` VARCHAR(250) NULL,
  `care_advice` VARCHAR(250) NULL,
  `picture` BLOB NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`name`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`PLANTED`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`PLANTED` (
  `u_name` VARCHAR(16) NOT NULL,
  `plant_name` VARCHAR(45) NOT NULL,
  `date_planted` DATE NOT NULL,
  `amount` INT NOT NULL,
  PRIMARY KEY (`u_name`, `plant_name`),
  INDEX `plant_name_idx` (`plant_name` ASC) VISIBLE,
  CONSTRAINT `plant_name`
    FOREIGN KEY (`plant_name`)
    REFERENCES `mydb`.`PLANT` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `u_name`
    FOREIGN KEY (`u_name`)
    REFERENCES `mydb`.`MY_GARDEN` (`u_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`GROWTH_ZONE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`GROWTH_ZONE` (
  `plant_name` VARCHAR(45) NOT NULL,
  `zone` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`plant_name`),
  CONSTRAINT `plant_name`
    FOREIGN KEY (`plant_name`)
    REFERENCES `mydb`.`PLANT` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`SEASON`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`SEASON` (
  `plant_name` VARCHAR(45) NOT NULL,
  `season` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`plant_name`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`DIET_COMPATIBLE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`DIET_COMPATIBLE` (
  `plant_name` VARCHAR(45) NOT NULL,
  `diet` VARCHAR(45) NOT NULL,
  `compatibility` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`plant_name`),
  UNIQUE INDEX `diet_UNIQUE` (`diet` ASC) VISIBLE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;