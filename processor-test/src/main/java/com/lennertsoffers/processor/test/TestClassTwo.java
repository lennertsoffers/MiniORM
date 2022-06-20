package com.lennertsoffers.processor.test;

import com.lennertsoffers.annotation.processor.annotations.Entity;
import com.lennertsoffers.annotation.processor.annotations.Id;

@Entity
public class TestClassTwo {
    @Id
    private int id;
    private String className;
    private float baseFloat;
    private double baseDouble;
    private long baseLong;
    private int baseInt;
    private boolean baseBoolean;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public float getBaseFloat() {
        return baseFloat;
    }

    public void setBaseFloat(float baseFloat) {
        this.baseFloat = baseFloat;
    }

    public double getBaseDouble() {
        return baseDouble;
    }

    public void setBaseDouble(double baseDouble) {
        this.baseDouble = baseDouble;
    }

    public long getBaseLong() {
        return baseLong;
    }

    public void setBaseLong(long baseLong) {
        this.baseLong = baseLong;
    }

    public int getBaseInt() {
        return baseInt;
    }

    public void setBaseInt(int baseInt) {
        this.baseInt = baseInt;
    }

    public boolean isBaseBoolean() {
        return baseBoolean;
    }

    public void setBaseBoolean(boolean baseBoolean) {
        this.baseBoolean = baseBoolean;
    }
}
