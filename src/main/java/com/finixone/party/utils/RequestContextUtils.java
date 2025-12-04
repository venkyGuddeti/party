package com.finixone.party.utils;

import com.finixone.party.context.RequestContext;

/**
 * Utility class for request context operations
 */
public class RequestContextUtils {
    
    /**
     * Get current user ID from request context
     * @return user ID or "system" if not available
     */
    public static String getCurrentUserId() {
        String userId = RequestContext.getUserId();
        return userId != null ? userId : "system";
    }
    
    /**
     * Get current request ID from request context
     * @return request ID or null if not available
     */
    public static String getCurrentRequestId() {
        return RequestContext.getRequestId();
    }
    
    /**
     * Check if user context is available
     * @return true if user context exists
     */
    public static boolean hasUserContext() {
        return RequestContext.hasContext() && RequestContext.getUserId() != null;
    }
    
    /**
     * Get user ID with fallback
     * @param fallback fallback value if user ID is not available
     * @return user ID or fallback value
     */
    public static String getCurrentUserIdOrDefault(String fallback) {
        String userId = RequestContext.getUserId();
        return userId != null ? userId : fallback;
    }
    
    /**
     * Log current request context info
     */
    public static void logCurrentContext(String operation) {
        String userId = RequestContext.getUserId();
        String requestId = RequestContext.getRequestId();
        Long timestamp = RequestContext.getRequestTimestamp();
        
        System.out.println(String.format("[%s] User: %s, Request: %s, Timestamp: %d", 
                operation, userId, requestId, timestamp));
    }
}