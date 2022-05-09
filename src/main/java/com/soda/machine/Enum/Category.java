package com.soda.machine.Enum;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum Category {
    ;

    public enum Soda {
        COKA("10000"),
        PEPSI("10000"),
        SODA("20000");
        public final String val;

        Soda(String val) {
            this.val = val;
        }

        public static BigInteger getValue(String code) {
            Soda e = Arrays.stream(Soda.values()).filter(o -> o.name().equals(code)).findFirst().orElse(null);
            if (e != null) return new BigInteger(e.val);
            return null;
        }
    }

    public enum VND {
        TEN_THOUSAND("10000"),
        THIRTY_THOUSAND("20000"),
        FIFTY_THOUSAND("50000"),
        ONE_HUNDRED_THOUSAND("100000"),
        TWO_HUNDRED_THOUSAND("200000");
        public final String val;

        VND(String val) {
            this.val = val;
        }

        public static BigInteger getValue(String code) {
            VND e = Arrays.stream(VND.values()).filter(o -> o.name().equals(code)).findFirst().orElse(null);
            if (e != null) return new BigInteger(e.val);
            return null;
        }
        public static String getName(String code) {
            VND e = Arrays.stream(VND.values()).filter(o -> o.val.equals(code)).findFirst().orElse(null);
            if (e != null) return e.name();
            return null;
        }
        public static String getAllValue() {
             return Arrays.stream(VND.values()).map(e -> e.val).collect(Collectors.joining(", "));
        }
    }
}
