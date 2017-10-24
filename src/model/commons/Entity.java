package model.commons;

import java.io.Serializable;
import java.util.UUID;

/**
 Represents en entity stored in the database.
 @author Castillo Clarence Fitzgerald Gumtang
 @version 1.0
 @since 2017-10-20
 */

public abstract class Entity implements Serializable {

    /**
     * The ID of the entity.
     */
    protected UUID id;

    /**
     * Creates entity with a system-generated ID number.
     */
    public Entity() {
        this.id = UUID.randomUUID();
    }

    /**
     * Gets the ID number of this Entity.
     * @return this Entity's ID number.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the ID number of this Entity.
     * @param id this Entity's current ID number.
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Compares this Entity to another.
     * @param obj the object to compare to.
     * @return true if and only if obj is instance of Entity and has the same ID number.
     */
    @Override
    public boolean equals(Object obj) {
        if ((null == obj) || !(obj instanceof Entity))
            return false;
        Entity entity = (Entity)obj;
        return entity.id == id;
    }

    /**
     * Returns a hash code value for the entity using the ID number.
     * @return a hash code value for the entity using the ID number.
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
