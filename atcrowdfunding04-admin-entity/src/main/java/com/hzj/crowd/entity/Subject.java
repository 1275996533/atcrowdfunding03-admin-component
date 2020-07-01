package com.hzj.crowd.entity;

/**
 * @ClassName Subject
 * @Description TODO
 * @Author 黄政杰
 * @Date 2020/6/13 13:37
 * @Version 1.0
 **/
public class Subject {
    private String name;
    private Integer score;

    public Subject() {
    }

    public Subject(String name, Integer score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", Score=" + score +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
