package org.example.java;

public class PairPrograming {

    //Write a program where a class can take upto 4 numeric arguments, during class instance creation.
    // It should have the ability to get and set any of the initalized value later in the execution.
    // It should have a method named sum with no aruments that will sum up all the values.


    private int id;

    private int marks;

    private int numberOfSubjects;

    private int marksInMaths;

    public PairPrograming(int id, int marks, int numberOfSubjects, int marksInMaths) {
        this.id = id;
        this.marks = marks;
        this.numberOfSubjects = numberOfSubjects;
        this.marksInMaths = marksInMaths;
    }

    public int sumOfAll() {
        return id + marks + numberOfSubjects + marksInMaths;
    }

    public int getId() {
        return id;
    }

    public PairPrograming setId(int id) {
        this.id = id;
        return this;
    }

    public int getMarks() {
        return marks;
    }

    public PairPrograming setMarks(int marks) {
        this.marks = marks;
        return this;
    }

    public int getNumberOfSubjects() {
        return numberOfSubjects;
    }

    public PairPrograming setNumberOfSubjects(int numberOfSubjects) {
        this.numberOfSubjects = numberOfSubjects;
        return this;
    }

    public int getMarksInMaths() {
        return marksInMaths;
    }

    public PairPrograming setMarksInMaths(int marksInMaths) {
        this.marksInMaths = marksInMaths;
        return this;
    }

    @Override
    public String toString() {
        return "PairPrograming{" +
                "id=" + id +
                ", marks=" + marks +
                ", numberOfSubjects=" + numberOfSubjects +
                ", marksInMaths=" + marksInMaths +
                '}';
    }
}
