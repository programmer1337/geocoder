package com.dmnine.geocoder.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Сущноть тест.
 * Содержит необходимые поля и методы доступа к ним.
 */

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

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(final Mark mark) {
        this.mark = mark;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(final boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }
        final Test test = (Test) o;
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



