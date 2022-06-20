package com.lennertsoffers.processor.test;

import com.lennertsoffers.persistence.api.PersistenceAPI;
import com.lennertsoffers.persistence.api.repositories.Repository;
import com.lennertsoffers.processor.test.TestClassBaseRepository;

public class Main {
    public static void main(String[] args) {
        PersistenceAPI.initialize();

        Repository<TestClass> testClassRepository = new TestClassBaseRepository();
        TestClass testClass1 = new TestClass("first_test_class");
        TestClass testClass2 = new TestClass("second_test_class");

        testClassRepository.save(testClass1);
        testClassRepository.save(testClass2);
    }
}
