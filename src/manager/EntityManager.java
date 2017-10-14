package manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.UUID;
import model.commons.Entity;

public abstract class EntityManager <T extends Entity> implements Serializable {

    protected Hashtable<UUID, T> entities;

    protected EntityManager() {
        this.entities = new Hashtable<UUID, T>();
    }

    public T findById(UUID id) {
        return entities.get(id);
    }

    public ArrayList<T> getList() {
        return new ArrayList<T>(entities.values());
    }
}
