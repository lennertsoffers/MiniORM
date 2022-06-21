DROP TABLE IF EXISTS test_class_two;
CREATE TABLE test_class_two (
    test_class_two_id INT PRIMARY KEY AUTO_INCREMENT,
    class_name VARCHAR(255),
    base_float FLOAT,
    base_double DOUBLE,
    base_long BIGINT,
    base_int INT,
    base_boolean BOOLEAN,
    wrapper_float VARCHAR(255),
    wrapper_double VARCHAR(255),
    wrapper_long VARCHAR(255),
    wrapper_int VARCHAR(255),
    wrapper_boolean VARCHAR(255)
);

