package com.finixone.party.context;

/**
 * Request scope context holder for storing user information
 * and other request-specific data throughout the request lifecycle
 */
public class RequestContext {
    private static final ThreadLocal<RequestContext> context = new ThreadLocal<>();
    
    private String userId;
    private String requestId;
    private Long requestTimestamp;
    
    private RequestContext() {
        this.requestTimestamp = System.currentTimeMillis();
    }
    
    public static RequestContext getInstance() {
        RequestContext ctx = context.get();
        if (ctx == null) {
            ctx = new RequestContext();
            context.set(ctx);
        }
        return ctx;
    }
    
    public static void setUserId(String userId) {
        getInstance().userId = userId;
    }
    
    public static String getUserId() {
        RequestContext ctx = context.get();
        return ctx != null ? ctx.userId : null;
    }
    
    public static void setRequestId(String requestId) {
        getInstance().requestId = requestId;
    }
    
    public static String getRequestId() {
        RequestContext ctx = context.get();
        return ctx != null ? ctx.requestId : null;
    }
    
    public static Long getRequestTimestamp() {
        RequestContext ctx = context.get();
        return ctx != null ? ctx.requestTimestamp : null;
    }
    
    public static void clear() {
        context.remove();
    }
    
    public static boolean hasContext() {
        return context.get() != null;
    }
}