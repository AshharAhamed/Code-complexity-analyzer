
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

class TestIF {

    public void netIdcal() {
        int count, i = 0, prefix = 10, netId = 121;
        int num1 = 30, num2 = 6, num3 = 0;

        for (count = 0; count < prefix && count == 1; ++count) {
            System.out.println(count);
        }

        while (i <= 10 && i == 3) {
            System.out.println(i);
            i++;
        }

        int firstInt = 2;


        for (count = 0; count < prefix; ++count) {
            System.out.println(i);
            --i;
        }

        do {
            System.out.println(i);
            i--;
        } while (i > 1 && i == 10);

        String test = "        for (count = 0; count < prefix; ++count) {\n" +
                "            System.out.println(count);\n" +
                "        }\n" +
                "\n" +
                "        while (i <= 10 && i == 3) {\n" +
                "            System.out.println(i);\n" +
                "            i++;\n" +
                "        }\n" +
                "\n" +
                "        int firstInt = 2;\n" +
                "\n" +
                "\n" +
                "        for (count = 0; count < prefix; ++count) {\n" +
                "            System.out.println(i);\n" +
                "            --i;\n" +
                "        }\n" +
                "\n" +
                "        do {\n" +
                "            System.out.println(i);\n" +
                "            i--;\n" +
                "        } while (i > 1 && i == 10);";
    }

//    public void netIdcal() {
//        int count, i = 0, prefix = 10, netId = 121;
//        int num1 = 30, num2 = 6, num3 =0;
//
//        for (count = 0; count < prefix; ++count) {
//            System.out.println(count);
//        }
//
//        while (i <= 10 && i == 3) {
//            System.out.println(i);
//            i++;
//        }
//
//        int firstInt = 2;
//
//        if ((firstInt & 1) != 0)
//            if ((firstInt & 1) > 0)
//                if ((firstInt | 1) == 1)
//                    System.out.println(i);
//
//
//        for (count = 0; count < prefix; ++count) {
//            if (((count == 0) || (count == 8) || (count == 16) || (count == 24)))
//                System.out.println(i);
//            if (count < 8 && count == -1 || count == -2)
//                System.out.println(i);
//            else if (((count < 16) && (firstInt | count) == 1) || (firstInt & count) == 0)
//                System.out.println(i);
//            else if (count < 24)
//                System.out.println(i);
//            else if (count < 32)
//                System.out.println(i);
//            --i;
//        }
//
//        do {
//            System.out.println(i);
//            i--;
//        } while (i > 1 && i == 10);
//
//
//        for (count = 1; count <= 4 && count == 1; ++count) {
//            if (count == 1)
//                System.out.println(i);
//
//            if ((count == 2) && (netId != 0))
//                System.out.println(i);
//
//            if ((count == 3) && (netId != 0))
//                System.out.println(i);
//
//            if ((count == 4) && (netId != 0) && (netId != 0) && (netId != 0))
//                System.out.println(i);
//        }
//
//    }
}