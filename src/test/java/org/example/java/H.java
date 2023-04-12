package org.example.java;

public class H {

    public static void main(String[] args) {
        String str = new String("abc");

        String str1 = "abc";

        boolean b = str == str1;

        boolean c = str.equals(str1);


        boolean b1 = str.hashCode() == str1.hashCode();
        System.out.println(b);
        System.out.println(c);
        System.out.println(b1);


    }
}
