package com.neo.ccaServices.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

class TestIF {
    private static int time;
    private static int count;
    private static int netID;

    public void printMessage() {
        if ((time < 10) && (time > 10)) {
            System.out.println("Good morning.");
        } else if (time < 20) {
            System.out.println("Good day.");
        } else {
            System.out.println("Good evening.");
        }

        if (count == 1)
            System.out.println("One");
        if ((count == 2) && (netID != 0))
            System.out.println("Two");
        if ((count == 3) && (netID != 0))
            System.out.println("Three");
        if ((count == 4) && (netID != 0) && (netID != 0) && (netID != 0))
            System.out.println("Four");

        int firstInt = 12;
        if ((firstInt & 1) != 0 || ((firstInt | 1) != 0) || (((true))))
            if ((firstInt & 1) > 0)
                if ((firstInt | 1) == 1)
                    System.out.println(count);

        if(true)
            System.out.println("Four");

        //22

//        if ((time < 10) && (time > 10)) {
//            System.out.println("Good morning.");
//        } else if (time < 20) {
//            System.out.println("Good day.");
//        } else {
//            System.out.println("Good evening.");
//        }
//
//        if (count == 1)
//            System.out.println("One");
//        if ((count == 2) && (netID != 0))
//            System.out.println("Two");
//        if ((count == 3) && (netID != 0))
//            System.out.println("Three");
//        if ((count == 4) && (netID != 0) && (netID != 0) && (netID != 0))
//            System.out.println("Four");
//
//        if(true)
//            System.out.println("Four");


        String ifString = "  if ((time < 10) && (time > 10)) {\n" +
                "            System.out.println(\"Good morning.\");\n" +
                "        } else if (time < 20) {\n" +
                "            System.out.println(\"Good day.\");\n" +
                "        } else {\n" +
                "            System.out.println(\"Good evening.\");\n" +
                "        }\n" +
                "\n" +
                "        if (count == 1)\n" +
                "            System.out.println(\"One\");\n" +
                "        if ((count == 2) && (netID != 0))\n" +
                "            System.out.println(\"Two\");\n" +
                "        if ((count == 3) && (netID != 0))\n" +
                "            System.out.println(\"Three\");\n" +
                "        if ((count == 4) && (netID != 0) && (netID != 0) && (netID != 0))\n" +
                "            System.out.println(\"Four\");\n" +
                "\n" +
                "        if(true)\n" +
                "            System.out.println(\"Four\");";
    }
}