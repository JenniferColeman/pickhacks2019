
-- -----------------------------------------------------
-- Table `mydb`.`MY_GARDEN`
-- -----------------------------------------------------

CREATE TABLE MY_GARDEN(
    u_name VARCHAR(16) NOT NULL,
    pref_diet VARCHAR(2048) NOT NULL,
    PRIMARY KEY(u_name)
);

-- -----------------------------------------------------
-- Table `mydb`.`PLANT`
-- -----------------------------------------------------
CREATE TABLE PLANT(
    p_name VARCHAR(50) NOT NULL,
    freq_water INT NOT NULL,
    harvest_time VARCHAR(2048) NOT NULL,
    gen_advice VARCHAR(2048),
    harvest_advice VARCHAR(2048),
    care_advice VARCHAR(2048),
    planting_advice VARCHAR(2048),
    picture BLOB,
    descrip VARCHAR(2048) NOT NULL,
    PRIMARY KEY(p_name)

);

-- -----------------------------------------------------
-- Table `mydb`.`PLANTED`
-- -----------------------------------------------------
CREATE TABLE PLANTED(
    p_name VARCHAR(50) NOT NULL,
    u_name VARCHAR(16) NOT NULL,
    date_planted DATE,
    amount INT NOT NULL,
    FOREIGN KEY(p_name) REFERENCES PLANT(p_name) ON DELETE CASCADE,
    FOREIGN KEY(u_name) REFERENCES MY_GARDEN(u_name) ON DELETE CASCADE,
    PRIMARY KEY(p_name, u_name)
);

-- -----------------------------------------------------
-- Table `mydb`.`HARDINESS_ZONE`
-- -----------------------------------------------------
CREATE TABLE HARDINESS_ZONE(
    p_name VARCHAR(50) NOT NULL,
    zone INT NOT NULL,
    FOREIGN KEY(p_name) REFERENCES PLANT(p_name) ON DELETE CASCADE,
    PRIMARY KEY(p_name, zone)
);

-- -----------------------------------------------------
-- Table `mydb`.`SEASON`
-- -----------------------------------------------------
CREATE TABLE SEASON(
    p_name VARCHAR(50) NOT NULL,
    season VARCHAR(50) NOT NULL,
    FOREIGN KEY (p_name) REFERENCES PLANT (p_name) ON DELETE CASCADE,
    PRIMARY KEY(p_name, season)
);

-- -----------------------------------------------------
-- Table `mydb`.`DIET_COMPAT`
-- -----------------------------------------------------
CREATE TABLE DIET_COMPAT(
    p_name VARCHAR(50) NOT NULL,
    diet_name VARCHAR(100) NOT NULL,
    compat_score BINARY(1) NOT NULL,
    FOREIGN KEY(p_name) REFERENCES PLANT (p_name) ON DELETE CASCADE,
    PRIMARY KEY(p_name, diet_name)
);