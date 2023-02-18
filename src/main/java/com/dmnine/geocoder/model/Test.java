package com.dmnine.geocoder.model;


import javax.persistence.*;
import java.util.Objects;
@Entity
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;
    @Enumerated(EnumType.STRING)
    private Mark mark;
    private boolean done;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Test test = (Test) o;
        return done == test.done && Objects.equals(id, test.id) && Objects.equals(name, test.name) && mark == test.mark;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, mark, done);
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mark=" + mark +
                ", done=" + done +
                '}';
    }
}



