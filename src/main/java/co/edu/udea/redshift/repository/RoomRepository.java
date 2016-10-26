package co.edu.udea.redshift.repository;


import co.edu.udea.redshift.domain.Room;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<Room, ObjectId> {
}
