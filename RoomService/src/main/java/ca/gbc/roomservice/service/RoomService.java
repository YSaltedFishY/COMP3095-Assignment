package ca.gbc.roomservice.service;

import ca.gbc.roomservice.dto.RoomRequest;
import ca.gbc.roomservice.dto.RoomResponse;

import java.util.List;

public interface RoomService {
    RoomResponse createRoom(RoomRequest roomRequest);

    List<RoomResponse> getAllRooms();
    List<RoomResponse> getAvailableRooms();

    Boolean checkRoomAvailability(Long id);

    Long updateRoom(Long id, RoomRequest roomRequest);

    void deleteRoom(Long id);

    void deleteAllRooms();
}
