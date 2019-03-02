-- -----------------------------------------------------
-- Select plants planeted in garden: names, amount planted, date planted
-- -----------------------------------------------------
SELECT p_name, amount, date_planted FROM `PLANTED` WHERE 1

-- -----------------------------------------------------
-- Select plant names similar to provided name
-- -----------------------------------------------------
SELECT p_name, picture, descrip
FROM PLANT
WHERE p_name LIKE %;

-- -----------------------------------------------------
-- Select plants based on diet
-- -----------------------------------------------------
SELECT PLANT.p_name, picture, compat_score
FROM PLANT, DIET_COMPAT
WHERE PLANT.p_name = DIET_COMPAT.p_name AND diet_name = "INPUT DIET NAME";

-- -----------------------------------------------------
-- Find Plants the suit a specific diet
-- -----------------------------------------------------
SELECT PLANT.p_name, picture, descrip
FROM PLANT, DIET_COMPAT
WHERE PLANT.p_name = DIET_COMPAT.p_name AND diet_name = "USER INPUT DIET NAME" AND compat_score = 1;

-- -----------------------------------------------------
-- Pull all details of plant
-- -----------------------------------------------------
SELECT *
FROM PLANT
WHERE p_name = "INPUT PLANT NAME";


-- -----------------------------------------------------
-- Pull Hardiness Zone
-- -----------------------------------------------------
SELECT zone
FROM PLANT, HARDINESS_ZONE
WHERE PLANT.p_name = HARDINESS_ZONE.p_name AND PLANT.p_name = "INSERT PLANT NAME";

-- -----------------------------------------------------
-- Pull seasons
-- -----------------------------------------------------
SELECT seasons
FROM PLANT, SEASON
WHERE PLANT.p_name = SEASON.p_name AND PLANT.p_name = "INSERT PLANT NAME";

-- -----------------------------------------------------
-- Pull all the diets that are compatible for a specific plant
-- -----------------------------------------------------
SELECT diet_name
FROM PLANT, DIET_COMPAT
WHERE PLANT.p_name = DIET_COMPAT.p_name AND PLANT.p_name = "INSERT PLANT NAME" and compat_score = 1;


-- -----------------------------------------------------
-- Total number of all plants in garden
-- -----------------------------------------------------
SELECT SUM(amount)
FROM PLANTED;

-- -----------------------------------------------------
-- Total number of different plants
-- -----------------------------------------------------
SELECT COUNT(*)
FROM PLANTED;

-- -----------------------------------------------------
-- Add new plant to planted
-- -----------------------------------------------------
INSERT INTO PLANTED (p_name, u_name, date_planted, amount) VALUES ("INSERT PLANT NAME","INSERT USER NAME",GETDATE(),"INSERT AMOUNT OF");

-- -----------------------------------------------------
-- Remove Plant from My Garden
-- -----------------------------------------------------
DELETE FROM PLANTED WHERE p_name = "INPUT PLANT NAME" AND date_planted = "INSERT DATE PLANTED";

-- -----------------------------------------------------
-- Change Diet
-- -----------------------------------------------------
UPDATE MY_GARDEN SET pref_diet = "INSERT NEW DIET NAME" WHERE u_name = "USER NAME";

