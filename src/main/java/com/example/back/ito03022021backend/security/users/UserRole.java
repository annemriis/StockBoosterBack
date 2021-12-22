package com.example.back.ito03022021backend.security.users;

public enum UserRole {
    // these are cumulative (admin has user role and guest role)
     USER, ADMIN;

     public boolean isAdmin() {
         return this == ADMIN;
     }

     // spring roles must have role prefix, we have described them in ApplicationRoles.java
    public String toApplicationRole() {
        return "ROLE_" + this.name();
    }

}
