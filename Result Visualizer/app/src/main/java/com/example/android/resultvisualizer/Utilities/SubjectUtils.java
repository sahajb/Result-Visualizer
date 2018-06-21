package com.example.android.resultvisualizer.Utilities;

import com.example.android.resultvisualizer.R;

public final class SubjectUtils {
    private static String subCode(String s) {
        switch (s) {
            case "A1":
            case "B1":
                return "MA101";
            case "A2":
            case "B2":
                return "AP101";
            case "A3":
                return "AC101";
            case "A4":
                return "ME101";
            case "A5":
                return "ME103";
            case "A6":
                return "HU101";
            case "A7":
            case "B7":
                return "MA102";
            case "A8":
            case "B8":
                return "AP102";
            case "A9":
                return "EE102";
            case "A10":
                return "CO102";
            case "A11":
                return "ME102";
            case "A12":
                return "EN102";
            case "B3":
                return "EE101";
            case "B4":
                return "CO101";
            case "B5":
                return "ME105";
            case "B6":
                return "EN101";
            case "B9":
                return "AC102";
            case "B10":
                return "ME104";
            case "B11":
                return "ME106";
            case "B12":
                return "HU102";
        }
        return "INV";
    }

    private static String[] subDetails(String s) {
        switch (s) {
            case "MA101":
                return new String[]{"MATHEMATICS - I", "4"};
            case "AP101":
                return new String[]{"PHYSICS – I", "4"};
            case "AC101":
            case "AC102":
                return new String[]{"CHEMISTRY", "4"};
            case "ME101":
            case "ME104":
                return new String[]{"BASIC MECHANICAL ENGINEERING", "4"};
            case "ME103":
            case "ME106":
                return new String[]{"WORKSHOP PRACTICE", "2"};
            case "HU101":
            case "HU102":
                return new String[]{"COMMUNICATION SKILLS", "3"};
            case "EE101":
            case "EE102":
                return new String[]{"BASIC ELECTRICAL ENGINEERING", "4"};
            case "CO101":
            case "CO102":
                return new String[]{"PROGRAMMING FUNDAMENTALS", "4"};
            case "ME105":
            case "ME102":
                return new String[]{"ENGINEERING GRAPHICS", "2"};
            case "EN101":
            case "EN102":
                return new String[]{"ENVIRONMENTAL SCIENCE", "3"};
            case "MA102":
                return new String[]{"MATHEMATICS - II", "4"};
            case "AP102":
                return new String[]{"PHYSICS - II", "4"};
        }
        return new String[]{"A", "4"};
    }

    public static boolean getStatus(String s) {
        switch (s) {
            case "F":
            case "DT":
            case "AB":
            case "RW":
            case "RL":
                return true;
        }
        return false;
    }

    public static int getId(int i) {
        switch (i) {
            case 0:
                return R.id.s1;
            case 1:
                return R.id.c1;
            case 2:
                return R.id.t1;
            case 3:
                return R.id.h1;
            case 4:
                return R.id.s2;
            case 5:
                return R.id.c2;
            case 6:
                return R.id.t2;
            case 7:
                return R.id.h2;
            case 8:
                return R.id.s3;
            case 9:
                return R.id.c3;
            case 10:
                return R.id.t3;
            case 11:
                return R.id.h3;
            case 12:
                return R.id.s4;
            case 13:
                return R.id.c4;
            case 14:
                return R.id.t4;
            case 15:
                return R.id.h4;
            case 16:
                return R.id.s5;
            case 17:
                return R.id.c5;
            case 18:
                return R.id.t5;
            case 19:
                return R.id.h5;
            case 20:
                return R.id.s6;
            case 21:
                return R.id.c6;
            case 22:
                return R.id.t6;
            case 23:
                return R.id.h6;
        }
        return R.id.s1;
    }

    public static String getSubCode(String s) {
        return subCode(s);
    }

    public static String[] getSubDetails(String s) {
        return subDetails(s);
    }
}
