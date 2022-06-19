DROP TABLE IF EXISTS little_class;
CREATE TABLE little_class (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255)
);

DROP TABLE IF EXISTS TestClassTwo;
CREATE TABLE TestClassTwo (
    id INT PRIMARY KEY AUTO_INCREMENT,
    className VARCHAR(255),
    aFloat FLOAT,
    aDouble DOUBLE,
    aLong LONG,
    anInt INT,
    aBoolean BOOLEAN
);

