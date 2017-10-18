package model.commons;

import java.io.Serializable;
import java.util.UUID;

public abstract class Entity implements Serializable {

    protected UUID id;
    public Entity() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if ((null == obj) || (obj.getClass() != Entity.class))
            return false;
        Entity entity = (Entity)obj;
        return entity.id == id;
    }
}
