package org.example.cucumber;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class Tes {

    public static void main(String[] args) {
        Set<Integer> set = new TreeSet<>();
        set.add(2);
        set.add(3);
        set.add(1);
        Iterator it = set.iterator();

        while (it.hasNext())
            System.out.print(it.next() + " ");

        for (Integer temp : set) {
            if (temp == 2)
                System.out.println(temp);
        }
    }

}
