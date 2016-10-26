package co.edu.udea.redshift.services;

import co.edu.udea.redshift.domain.Room;
import co.edu.udea.redshift.repository.RoomCustomRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@ComponentScan
@Component
public class RoomServices implements RoomCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public RoomServices(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Room updateRoom(Room room) {
        Update update = new Update();
        Optional.ofNullable(room.getCapacity()).ifPresent(integer -> update.set("capacity", integer));
        Optional.ofNullable(room.getNumber()).ifPresent(number -> update.set("number", number));
        Optional.ofNullable(room.getObservations()).ifPresent(value -> update.set("observations", value));
        Optional.ofNullable(room.getType()).ifPresent(value -> update.set("type", value));
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);
        return mongoTemplate.findAndModify(Query.query(where("_id").is(new ObjectId(room.get_id()))), update, options, Room.class);
    }

    @Override
    public Room deleteRoom(Room room) {
        return null;
    }
}
