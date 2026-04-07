package service;

public class PatternDetector {

    public static String detect(double r) {

        if (r > 0.02) return "SPIKE";
        else if (r < -0.02) return "CRASH";
        else return "STABLE";
    }
}