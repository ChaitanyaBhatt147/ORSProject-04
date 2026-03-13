package in.co.rays.proj4.bean;

/**
 * RoomBean represents room information within the system.
 * It includes details such as room code, room name,
 * capacity, and room status.
 * This class extends {@link BaseBean} to include common audit fields.
 *
 * author Chaitanya Bhatt
 * @version 1.0
 */
public class RoomBean extends BaseBean {

    /** Unique code of the room. */
    private String roomCode;

    /** Name of the room. */
    private String roomName;

    /** Capacity of the room. */
    private Integer capacity;

    /** Status of the room (Available / Occupied / Maintenance). */
    private String roomStatus;

    /**
     * Gets the room code.
     *
     * @return roomCode
     */
    public String getRoomCode() {
        return roomCode;
    }

    /**
     * Sets the room code.
     *
     * @param roomCode the room code
     */
    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    /**
     * Gets the room name.
     *
     * @return roomName
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Sets the room name.
     *
     * @param roomName the room name
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /**
     * Gets the capacity of the room.
     *
     * @return capacity
     */
    public Integer getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity of the room.
     *
     * @param capacity the capacity
     */
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the room status.
     *
     * @return roomStatus
     */
    public String getRoomStatus() {
        return roomStatus;
    }

    /**
     * Sets the room status.
     *
     * @param roomStatus the room status
     */
    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    /**
     * Returns the unique key (ID) as a string.
     *
     * @return the key
     */
    @Override
    public String getKey() {
        return id + "";
    }

    /**
     * Returns the display value of the room.
     *
     * @return roomName
     */
    @Override
    public String getValue() {
        return roomName;
    }
}