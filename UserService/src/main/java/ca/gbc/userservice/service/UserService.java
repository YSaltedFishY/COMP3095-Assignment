package ca.gbc.userservice.service;

import ca.gbc.userservice.dto.UserResponse;
import ca.gbc.userservice.dto.UserRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {
    UserResponse createUser(UserRequest userRequest);

    List<UserResponse> getAllUsers();


    Long updateUser(Long id, UserRequest userRequest);

    void deleteUser(Long id);
    Boolean checkUserStaff(Long id);
    void deleteAllUsers();

}
