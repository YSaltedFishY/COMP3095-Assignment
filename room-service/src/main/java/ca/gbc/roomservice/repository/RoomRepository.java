package ca.gbc.roomservice.repository;

import ca.gbc.roomservice.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findAllByCapacityGreaterThanEqual(Integer capacity);

    List<Room> findAllByAvailabilityTrue();

}
