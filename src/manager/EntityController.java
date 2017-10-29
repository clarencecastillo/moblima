package manager;

import model.commons.Entity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.UUID;

/**
 Represents the base class of the entity controllers.
 @author Castillo Clarence Fitzgerald Gumtang
 @version 1.0
 @since 2017-10-20
 */
public abstract class EntityController<T extends Entity> {

    // TODO Javadoc
    public static final String DAT_FILENAME = "moblima.dat";

    // TODO Javadoc
    private static ArrayList<EntityController> controllers = new ArrayList<>();

    /**
     * A hash table with the UUID of the entity as the key and the entity as the value.
     */
    protected Hashtable<UUID, T> entities;

    /**
     * Creates the entity controller, initializing a hash table for the entity and its UUID.
     */
    protected EntityController() {
        this.entities = new Hashtable<>();
        controllers.add(this);
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

    // TODO Javadoc
    public static void save(ObjectOutputStream objectOutputStream) throws IOException {
        for (EntityController controller : controllers)
            objectOutputStream.writeObject(controller.entities);
        objectOutputStream.close();
    }

    // TODO Javadoc
    public static void load(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        for (EntityController controller : controllers)
            controller.loadEntities(objectInputStream);
        objectInputStream.close();
    }

    @SuppressWarnings("unchecked")
    private void loadEntities(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        entities = (Hashtable<UUID, T>) objectInputStream.readObject();
    }
}
