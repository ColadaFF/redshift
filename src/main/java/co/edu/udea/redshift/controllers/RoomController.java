package co.edu.udea.redshift.controllers;

import co.edu.udea.redshift.domain.Room;
import co.edu.udea.redshift.repository.RoomRepository;
import co.edu.udea.redshift.services.RoomServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.StreamUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController(value = "Rooms")
@RequestMapping(value = "/rooms")
@CrossOrigin(origins = "*")
public class RoomController {

    private final RoomRepository roomRepository;
    private final RoomServices roomServices;

    @Autowired
    public RoomController(RoomRepository roomRepository, RoomServices roomServices) {
        this.roomRepository = roomRepository;
        this.roomServices = roomServices;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Room> getRoomsPaginate(@RequestParam(value = "limit", required = false) Integer limit, @RequestParam(value = "skip", required = false) Integer skip) {
        Optional<Integer> limitOptional = Optional.ofNullable(limit);
        Optional<Integer> skipOptional = Optional.ofNullable(skip);
        Pageable page = new PageRequest(skipOptional.orElse(0), limitOptional.orElse(10));
        Page<Room> roomPage = roomRepository.findAll(page);
        return roomPage.getContent();
    }

    @RequestMapping(value = "/{roomId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Room getRoomById(@PathVariable("roomId") String roomId) {
        return roomRepository.findOne(new ObjectId(roomId));
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Room saveRoom(@RequestBody Room roomJson) {
        return roomRepository.insert(roomJson);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Room updateRoom(@RequestBody Room roomJson) {
        return roomServices.updateRoom(roomJson);
    }
}
