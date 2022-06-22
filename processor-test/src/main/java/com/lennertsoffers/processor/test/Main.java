package com.lennertsoffers.processor.test;

import com.lennertsoffers.persistence.api.PersistenceAPI;
import com.lennertsoffers.persistence.api.repositories.Repository;

public class Main {
    public static void main(String[] args) {
        PersistenceAPI.initialize();

        Repository<Integer, TestClass> testClassRepository = new com.lennertsoffers.processor.test.TestClassBaseRepository();
        Repository<Integer, TestClassTwo> testClassTwoRepository = new com.lennertsoffers.processor.test.TestClassTwoBaseRepository();
        TestClass testClass1 = new TestClass("first_test_class");
        TestClass testClass2 = new TestClass("second_test_class");
        TestClassTwo tct1 = new TestClassTwo(null, 1.0f, 1.0, 1L, 1, true);
        TestClassTwo tct2 = new TestClassTwo("tct2", 1.0f, 1.0, 1L, 1, true);
        tct2.setWrapperDouble(null);

        testClassRepository.save(testClass1);
        testClassRepository.save(testClass2);
        testClassTwoRepository.save(tct1);
        testClassTwoRepository.save(tct2);

        TestClass tc1 = testClassRepository.findById(1).get();
        TestClass tc2 = testClassRepository.findById(2).get();
        System.out.println(tc1);
        System.out.println(tc2);

        TestClassTwo tct3 = testClassTwoRepository.findById(1).get();
        TestClassTwo tct4 = testClassTwoRepository.findById(2).get();


        System.out.println(tct3);
        System.out.println(tct4);
    }
}
