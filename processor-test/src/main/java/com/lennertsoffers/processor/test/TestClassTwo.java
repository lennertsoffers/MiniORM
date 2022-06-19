package com.lennertsoffers.processor.test;

import com.lennertsoffers.annotation.processor.annotations.Entity;
import com.lennertsoffers.annotation.processor.annotations.Id;

@Entity
public class TestClassTwo {
    @Id
    private int id;
    private String className;
    private float aFloat;
    private double aDouble;
    private long aLong;
    private int anInt;
    private boolean aBoolean;
}
