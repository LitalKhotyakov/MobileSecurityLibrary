# 📌 Permissions Management Library

## 🔍 Overview
The **Permissions Management Library** is an Android SDK designed to simplify and secure user permission handling. It provides a structured approach to managing permissions, offering **dynamic updates, temporary permissions, secure storage, and logging**.

Built with **Room Database** for persistent storage and **SharedPreferences** for secure logging, this library ensures **smooth and reliable access control** within your application.

<img src="https://raw.githubusercontent.com/LitalKhotyakov/MobileSecurityLibrary/master/pic.png" width="288">

---

## ✨ Features
✅ **Basic Permission Management** – Assign, verify, and revoke user permissions.  
✅ **Dynamic Permission Updates** – Modify user permissions at runtime.  
✅ **Secure Storage** – Store permissions safely using Room Database.  
✅ **Temporary Permissions** – Auto-expiring permissions with time constraints.  
✅ **Permission Logging** – Track and log permission-related actions.  
✅ **User Management** – Create and manage users along with their permissions.  

---

## 📥 Installation

### **Step 1: Add the Library to Your Project**
Add the following dependency to your `settings.gradle`:
```include ':permissionslibrary'
```Then, in your module’s `build.gradle`:
```dependencies {
    implementation project(':permissionslibrary')
}
```
---

## 🚀 Usage Guide

### **🔹 Step 1: Initialize the Library**
```
PermissionManager permissionManager = new PermissionManager(context);
```
### **🔹 Step 2: Creating a User**
```permissionManager.createUser("john_doe", new PermissionManager.UserCreationCallback() {
   @Override
   public void onUserCreated(User user) {
       Log.d("Permissions", "User Created: " + user.getUsername());
   }

   @Override
   public void onUserAlreadyExists() {
       Log.d("Permissions", "User already exists");
   }
});
```
### **🔹 Step 3: Assigning Temporary Permission**
```long duration = 60000; // 1 minute
permissionManager.assignTemporaryPermission("john_doe", "READ_CONTACTS", duration);
```
### **🔹 Step 4: Checking User Permissions**
```permissionManager.getUserPermissions("john_doe", new PermissionManager.PermissionsCallback() {
   @Override
   public void onPermissionsFetched(List<Permission> permissions) {
       for (Permission p : permissions) {
           Log.d("Permissions", "User has permission: " + p.getPermission());
       }
   }
});
```
### **🔹 Step 5: Viewing Permission Logs**
```PermissionLog permissionLog = new PermissionLog(context);
Set<String> logs = permissionLog.getLogs();
for (String log : logs) {
    Log.d("Permission Log", log);
}
```
---

## 🛡️ Temporary Permissions Feature
Temporary permissions allow for **time-limited access control**. The permissions **expire automatically** after a predefined period.

### **🛠️ How It Works**
1. Assign a temporary permission with an expiration time.
2. The library stores the permission with an expiration timestamp.
3. Expired permissions are **automatically revoked**.

```long duration = 60000; // 1 minute
permissionManager.assignTemporaryPermission("john_doe", "READ_CONTACTS", duration);
```
---

## 📂 Project Structure
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
