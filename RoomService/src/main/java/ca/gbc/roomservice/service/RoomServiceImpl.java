package ca.gbc.roomservice.service;

import ca.gbc.roomservice.dto.RoomRequest;
import ca.gbc.roomservice.dto.RoomResponse;
import ca.gbc.roomservice.model.Room;
import ca.gbc.roomservice.repository.RoomRepository;
import jakarta.persistence.NamedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{

    private final RoomRepository roomRepository;


    @Override
    public RoomResponse createRoom(RoomRequest roomRequest) {
        log.debug("Creating a new room {}", roomRequest.roomName());

        Room room = Room.builder()
                .roomName(roomRequest.roomName())
                .features(roomRequest.features())
                .availability(roomRequest.availability())
                .capacity(roomRequest.capacity())
                .price(roomRequest.price())
                .build();
        roomRepository.save(room);
        log.debug("Room {} is saved", room.getId());


        return null;
    }

    @Override
    public List<RoomResponse> getAllRooms() {
        List<Room> rooms=roomRepository.findAll();
        return rooms.stream().map(this::mapToRoomResponse).toList();
    }

    @Override
    public List<RoomResponse> getAvailableRooms() {
        List<Room> rooms=roomRepository.findAllByAvailabilityTrue();



        return rooms.stream().map(this::mapToRoomResponse).toList();
    }

    @Override
    public Boolean checkRoomAvailability(Long id) {

        Room room = roomRepository.getReferenceById(id);
        return room.getAvailability();
    }

    private  RoomResponse mapToRoomResponse(Room room){
        return new RoomResponse(room.getId(),  room.getAvailability(), room.getRoomName(), room.getFeatures(),room.getPrice(), room.getCapacity());
    }
//    @Override
//    @Query(value= "SELECT r from Room r where r.availability=true")
//    public List<RoomResponse> getAvailableRooms(){
//
//    };


    @Override
    public Long updateRoom(Long id, RoomRequest roomRequest) {
        Room roomToUpdate = roomRepository.getReferenceById(id);

        if(roomToUpdate !=null){
            roomToUpdate.setRoomName(roomRequest.roomName());
            roomToUpdate.setAvailability(roomRequest.availability());
            roomToUpdate.setPrice(roomRequest.price());
            roomToUpdate.setFeatures(roomRequest.features());
            roomToUpdate.setCapacity(roomRequest.capacity());

            return roomRepository.save(roomToUpdate).getId();
        }
        return id;
    }

    @Override
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);

    }
}
