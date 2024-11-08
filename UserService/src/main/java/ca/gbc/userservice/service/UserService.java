package ca.gbc.userservice.service;

import ca.gbc.userservice.dto.UserRequest;
import ca.gbc.userservice.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest userRequest);

    List<UserResponse> getAllUsers();
    //List<RoomResponse> getAvailableRooms();

//    Boolean checkRoomAvailability(Long id);

    Long updateUser(Long id, UserRequest userRequest);

    void deleteUser(Long id);
}
