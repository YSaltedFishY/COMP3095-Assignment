package ca.gbc.roomservice.controller;

import ca.gbc.roomservice.dto.RoomRequest;
import ca.gbc.roomservice.dto.RoomResponse;
import ca.gbc.roomservice.model.Room;
import ca.gbc.roomservice.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RoomResponse> createRoom(@RequestBody RoomRequest roomRequest){
        RoomResponse createdroom=roomService.createRoom(roomRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/room/"+createdroom.id());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .body(createdroom);


    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RoomResponse> getAllRooms(){
        return roomService.getAllRooms();
    }
    @GetMapping("/available")
    @ResponseStatus(HttpStatus.OK)
    public List<RoomResponse> getAvailableRooms(){
        return roomService.getAvailableRooms();
    }
    @GetMapping("/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public Boolean getRoomAvailability( @PathVariable ("roomId") Long roomId){
        return roomService.checkRoomAvailability(roomId);
    }





    @PutMapping("/{roomId}")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable ("roomId") Long roomId,@RequestBody RoomRequest roomRequest){
        Long updatedRoomId=roomService.updateRoom(roomId, roomRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/room/"+updatedRoomId);

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);

    }
    @DeleteMapping("/{roomId}")
    public ResponseEntity<RoomResponse> deleteRoom(@PathVariable ("roomId") Long roomId){
        roomService.deleteRoom(roomId);


        return new ResponseEntity<>( HttpStatus.NO_CONTENT);

    }



}
