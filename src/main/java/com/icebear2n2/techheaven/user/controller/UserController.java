package com.icebear2n2.techheaven.user.controller;

import com.icebear2n2.techheaven.domain.request.LoginRequest;
import com.icebear2n2.techheaven.domain.request.SignupRequest;
import com.icebear2n2.techheaven.domain.response.UserResponse;
import com.icebear2n2.techheaven.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignupRequest signupRequest) {
        userService.signup(signupRequest);
        return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
    }


    @PostMapping("/login")

    public ResponseEntity<Map<String, String>> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Map<String, String> token = userService.authenticateUser(loginRequest);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse.UserData>> getAll(
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page) {

        PageRequest request = PageRequest.of(page, size);

        return new ResponseEntity<>(userService.getAll(request), HttpStatus.OK);
    }

//    @GetMapping
//    public ResponseEntity<User> getUser(@AuthenticationPrincipal PrincipalDetails principalDetails) {
//        if (principalDetails == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        return ResponseEntity.ok(principalDetails.getUser());
//    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> removeUser(@RequestBody Long userId) {
        userService.removeUser(userId);
        return new ResponseEntity<>("User deleted successfully.", HttpStatus.OK);
    }

}
