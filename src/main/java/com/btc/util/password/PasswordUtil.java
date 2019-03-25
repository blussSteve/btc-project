package com.btc.util.password;

public class PasswordUtil {

    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static void main(String[] args) {
    	System.out.println(encode("123456"));

    }

    public static String encode(String pwd) {
        return passwordEncoder.encode(pwd);
    }

    public static boolean equals(String pwd, String encodePwd) {
        return passwordEncoder.matches(pwd, encodePwd);
    }
}
