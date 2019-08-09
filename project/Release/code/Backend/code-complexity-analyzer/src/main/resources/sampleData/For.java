
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class TestIF {

    public void netIdcal(int resultArr[], int prefix) {
        int prefix = 12;
        int netArr[ prefix];
        int count, i;
        int netId1 = 0, netId2 = 0, netId3 = 0, netId4 = 0;

        for (count = 0; count < prefix; ++count) {
            netArr[count] = resultArr[count];
        }

        while (i <= 10 && i == 3) {
            System.out.println(i);
            i++;
        }

//        while (i <= 10 && i == 3) {
//            System.out.println(i);
//            i++;
//        }

        /*while (i <= 10 && i == 3) {
            System.out.println(i);
            i++;
        }*/

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

//        for (count = 0; count < prefix; ++count) {
//            if ((count == 0) || (count == 8) || (count == 16) || (count == 24) | (count = 1))
//                i = 7;
//            if (count < 8)
//                netId1 += netArr[count] * (pow(2, i));
//            else if (count < 16)
//                netId2 += netArr[count] * (pow(2, i));
//            else if (count < 24)
//                netId3 += netArr[count] * (pow(2, i));
//            else if (count < 32)
//                netId4 += netArr[count] * (pow(2, i));
//            --i;
//        }

         /*for (count = 0; count < prefix; ++count) {
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
        }*/

        do {
            System.out.println(i);
            i--;
        } while (i > 1 && i == 10);

//        do {
//            System.out.println(i);
//            i--;
//        } while (i > 1 && i == 10);

                /*do {
            System.out.println(i);
            i--;
        } while (i > 1 && i == 10);*/

        printf("Network ID = ");
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

        String test = "        for (count = 1; count <= 4 && count == 1; ++count) {\n" +
                "            if (count ==\n" +
                "                    1)\n" +
                "                printf(\"%d\", netId1);\n" +
                "\n" +
                "            if ((count == 2) && (netId2 != 0))\n" +
                "                printf(\".%d\", netId2);\n" +
                "\n" +
                "            if ((count == 3) && (netId3 != 0))\n" +
                "                printf(\".%d\", netId3);\n" +
                "\n" +
                "            if ((count == 4) && (netId4 != 0) && (netId4 != 0) && (netId4 != 0))\n" +
                "                printf(\".%d\", netId4);\n" +
                "        }"
    }
}