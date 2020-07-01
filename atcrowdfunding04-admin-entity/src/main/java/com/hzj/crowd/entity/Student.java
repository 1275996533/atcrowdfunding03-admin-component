package com.hzj.crowd.entity;


import java.util.List;
import java.util.Map;

/**
 * @ClassName Student
 * @Description TODO
 * @Author 黄政杰
 * @Date 2020/6/13 13:35
 * @Version 1.0
 **/
public class Student {
    private String name;
    private Integer age;
    private List<Subject> subjects;
    private Address address;
    private Map map;

    public Student() {
    }

    public Student(String name, Integer age, List<Subject> subjects, Address address, Map map) {
        this.name = name;
        this.age = age;
        this.subjects = subjects;
        this.address = address;
        this.map = map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", subjects=" + subjects +
                ", address=" + address +
                ", map=" + map +
                '}';
    }
}
