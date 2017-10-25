package model.commons;

import java.io.Serializable;
import java.util.UUID;

/**
 * Represents an entity with an ID.
 *
 * @author Castillo Clarence Fitzgerald Gumtang
 * @version 1.0
 * @since 2017-10-20
 */

public abstract class Entity implements Serializable {

    /**
     * The ID of this entity.
     */
    protected UUID id;

    /**
     * Creates an entity with a system-generated ID number.
     */
    public Entity() {
        this.id = UUID.randomUUID();
    }

    /**
     * Gets the ID number of this entity.
     *
     * @return This entity's ID number.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Changes the ID number of this entity.
     *
     * @param id This entity's new ID number.
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Compares this entity to another.
     *
     * @param obj The object to compare to.
     * @return true if and only if obj is instance of entity and has the same ID number.
     */
    @Override
    public boolean equals(Object obj) {
        if ((null == obj) || !(obj instanceof Entity))
            return false;
        Entity entity = (Entity) obj;
        return entity.id == id;
    }

    /**
     * Gets a hash code value for this entity using the ID number.
     *
     * @return a hash code value for this entity using the ID number.
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
