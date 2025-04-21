package com.ticketing.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    public String id;
    public String username;
    public String role;
    public String status;

    // public UserDTO(String id, String username, String role, String status) {
    //     this.id = id;
    //     this.username = username;
    //     this.role = role;
    //     this.status = status;
    // }

    // public String getId() {
    //     return this.id;
    // }

    // public void setId(String id) {
    //     this.id = id;
    // }

    // public String getUsername() {
    //     return this.username;
    // }

    // public void setUsername(String username) {
    //     this.username = username;
    // }

    // public String getRole() {
    //     return this.role;
    // }

    // public void setRole(String role) {
    //     this.role = role;
    // }

    // public String getStatus() {
    //     return this.status;
    // }

    // public void setStatus(String status) {
    //     this.status = status;
    // }
}


