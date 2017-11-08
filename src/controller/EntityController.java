package moblima.controller;

import moblima.model.commons.Entity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.UUID;

/**
 Represents the base class of the entity controllers.
 @version 1.0
 @since 2017-10-20
 */
public abstract class EntityController<T extends Entity> {

    /**
     * The name of the data file.
     */
    public static final String DAT_FILENAME = "moblima.dat";

    /**
     * The array list of entity controllers.
     */
    private static ArrayList<EntityController> controllers = new ArrayList<>();

    /**
     * A hash table with the UUID of the entity as the key and the entity as the value.
     */
    protected Hashtable<UUID, T> entities;

    /**
     * Creates the entity moblima.controller, initializing a hash table for the entity and its UUID.
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

    /**
     * Save the data.
     * @param objectOutputStream The object to be saved.
     * @throws IOException if the file is not found.
     */
    public static void save(ObjectOutputStream objectOutputStream) throws IOException {
        for (EntityController controller : controllers)
            objectOutputStream.writeObject(controller.entities);
        objectOutputStream.close();
    }

    /**
     * Loads the data.
     * @param objectInputStream The data to be loaded.
     * @throws IOException if the file is not found.
     * @throws ClassNotFoundException if the class is not found.
     */
    public static void load(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        for (EntityController controller : controllers)
            controller.loadEntities(objectInputStream);
        objectInputStream.close();
    }

    /**
     * Loads the entity form the data.
     * @param objectInputStream The entities to be loaded.
     * @throws IOException if the file is not found.
     * @throws ClassNotFoundException if the class is not found.
     */
    @SuppressWarnings("unchecked")
    private void loadEntities(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        entities = (Hashtable<UUID, T>) objectInputStream.readObject();
    }
}
