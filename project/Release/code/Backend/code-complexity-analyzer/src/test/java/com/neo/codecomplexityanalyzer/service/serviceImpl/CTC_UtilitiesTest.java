package com.neo.codecomplexityanalyzer.service.serviceImpl;

import org.junit.Test;

import static org.junit.Assert.*;

public class CTC_UtilitiesTest {

    @Test
    public void getControlScore() {
        GeneralServiceImpl g1 = new GeneralServiceImpl();
        String t1 = g1.removeDoubleQuotedText("package com.neo.ccaServices.resources;\n" +
                "\n" +
                "import java.io.BufferedReader;\n" +
                "import java.io.IOException;\n" +
                "import java.io.StringReader;\n" +
                "\n" +
                "public class TestIF {\n" +
                "    private static int time;\n" +
                "\n" +
                "    public void printMessage() {\n" +
                "        if (time < 10) {\n" +
                "            System.out.println(\"Good morning.\");\n" +
                "        } else if (time < 20) {\n" +
                "            System.out.println(\"Good day.\");\n" +
                "        } else {\n" +
                "            System.out.println(\"Good evening.\");\n" +
                "        }\n" +
                "//        if (time < 10) {\n" +
                "//            System.out.println(\"Good morning.\");\n" +
                "//        } else if (time < 20) {\n" +
                "//            System.out.println(\"Good day.\");\n" +
                "//        } else {\n" +
                "//            System.out.println(\"Good evening.\");\n" +
                "//        }\n" +
                "/*        if (time < 10) {\n" +
                "            System.out.println(\"Good morning.\");\n" +
                "        } else if (time < 20) {\n" +
                "            System.out.println(\"Good day.\");\n" +
                "        } else {\n" +
                "            System.out.println(\"Good evening.\");\n" +
                "        }*/\n" +
                "    }\n" +
                "\n" +
                "    public void netIdcal(int resultArr[], int prefix) {\n" +
                "        int prefix = 12;\n" +
                "        int netArr[ prefix];\n" +
                "        int count, i;\n" +
                "        int netId1 = 0, netId2 = 0, netId3 = 0, netId4 = 0;\n" +
                "\n" +
                "        for (count = 0; count < prefix; ++count) {\n" +
                "            netArr[count] = resultArr[count];\n" +
                "        }\n" +
                "\n" +
                "        for (count = 0; count < prefix; ++count) {\n" +
                "            if ((count == 0) || (count == 8) || (count == 16) || (count == 24) | (count = 1))\n" +
                "                i = 7;\n" +
                "            if (count < 8)\n" +
                "                netId1 += netArr[count] * (pow(2, i));\n" +
                "            else if (count < 16)\n" +
                "                netId2 += netArr[count] * (pow(2, i));\n" +
                "            else if (count < 24)\n" +
                "                netId3 += netArr[count] * (pow(2, i));\n" +
                "            else if (count < 32)\n" +
                "                netId4 += netArr[count] * (pow(2, i));\n" +
                "            --i;\n" +
                "        }\n" +
                "\n" +
                "        \n" +
                "        String test = \"//        for (count = 0; count < prefix; ++count) {\\n\" +\n" +
                "                \"//            if ((count == 0) || (count == 8) || (count == 16) || (count == 24) | (count = 1))\\n\" +\n" +
                "                \"//                i = 7;\\n\" +\n" +
                "                \"//            if (count < 8)\\n\" +\n" +
                "                \"//                netId1 += netArr[count] * (pow(2, i));\\n\" +\n" +
                "                \"//            else if (count < 16)\\n\" +\n" +
                "                \"//                netId2 += netArr[count] * (pow(2, i));\\n\" +\n" +
                "                \"//            else if (count < 24)\\n\" +\n" +
                "                \"//                netId3 += netArr[count] * (pow(2, i));\\n\" +\n" +
                "                \"//            else if (count < 32)\\n\" +\n" +
                "                \"//                netId4 += netArr[count] * (pow(2, i));\\n\" +\n" +
                "                \"//            --i;\\n\" +\n" +
                "                \"//        }\";\n" +
                "//        for (count = 0; count < prefix; ++count) {\n" +
                "//            if ((count == 0) || (count == 8) || (count == 16) || (count == 24) | (count = 1))\n" +
                "//                i = 7;\n" +
                "//            if (count < 8)\n" +
                "//                netId1 += netArr[count] * (pow(2, i));\n" +
                "//            else if (count < 16)\n" +
                "//                netId2 += netArr[count] * (pow(2, i));\n" +
                "//            else if (count < 24)\n" +
                "//                netId3 += netArr[count] * (pow(2, i));\n" +
                "//            else if (count < 32)\n" +
                "//                netId4 += netArr[count] * (pow(2, i));\n" +
                "//            --i;\n" +
                "//        }\n" +
                "\n" +
                "        printf(\"Netword Address = %d.%d.%d.%d\\n\", netId1, netId2, netId3, netId4);\n" +
                "\n" +
                "        printf(\"Network ID = \");\n" +
                "        for (count = 1; count <= 4 && count == 1; ++count) {\n" +
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
                "        }\n" +
                "        puts(\"\");\n" +
                "    }\n" +
                "}");
        System.out.println(t1);
//        CTCServiceImpl cctUtil = new CTCServiceImpl("C://Student//sampleCodes//Catch.java");
//        int output = cctUtil.getControlScore();
//        assertEquals(20, output);
    }
}