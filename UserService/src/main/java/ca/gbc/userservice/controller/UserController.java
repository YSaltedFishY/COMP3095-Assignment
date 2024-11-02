package ca.gbc.userservice.controller;

import ca.gbc.userservice.dto.UserRequest;
import ca.gbc.userservice.dto.UserResponse;
import ca.gbc.userservice.model.User;
import ca.gbc.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest){
        UserResponse createduser= userService.createUser(userRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/user/"+createduser.id());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .body(createduser);


    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAllRooms(){
        return userService.getAllUsers();
    }
    @PutMapping("/{userId}/")
    public ResponseEntity<UserResponse> updateUser(@PathVariable ("userId") Long userId, @RequestBody UserRequest userRequest){
        Long updatedRoomId= userService.updateUser(userId, userRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/user/"+updatedRoomId);

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);

    }
    @DeleteMapping("/{userId}")

    public ResponseEntity<UserResponse> deleteRoom(@PathVariable ("userId") Long userId, @RequestBody UserRequest userRequest){
        userService.deleteUser(userId);


        return new ResponseEntity<>( HttpStatus.NO_CONTENT);

    }



}
