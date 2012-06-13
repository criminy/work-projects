package us.gaje.efiling.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface Persistable<T extends Persistable<?>> {
    public Class<T> getPersistableClass();
}
