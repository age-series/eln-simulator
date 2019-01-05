package mods.eln.common;

/**
 * Units: This class is designed to provide easy formatting of many different types of units (volts, amps, watts, etc)
 *
 * It will make sure that it is within a relevant range, with regards to using commonly used SI names as much as possible.
 */
public class Units {
    public static String volts(double v) {
        return prefix(v) + "V";
    }

    public static String ohms(double o) {
        return prefix(o) + "Ω";
    }

    public static String amps(double i) {
        return prefix(i) + "A";
    }

    public static String watts(double w) {
        return prefix(w) + "W";
    }

    public static String henrys(double h) {
        return prefix(h) + "H";
    }

    public static String farads(double f) {
        return prefix(f) + "F";
    }

    public static String prefix(double in) {
        double inAbs = Math.abs(in);
        if        (inAbs < 0.000001) {
            return "0";
        } else if (inAbs < 0.000999) {
            return String.format("%1.2fµ",in * 1000000);
        } else if (inAbs < 0.00999) {
            return String.format("%1.2fm", in * 1000);
        } else if (inAbs < 0.0999) {
            return String.format("%2.1fm", in * 1000);
        } else if (inAbs < 0.999) {
            return String.format("%3.0fm", in * 1000);
        } else if (inAbs < 9.99) {
            return String.format("%1.2f", in);
        } else if (inAbs < 99.9) {
            return String.format("%2.1f", in);
        } else if (inAbs < 999) {
            return String.format("%3.0f", in);
        } else if (inAbs < 9999) {
            return String.format("%1.2fk", in / 1000.0);
        } else if (inAbs < 99999) {
            return String.format("%2.1fk", in / 1000.0);
        } else if (inAbs < 999999) {
            return String.format("%3.1fk", in / 1000.0);
        } else if (inAbs < 999999999.0) {
            return String.format("%3.1fM", in / 1000000.0);
        } else if (inAbs < 999999999999.0) {
            return String.format("%3.1fG", in / 1000000000.0);
        } else { // if(value < 1000000)
            return String.format("%3.0fWTF", in / 1000.0);
        }
    }

    public static void main(String[] arge) {
        int ret = 0;

        // array of things to test
        double[] vals = {1000000000.0, 900000.0, 1000000.0, 1000.0, 100.0, 10.0, 1.0, 0.1, 0.001, 0.00001, 0.000001, 0.0000001};// last number is 0.1 micro, but should be 0 output.
        // array of same length of the correct formatting for those prefixes. For example, 0.001 should output 1.0m
        String[] results = {"1.0G", "900.0k", "1.0M", "1.00k", "100", "10.0", "1.00", "100m", "1.00m", "10.00µ", "1.00µ", "0"};

        if (vals.length != results.length) {
            System.out.println("The values and results lists are not the same length. Fix the test case in mods.eln.common.Units");
            System.exit(2);
        }

        for (int x = 0; x < vals.length; x++) {
            double val = vals[x];
            String rep = results[x];
            System.out.println(prefix(val));
            System.out.println(rep);
            if (prefix(val).equals(rep)) {
                System.out.println("Passed test " + (x + 1) + " of " + results.length);
            }else{
                System.out.println("FAILED TEST " + (x + 1) + " of " + results.length);
                ret = 1;
            }
        }
        if (ret == 0) {
            System.out.println("ALL TESTS PASSED");
        }else{
            System.out.println("ONE OR MORE TEST CASES FAILED");
        }
        System.exit(ret);
    }
}