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
		

//        switch (week) {
//            case 1:
//                day = "Sunday";
//                break;
//            case 2:
//                day = "Monday";
//                break;
//            case 3:
//                day = "Tuesday";
//                break;
//            case 4:
//                day = "Wednesday";
//                break;
//            case 5:
//                day = "Thursday";
//                break;
//            case 6:
//                day = "Friday";
//                break;
//            case 7:
//                day = "Saturday";
//                break;
//            default:
//                day = "Invalid day";
//                break;
//        }

        String switchTest = "switch (week) {\n" +
                "            case 1:\n" +
                "                day = \"Sunday\";\n" +
                "                break;\n" +
                "            case 2:\n" +
                "                day = \"Monday\";\n" +
                "                break;\n" +
                "            case 3:\n" +
                "                day = \"Tuesday\";\n" +
                "                break;\n" +
                "            case 4:\n" +
                "                day = \"Wednesday\";\n" +
                "                break;\n" +
                "            case 5:\n" +
                "                day = \"Thursday\";\n" +
                "                break;\n" +
                "            case 6:\n" +
                "                day = \"Friday\";\n" +
                "                break;\n" +
                "            case 7:\n" +
                "                day = \"Saturday\";\n" +
                "                break;\n" +
                "            default:\n" +
                "                day = \"Invalid day\";\n" +
                "                break;\n" +
                "        }";
        System.out.println(day);
    }
}