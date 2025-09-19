package com.finixone.party.utils;

public class GenericUtils {

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isNullOrEmpty(Object obj) {
        return obj == null || obj.toString().trim().isEmpty();
    }   

    public static boolean isCreateOperation(String operation) {
        return  "create".equalsIgnoreCase(GenericUtils.isNullOrEmpty(operation)?"create":operation);
    }

    public static boolean isUpdateOperation(String operation) {
        return "update".equalsIgnoreCase(operation);
    }

    public static boolean isDeleteOperation(String operation) {
        return "delete".equalsIgnoreCase(operation);
    }

}
