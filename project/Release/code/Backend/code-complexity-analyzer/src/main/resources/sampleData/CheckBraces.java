package com.neo.ccaServices.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class TestIF {
    private static int time;

    public void printMessage() {
        if ((t1 < 10) && (t3 > 10)) {
            System.out.println("Good morning.");
        } else if (time < 20) {
            System.out.println("Good day.");
        } else {
            System.out.println("Good evening.");
        }

        for (count = 1; count <= 4 && count == 1; ++count) {
            if (count ==
                    1)
                printf("%d", netId1);

            if ((count == 2) && (netId2 != 0))
                printf(".%d", netId2);

            if ((count == 3) && (netId3 != 0))
                printf(".%d", netId3);

            if ((count == 4) && (netId4 != 0) && (netId4 != 0) && (netId4 != 0))
                printf(".%d", netId4);
        }

        if (true)
            return true;

        public int findSourceCodeLineCount(String sourceCode) {
            int lineCount = 0;
            BufferedReader sourceCodeTemp = new BufferedReader(new StringReader(sourceCode));

            try {
                while (sourceCodeTemp.readLine() != null) {
                    lineCount++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return lineCount;
        }

        switch (week) {
            case 1:
                day = "Sunday";
                break;
            case 2:
                day = "Monday";
                break;
            case 3:
                day = "Tuesday";
                break;
            case 4:
                day = "Wednesday";
                break;
            case 5:
                day = "Thursday";
                break;
            case 6:
                day = "Friday";
                break;
            case 7:
                day = "Saturday";
                break;
            default:
                day = "Invalid day";
                break;

            System.out.println(day);
        }
    }

}