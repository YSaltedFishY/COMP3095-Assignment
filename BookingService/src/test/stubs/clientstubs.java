

public class Roomclientstubs {


public boolean isRoomAvailable(Long roomId, LocalDateTime startTime, LocalDateTime endTime) {

        if (roomId == 1L && startTime.isBefore(LocalDateTime.now().plusDays(1))) {
            return false;
        }
        return true;
    }


    public Room getRoomDetails(Long roomId) {
        // Simulate getting room details
        return new Room(roomId, "Conference Room A", 50); // Example room details
    }

    public static class Room {
        private Long id;
        private String name;
        private int capacity;

        public Room(Long id, String name, int capacity) {
            this.id = id;
            this.name = name;
            this.capacity = capacity;
        }

        // Getters
        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getCapacity() {
            return capacity;
        }
    }
}
}