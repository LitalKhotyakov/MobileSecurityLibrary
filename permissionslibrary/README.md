# Permissions Management Library

## Overview
The **Permissions Management Library** is an Android library designed to simplify and secure user permission handling within applications. It enables dynamic permission management, secure storage, and real-time monitoring, ensuring smooth and reliable access control.

Built using **Room Database** for persistent storage and **SharedPreferences** for secure logging, this library offers a structured approach to permission handling.

---

## Features
✅ **Basic Permission Management** – Verify, assign, and revoke user permissions effortlessly.
✅ **Dynamic Permission Updates** – Modify user permissions at runtime.
✅ **Secure Storage** – Store permissions safely using Room Database.
✅ **Temporary Permissions** – Set time-bound permissions that auto-expire.
✅ **Permission Logging** – Track and log permission-related actions.
✅ **User Management** – Create and manage users along with their permissions.

---

## Integration Guide

### **Step 1: Add Dependencies**
Add the following to your `build.gradle`:
```gradle
dependencies {
   implementation "androidx.room:room-runtime:2.5.2"
   annotationProcessor "androidx.room:room-compiler:2.5.2"
   implementation "androidx.lifecycle:lifecycle-extensions:2.2.0"
}
```

### **Step 2: Initialize the Library**
```java
PermissionManager permissionManager = new PermissionManager(context);
```

### **Step 3: Usage Examples**
#### **Creating a User**
```java
permissionManager.createUser("username", new PermissionManager.UserCreationCallback() {
   @Override
   public void onUserCreated(User user) {
       // Handle user creation
   }

   @Override
   public void onUserAlreadyExists() {
       // Handle existing user
   }
});
```

#### **Assigning Temporary Permission**
```java
long duration = 60000; // 1 minute
permissionManager.assignTemporaryPermission("username", "READ_CONTACTS", duration);
```

#### **Checking User Permissions**
```java
permissionManager.getUserPermissions("username", new PermissionManager.PermissionsCallback() {
   @Override
   public void onPermissionsFetched(List<Permission> permissions) {
       // Handle permissions
   }
});
```

#### **Viewing Permission Logs**
```java
PermissionLog permissionLog = new PermissionLog(context);
Set<String> logs = permissionLog.getLogs();
```

---

## Temporary Permissions Feature
Temporary permissions are automatically revoked after a specified duration, ensuring controlled access.

### **How It Works**
1. Assign a temporary permission with an expiration time.
2. The library stores the permission with an expiration timestamp.
3. Expired permissions are automatically removed.

```java
long duration = 60000; // 1 minute
permissionManager.assignTemporaryPermission("username", "READ_CONTACTS", duration);
```

---

## Project Structure
📁 **Library Module**
- `AppDatabase` – Manages Room Database.
- `Permission` – Represents permissions.
- `User` – Defines user entity.
- `PermissionManager` – Handles permission operations.
- `PermissionLog` – Stores permission logs securely.

📁 **Demo Application**
- `MainActivity` – Navigation for user management, permissions, and logs.
- `UserManagementActivity` – Interface for user creation and permission assignment.
- `PermissionsCheckActivity` – Check user permissions.
- `PermissionLogActivity` – View permission logs.

---

## Conclusion
The **Permissions Management Library** simplifies and secures user permissions in Android apps. With dynamic permission updates, secure storage, and temporary permission support, it ensures reliable access control. 

For any questions, refer to the examples above or contact the maintainers.

---

