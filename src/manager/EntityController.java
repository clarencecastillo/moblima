package manager;

import model.commons.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.UUID;

/**
 Represents the base class of the entity controllers.
 @author Castillo Clarence Fitzgerald Gumtang
 @version 1.0
 @since 2017-10-20
 */
public abstract class EntityController<T extends Entity> {

    /**
     * A hash table with the UUID of the entity as the key and the entity as the value.
     */
    protected Hashtable<UUID, T> entities;

    /**
     * Creates the entity controller, initializing a hash table for the entity and its UUID.
     */
    protected EntityController() {
        this.entities = new Hashtable<>();
    }

    /**
     * Gets the entity of a given UUID.
     * @param id The UUID of then entity to be searched for.
     * @return the entity of this UUID.
     */
    public T findById(UUID id) {
        return entities.get(id);
    }

    /**
     * Gets the list of the entities.
     * @return the list of the entities.
     */
    public ArrayList<T> getList() {
        return new ArrayList<>(entities.values());
    }

    // TODO javadoc
    public Hashtable<UUID, T> getEntities() {
        return entities;
    }

    // TODO javadoc
    public void setEntities(Hashtable<UUID, T> entities) {
        this.entities = entities;
    }
}
