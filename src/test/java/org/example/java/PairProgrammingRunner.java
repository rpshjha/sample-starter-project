package org.example.java;

public class PairProgrammingRunner {


    public static void main(String[] args) {


        PairPrograming pairPrograming = new PairPrograming(2, 49, 93, 23);

        System.out.println(pairPrograming.getMarks());

        pairPrograming.setNumberOfSubjects(4);

        System.out.println(pairPrograming.getNumberOfSubjects());

        System.out.println(pairPrograming.sumOfAll());
    }
}
