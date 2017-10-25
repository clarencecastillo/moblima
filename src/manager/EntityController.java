package manager;

import model.commons.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.UUID;

public abstract class EntityController<T extends Entity> implements Serializable {

    protected Hashtable<UUID, T> entities;

    protected EntityController() {
        this.entities = new Hashtable<>();
    }

    public T findById(UUID id) {
        return entities.get(id);
    }

    public ArrayList<T> getList() {
        return new ArrayList<>(entities.values());
    }
}
