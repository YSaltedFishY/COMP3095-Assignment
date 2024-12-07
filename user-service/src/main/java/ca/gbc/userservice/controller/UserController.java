package ca.gbc.userservice.controller;

import ca.gbc.userservice.dto.UserRequest;
import ca.gbc.userservice.dto.UserResponse;
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
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable ("userId") Long userId, @RequestBody UserRequest userRequest){
        //log.debug("userUpdating");
        Long updatedRoomId= userService.updateUser(userId, userRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/user/"+updatedRoomId);

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);

    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable ("userId") Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);

    }
    @GetMapping("/approve/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Boolean checkIfUserStaff( @PathVariable ("userId") Long userId){
        return userService.checkUserStaff(userId);
    }

    @DeleteMapping("/deleteeverything")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponse> deleteAll(){
        userService.deleteAllUsers();
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

    //User-info event
    @GetMapping("/info/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Boolean getUserInfo(@PathVariable ("id") Long userId){
        return userService.getUserInfo(userId);
    }

}
