package ca.gbc.userservice.service;
import ca.gbc.userservice.model.UserType;

import ca.gbc.userservice.dto.UserRequest;
import ca.gbc.userservice.dto.UserResponse;
import ca.gbc.userservice.model.User;
import ca.gbc.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;


    @Override
    public UserResponse createUser(UserRequest userRequest) {
        log.debug("Creating a new user {}", userRequest.name());

        User user = User.builder()
                .name(userRequest.name())
                .userType(userRequest.userType())
                .email(userRequest.email())
                .role(userRequest.role())
                .build();
        userRepository.save(user);
        log.debug("Room {} is saved", user.getId());


        return null;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users=userRepository.findAll();
        return users.stream().map(this::mapToUserResponse).toList();
    }

    private  UserResponse mapToUserResponse(User user){
        return new UserResponse(user.getId(),  user.getName(), user.getEmail(), user.getUserType(),user.getRole());
    }
//    @Override
//    @Query(value= "SELECT r from Room r where r.availability=true")
//    public List<RoomResponse> getAvailableRooms(){
//
//    };


    @Override
    public Long updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.getReferenceById(id);

        if(user !=null){
            user.setName(userRequest.name());
            user.setEmail(userRequest.email());
            user.setUserType(userRequest.userType());
            user.setRole(userRequest.role());

            return userRepository.save(user).getId();
        }
        return id;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);

    }
}
