package com.example.util;

import com.bihar.land_records.MyApplication;

public class UserUtils {
    public static String getUserId() {
        return MyApplication.getInstance().getIsLogin() ? MyApplication.getInstance().getUserId() : "";
    }
}
