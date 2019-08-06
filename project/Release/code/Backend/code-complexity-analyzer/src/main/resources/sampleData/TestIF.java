package com.neo.ccaServices.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class TestIF {
    private static int time;

    public void printMessage() {
        if (time < 10) {
            System.out.println("Good morning.");
        } else if (time < 20) {
            System.out.println("Good day.");
        } else {
            System.out.println("Good evening.");
        }
    }

    public void netIdcal(int resultArr[], int prefix) {
        int prefix = 12;
        int netArr[ prefix];
        int count, i;
        int netId1 = 0, netId2 = 0, netId3 = 0, netId4 = 0;

        for (count = 0; count < prefix; ++count) {
            netArr[count] = resultArr[count];
        }

        for (count = 0; count < prefix; ++count) {
            if ((count == 0) || (count == 8) || (count == 16) || (count == 24) | (count = 1))
                i = 7;


            if (count < 8)
                netId1 += netArr[count] * (pow(2, i));
            else if (count < 16)
                netId2 += netArr[count] * (pow(2, i));
            else if (count < 24)
                netId3 += netArr[count] * (pow(2, i));
            else if (count < 32)
                netId4 += netArr[count] * (pow(2, i));
            --i;
        }

        printf("Netword Address = %d.%d.%d.%d\n", netId1, netId2, netId3, netId4);

        printf("Network ID = ");
        for (count = 1; count <= 4; ++count) {
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
        puts("");

    }

}

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


class Day {
    public static void main(String[] args) {
        int week = 4;
        String day;

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
        }
        System.out.println(day);
    }
}