package ru.job4j.serialization.xml;

import jakarta.xml.bind.annotation.*;

import java.util.Arrays;
import java.util.Objects;

@XmlRootElement(name = "student")
@XmlAccessorType(XmlAccessType.FIELD)
public class Student {
    @XmlAttribute
    private boolean isFullTime;
    @XmlAttribute
    private int age;
    @XmlAttribute
    private String name;
    @XmlElement
    private Address address;
    @XmlElementWrapper(name = "subjects")
    @XmlElement(name = "subject")
    private String[] subjects;

    public Student() {
    }

    public Student(boolean isFullTime, int age, String name, Address address, String[] subjects) {
        this.isFullTime = isFullTime;
        this.age = age;
        this.name = name;
        this.address = address;
        this.subjects = subjects;
    }

    public boolean isFullTime() {
        return isFullTime;
    }

    public void setFullTime(boolean fullTime) {
        isFullTime = fullTime;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String[] getSubjects() {
        return subjects;
    }

    public void setSubjects(String[] subjects) {
        this.subjects = subjects;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return isFullTime == student.isFullTime && age == student.age && Objects.equals(name, student.name) && Objects.equals(address, student.address) && Objects.deepEquals(subjects, student.subjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isFullTime, age, name, address, Arrays.hashCode(subjects));
    }

    @Override
    public String toString() {
        return "Student{"
                + "isFullTime="
                + isFullTime
                + ", age="
                + age
                + ", name='"
                + name + '\''
                + ", address="
                + address
                + ", subjects="
                + Arrays.toString(subjects) + '}';
    }
}
