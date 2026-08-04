package com.college.erpBackEnd.controller;

import com.college.erpBackEnd.dto.ResetPasswordRequest;
import com.college.erpBackEnd.dto.UpdateRolesRequest;
import com.college.erpBackEnd.dto.UserResponse;
import com.college.erpBackEnd.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ADMIN')") // every endpoint in this controller is admin-only
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<UserResponse> listUsers(Pageable pageable) {
        return userService.listUsers(pageable);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PatchMapping("/{id}/roles")
    public UserResponse updateRoles(@PathVariable Long id, @RequestBody UpdateRolesRequest request) {
        return userService.updateRoles(id, request.getRoles());
    }

    @PatchMapping("/{id}/enable")
    public UserResponse enableUser(@PathVariable Long id) {
        return userService.setEnabled(id, true);
    }

    @PatchMapping("/{id}/disable")
    public UserResponse disableUser(@PathVariable Long id) {
        return userService.setEnabled(id, false);
    }

    @PatchMapping("/{id}/reset-password")
    public void resetPassword(@PathVariable Long id, @RequestBody ResetPasswordRequest request) {
        userService.resetPassword(id, request.getNewPassword());
    }
}