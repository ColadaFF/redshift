package co.edu.udea.redshift.domain;

import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

@Document(collection = "rooms")
public class Room {
    public enum RoomType {
        @SerializedName("auditorium")
        AUDITORIUM,
        @SerializedName("computers")
        COMPUTERS,
        @SerializedName("drawing")
        DRAWING,
        @SerializedName("special")
        SPECIAL,
        @SerializedName("lab")
        LAB
    }

    @Id
    private String _id;
    @Indexed(unique = true)
    private String number;
    private Integer capacity;
    private String observations;
    private RoomType type;
    private Boolean deleted;

    public Room(String number, Integer capacity, String observations, RoomType type) {
        String numberValNotNull = checkNotNull(number, "Number can't be null");
        Integer capacityValNotNull = checkNotNull(capacity, "Capacity can't be null");
        RoomType typeValNotNull = checkNotNull(type, "Type can't be null");

        if (capacityValNotNull <= 0) {
            throw new IllegalArgumentException("Room capacity can't be negative");
        }

        this.number = numberValNotNull;
        this.capacity = capacityValNotNull;
        this.observations = Optional.ofNullable(observations).orElse("");
        this.type = typeValNotNull;
        this.deleted = Boolean.FALSE;
    }

    public Room() {
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getNumber() {
        return number;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public String getObservations() {
        return observations;
    }

    public RoomType getType() {
        return type;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setObservations(Optional<String> observations) {
        this.observations = observations.orElse("");
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    @Override
    public String toString() {
        return "Room{" +
                "number='" + number + '\'' +
                ", capacity=" + capacity +
                ", observations=" + observations +
                ", type=" + type +
                '}';
    }
}
