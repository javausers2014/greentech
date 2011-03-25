// Copyright 2007 Y3 Technologies, All Rights Reserved

package com.gtech.iarc.base.models.core;

import com.gtech.iarc.base.util.DateUtil;

/** <code>UserInfo</code> maintains for now only the user-id .*/ 
public class UserInfo {
    private static ThreadLocal userInfo = new ThreadLocal();
    private static ThreadLocal datePerference = new ThreadLocal() {
        public Object initialValue() {
            return DateUtil.datePattern;
        }
    };
    
    public static void setUserId(String userId) {
        userInfo.set(userId);
    }
    
    public static String getUserId() {
        return (String) userInfo.get();
    }
    
    public static void setDatePattern(String datePattern) {
        if (datePattern != null 
            && datePattern.length() > 0) {
            datePerference.set(datePattern);
        }
    }
    
    public static String getDatePattern() {
        return (String) datePerference.get();
    }
}