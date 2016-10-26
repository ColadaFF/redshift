package co.edu.udea.redshift.repository;

import co.edu.udea.redshift.domain.Room;

public interface RoomCustomRepository {
    Room updateRoom(Room room);
    Room deleteRoom(Room room);
}
