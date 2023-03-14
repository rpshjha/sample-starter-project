package org.example.test;

public class Interview {

    public static void main(String[] args) {

//        String s = "fez day";
//        String s= "day fyyyz";
//        String s = "day fez";
        String s = "!!day--yaz!!";
//        String s = "DAY abc XYZ";

        Interview interview = new Interview();

        int ans = interview.countYZ(s);

        System.out.println(ans);
    }

    public int countYZ(String str) {

        String[] arry = str.split("[^a-zA-Z]");

        int counter = 0;

        for (String temp : arry) {
            if (!temp.isEmpty()) {
                char endChar = temp.charAt(temp.length() - 1);

                if (endChar == 'y' || endChar == 'z' || endChar == 'Y' || endChar == 'Z') {
                    counter++;
                }
            }
        }

        return counter;
    }

}
