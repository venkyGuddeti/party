# Request Context Implementation

This document explains the implementation of request-scope context for storing user information across the request lifecycle.

## Overview

The implementation provides a thread-safe way to store and access user information (like `X-User-Id`) throughout the entire request processing chain, from controllers through services to repositories.

## Components

### 1. RequestContext (Thread-Local Storage)
- **Location**: `com.finixone.party.context.RequestContext`
- **Purpose**: Stores user ID, request ID, and request timestamp in thread-local storage
- **Key Methods**:
  - `setUserId(String userId)` - Set current user ID
  - `getUserId()` - Get current user ID
  - `setRequestId(String requestId)` - Set current request ID
  - `getRequestId()` - Get current request ID
  - `clear()` - Clean up thread-local data (prevents memory leaks)

### 2. RequestContextInterceptor (HTTP Interceptor)
- **Location**: `com.finixone.party.config.RequestContextInterceptor`
- **Purpose**: Intercepts HTTP requests to extract headers and populate RequestContext
- **Functionality**:
  - Extracts `X-User-Id` from request header
  - Extracts or generates `X-Request-Id` for request tracing
  - Adds `X-Request-Id` to response headers
  - Cleans up thread-local data after request completion

### 3. WebMvcConfig (Configuration)
- **Location**: `com.finixone.party.config.WebMvcConfig`
- **Purpose**: Registers the interceptor for all API endpoints
- **Configuration**:
  - Applies to `/api/**` paths
  - Excludes health check endpoints

### 4. RequestContextUtils (Utility Class)
- **Location**: `com.finixone.party.utils.RequestContextUtils`
- **Purpose**: Provides convenient methods for accessing request context
- **Helper Methods**:
  - `getCurrentUserId()` - Get user ID with "system" fallback
  - `getCurrentUserIdOrDefault(String fallback)` - Get user ID with custom fallback
  - `hasUserContext()` - Check if user context is available
  - `logCurrentContext(String operation)` - Log current context information

## Usage

### In Controllers
```java
@RestController
public class ExampleController {
    
    @PostMapping("/example")
    public ResponseEntity<String> example(@RequestBody ExampleDto data) {
        String userId = RequestContext.getUserId();
        System.out.println("Processing request for user: " + userId);
        // No need for @RequestHeader("X-User-Id") anymore
        return ResponseEntity.ok("Success");
    }
}
```

### In Services
```java
@Service
public class ExampleService {
    
    public void processData(ExampleData data) {
        String userId = RequestContext.getUserId();
        String requestId = RequestContext.getRequestId();
        
        // Use userId for audit logging, data filtering, etc.
        System.out.println("Service processing for User: " + userId + ", Request: " + requestId);
        
        // Set audit fields if they exist in your entities
        // data.setCreatedBy(userId);
        // data.setRequestId(requestId);
    }
}
```

### Using Utility Class
```java
@Service
public class AuditService {
    
    public void logOperation(String operation) {
        // Use utility for consistent logging
        RequestContextUtils.logCurrentContext(operation);
        
        // Use utility for safe access with fallback
        String userId = RequestContextUtils.getCurrentUserIdOrDefault("anonymous");
    }
}
```

## Request Flow

1. **Client Request**: Client sends request with `X-User-Id` header
2. **Interceptor**: `RequestContextInterceptor.preHandle()` extracts headers and sets context
3. **Controller**: Controller methods access context via `RequestContext.getUserId()`
4. **Service Layer**: Services access context for audit logging, data filtering, etc.
5. **Repository Layer**: Can access context for automatic audit field population
6. **Response**: Request completes, `RequestContextInterceptor.afterCompletion()` cleans up

## Benefits

1. **Clean APIs**: No need for `@RequestHeader("X-User-Id")` in every controller method
2. **Consistent Access**: User context available throughout the entire request chain
3. **Thread Safety**: Uses ThreadLocal for proper isolation between concurrent requests
4. **Memory Safe**: Automatic cleanup prevents memory leaks
5. **Request Tracing**: Built-in request ID generation for debugging and monitoring
6. **Audit Support**: Easy access to user information for audit logging

## Configuration

### Headers
- **X-User-Id**: User identifier (required for user context)
- **X-Request-Id**: Request identifier (optional, auto-generated if not provided)

### Interceptor Paths
- **Included**: `/api/**` (all API endpoints)
- **Excluded**: `/api/health`, `/api/actuator/**` (health checks)

## Best Practices

1. **Always Use Utilities**: Use `RequestContextUtils` for consistent access patterns
2. **Handle Missing Context**: Check if context exists before using in non-API contexts
3. **Audit Logging**: Use context information for comprehensive audit trails
4. **Request Tracing**: Leverage request IDs for distributed tracing
5. **Fallback Values**: Always provide sensible defaults for user context

## Migration

### From Header-Based Controllers
```java
// Before
@PostMapping
public Party saveParty(@RequestHeader("X-User-Id") String userId, @RequestBody Party party) {
    System.out.println("User: " + userId);
    return service.save(party);
}

// After
@PostMapping
public Party saveParty(@RequestBody Party party) {
    String userId = RequestContext.getUserId();
    System.out.println("User: " + userId);
    return service.save(party);
}
```

### Service Layer Enhancement
```java
// Add context awareness to existing services
@Service
public class ExistingService {
    
    public Entity save(Entity entity) {
        String userId = RequestContextUtils.getCurrentUserId();
        entity.setCreatedBy(userId);
        entity.setUpdatedBy(userId);
        return repository.save(entity);
    }
}
```

## Testing

### Unit Tests
```java
@Test
public void testWithContext() {
    // Set up context for testing
    RequestContext.setUserId("test-user");
    RequestContext.setRequestId("test-request");
    
    // Test your service
    service.doSomething();
    
    // Clean up
    RequestContext.clear();
}
```

### Integration Tests
```java
@Test
public void testControllerWithHeader() {
    mockMvc.perform(post("/api/example")
            .header("X-User-Id", "test-user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonContent))
            .andExpected(status().isOk());
}
```