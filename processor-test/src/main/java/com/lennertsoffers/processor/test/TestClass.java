package com.lennertsoffers.processor.test;

import com.lennertsoffers.annotation.processor.annotations.Entity;
import com.lennertsoffers.annotation.processor.annotations.Id;

@Entity("little_class")
public class TestClass {
    @Id
    private int id;
    private String name;

    public TestClass(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public TestClass(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
