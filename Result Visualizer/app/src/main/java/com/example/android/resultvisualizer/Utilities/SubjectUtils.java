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
            case "ME13":
                return "PE251";
            case "ME14":
                return "ME201";
            case "ME15":
                return "ME203";
            case "ME16":
                return "ME205";
            case "ME17":
                return "ME207";
            case "ME18":
                return "MG201";
            case "EL13":
                return "MA251";
            case "EL14":
                return "EL201";
            case "EL15":
                return "EL203";
            case "EL16":
                return "EL205";
            case "EL17":
                return "EL207";
            case "EL18":
                return "HU201";
            case "EN13":
                return "CE251";
            case "EN14":
                return "EN201";
            case "EN15":
                return "EN203";
            case "EN16":
                return "EN205";
            case "EN17":
                return "EN207";
            case "EN18":
                return "HU201";
            case "AE13":
                return "PE261";
            case "AE14":
                return "AE201";
            case "AE15":
                return "AE203";
            case "AE16":
                return "AE205";
            case "AE17":
                return "AE207";
            case "AE18":
                return "MG201";
            case "PT13":
                return "EC271";
            case "PT14":
                return "PS201";
            case "PT15":
                return "PS203";
            case "PT16":
                return "PS205";
            case "PT17":
                return "PS207";
            case "PT18":
                return "MG201";
            case "MC13":
                return "CS251";
            case "MC14":
                return "MC201";
            case "MC15":
                return "MC203";
            case "MC16":
                return "MC205";
            case "MC17":
                return "MC207";
            case "MC18":
                return "MG201";
            case "EE13":
                return "MA261";
            case "EE14":
                return "EE201";
            case "EE15":
                return "EE203";
            case "EE16":
                return "EE205";
            case "EE17":
                return "EE207";
            case "EE18":
                return "HU201";
            case "IT13":
                return "EC261";
            case "IT14":
                return "IT201";
            case "IT15":
                return "IT203";
            case "IT16":
                return "IT205";
            case "IT17":
                return "IT207";
            case "IT18":
                return "HU201";
            case "EC13":
                return "EE251";
            case "EC14":
                return "EC201";
            case "EC15":
                return "EC203";
            case "EC16":
                return "EC205";
            case "EC17":
                return "EC207";
            case "EC18":
                return "HU201";
            case "BT13":
                return "MC251";
            case "BT14":
                return "BT201";
            case "BT15":
                return "BT203";
            case "BT16":
                return "BT205";
            case "BT17":
                return "BT207";
            case "BT18":
                return "HU201";
            case "CE13":
                return "EC251";
            case "CE14":
                return "CE201";
            case "CE15":
                return "CE203";
            case "CE16":
                return "CE205";
            case "CE17":
                return "CE207";
            case "CE18":
                return "MG203";
            case "PE13":
                return "ME261";
            case "PE14":
                return "PE201";
            case "PE15":
                return "PE203";
            case "PE16":
                return "PE205";
            case "PE17":
                return "PE207";
            case "PE18":
                return "MG201";
            case "CO13":
                return "EC261";
            case "CO14":
                return "CO201";
            case "CO15":
                return "CO203";
            case "CO16":
                return "CO205";
            case "CO17":
                return "CO207";
            case "CO18":
                return "HU201";
            case "EP13":
                return "ME251";
            case "EP14":
                return "EP201";
            case "EP15":
                return "EP203";
            case "EP16":
                return "EP205";
            case "EP17":
                return "EP207";
            case "EP18":
                return "MG201";
            case "SE13":
                return "EC261";
            case "SE14":
                return "SE201";
            case "SE15":
                return "SE203";
            case "SE16":
                return "SE205";
            case "SE17":
                return "SE207";
            case "SE18":
                return "MG201";
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
            case "AE205":
                return new String[]{"MANUFACTURING MACHINES", "4"};
            case "BT201":
                return new String[]{"INTRODUCTION TO BIOTECHNOLOGY", "4"};
            case "AE207":
                return new String[]{"ENGINEERING ANALYSIS AND DESIGN", "4"};
            case "MA251":
                return new String[]{"NUMERICAL AND ENGINEERING OPTIMIZATION METHODS", "4"};
            case "AE201":
                return new String[]{"ENGINEERING MECHANICS", "4"};
            case "ME207":
                return new String[]{"ENGINEERING ANALYSIS AND DESIGN", "4"};
            case "BT207":
                return new String[]{"ENGINEERING ANALYSIS AND DESIGN", "4"};
            case "EP203":
                return new String[]{"MATHEMATICAL PHYSICS", "4"};
            case "MC203":
                return new String[]{"MATHEMATICS-III", "4"};
            case "BT203":
                return new String[]{"BIOCHEMISTRY", "4"};
            case "MC207":
                return new String[]{"ENGINEERING ANALYSIS AND DESIGN", "4"};
            case "CO207":
                return new String[]{"MODELLING AND SIMULATION", "4"};
            case "MC205":
                return new String[]{"PROBABILITY & STATISTICS", "4"};
            case "ME201":
                return new String[]{"MECHANICS OF SOLIDS", "4"};
            case "ME203":
                return new String[]{"THERMAL ENGINEERING- I", "4"};
            case "ME205":
                return new String[]{"MACHINE DRAWING AND SOLID MODELLING", "4"};
            case "MA261":
                return new String[]{"NUMERICAL AND ENGINEERING OPTIMIZATION METHODS", "4"};
            case "EN205":
                return new String[]{"ENVIRONMENTAL CHEMISTRY & MICROBIOLOGY", "4"};
            case "BT205":
                return new String[]{"CHEMICAL ENGINEERING PRINCIPLES", "4"};
            case "SE205":
                return new String[]{"WEB TECHNOLOGY", "4"};
            case "CO201":
                return new String[]{"DATA STRUCTURES", "4"};
            case "EC203":
                return new String[]{"DIGITAL DESIGN-I", "4"};
            case "PE261":
                return new String[]{"QUANTITATIVE TECHNIQUES", "4"};
            case "EC201":
                return new String[]{"ANALOG ELECTRONICS-I", "4"};
            case "EC207":
                return new String[]{"NETWORK ANALYSIS AND DESIGN", "4"};
            case "AE203":
                return new String[]{"THERMODYNAMICS", "4"};
            case "CO205":
                return new String[]{"DISCRETE STRUCTURES", "4"};
            case "EE251":
                return new String[]{"ELECTRONIC INSTRUMENTATION AND MEASUREMENTS", "4"};
            case "EC261":
                return new String[]{"ANALOG ELECTRONICS", "4"};
            case "ME261":
                return new String[]{"KINEMATIC AND DYNAMIC OF MACHINES", "4"};
            case "MG203":
                return new String[]{"FUNDAMENTALS OF MANAGEMENT", "3"};
            case "EL207":
                return new String[]{"ENGINEERING ANALYSIS AND DESIGN", "4"};
            case "CE205":
                return new String[]{"FLUID MECHANICS", "4"};
            case "EL203":
                return new String[]{"ELECTRONIC DEVICES AND CIRCUITS", "4"};
            case "EL201":
                return new String[]{"CIRCUITS AND SYSTEMS", "4"};
            case "PE201":
                return new String[]{"ENGINEERING MATERIALS & METALLURGY", "4"};
            case "MC251":
                return new String[]{"APPLIED MATHEMATICS", "4"};
            case "MC201":
                return new String[]{"DISCRETE MATHEMATICS", "4"};
            case "CE203":
                return new String[]{"ENGINEERING MECHANICS", "4"};
            case "EP201":
                return new String[]{"INTRODUCTION TO COMPUTING", "4"};
            case "EP207":
                return new String[]{"DIGITAL ELECTRONICS", "4"};
            case "EP205":
                return new String[]{"CLASSICAL AND QUANTUM MECHANICS", "4"};
            case "PT203":
                return new String[]{"ELEMENTS OF CHEMICAL ENGG", "4"};
            case "EL205":
                return new String[]{"ELECTROMECHANICAL ENERGY CONVERSION", "4"};
            case "PT201":
                return new String[]{"PRINCIPLES OF POLYMERIZATION", "4"};
            case "IT205":
                return new String[]{"DISCRETE STRUCTURES", "4"};
            case "PT207":
                return new String[]{"ENGINEERING ANALYSIS AND DESIGN", "4"};
            case "CE201":
                return new String[]{"CIVIL ENGINEERING BASICS AND APPLICATIONS", "4"};
            case "MG201":
                return new String[]{"FUNDAMENTALS OF MANAGEMENT", "3"};
            case "PE251":
                return new String[]{"ENGINEERING MATERIALS & METALLURGY", "4"};
            case "CE251":
                return new String[]{"BUILDING MATERIAL & CONSTRUCTION", "4"};
            case "PE203":
                return new String[]{"THERMAL ENGINEERING-I", "4"};
            case "PE205":
                return new String[]{"MANUFACTURING MACHINES", "4"};
            case "PE207":
                return new String[]{"MODELING AND SIMULATION", "4"};
            case "CE207":
                return new String[]{"ENGINEERING ANALYSIS AND DESIGN", "4"};
            case "CO203":
                return new String[]{"OBJECT ORIENTED PROGRAMMING", "4"};
            case "EN207":
                return new String[]{"ENGINEERING ANALYSIS & DESIGN", "4"};
            case "EN201":
                return new String[]{"STRENGTH OF MATERIALS", "4"};
            case "EN203":
                return new String[]{"ENGINEERING & ENVIRONMENTAL SURVEYING", "4"};
            case "PT205":
                return new String[]{"CHEMICAL ENGINEERING THERMODYNAMICS", "4"};
            case "SE203":
                return new String[]{"OBJECT ORIENTED PROGRAMMING", "4"};
            case "EC205":
                return new String[]{"SIGNALS & SYSTEMS", "4"};
            case "EC251":
                return new String[]{"BASIC ELECTRONICS & INSTRUMENTATION", "4"};
            case "EE201":
                return new String[]{"NETWORK ANALYSIS & SYNTHESIS", "4"};
            case "EE203":
                return new String[]{"ELECTRONIC DEVICES AND CIRCUITS", "4"};
            case "EC271":
                return new String[]{"BASIC ELECTRONICS ENGG.", "4"};
            case "EE205":
                return new String[]{"ELECTROMECHANICAL ENERGY CONVERSION", "4"};
            case "SE201":
                return new String[]{"DATA STRUCTURES", "4"};
            case "EE207":
                return new String[]{"ENGINEERING ANALYSIS AND DESIGN", "4"};
            case "ME251":
                return new String[]{"ENGINEERING MECHANICS", "4"};
            case "IT203":
                return new String[]{"OBJECT ORIENTED PROGRAMMING", "4"};
            case "IT201":
                return new String[]{"DATA STRUCTURES", "4"};
            case "HU201":
                return new String[]{"ENGINEERING ECONOMICS", "3"};
            case "IT207":
                return new String[]{"MODELING & SIMULATION", "4"};
            case "CS251":
                return new String[]{"DATA STRUCTURE", "4"};
            case "SE207":
                return new String[]{"MODELLING AND SIMULATION", "4"};
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
