CREATE SCHEMA IF NOT EXISTS HEATMAP;
SET SCHEMA HEATMAP;

CREATE TABLE CALL_LOG(
ID VARCHAR(45), 
USER_ID VARCHAR(45),
USERNAME VARCHAR(255),
ONOFF_NUMBER VARCHAR(45), 
CONTACT_NUMBER VARCHAR(45), 
STATUS VARCHAR(45),
INCOMING TINYINT(1), 
DURATION INT,
STARTED_AT TIMESTAMP(3),
ENDED_AT TIMESTAMP(3)
);