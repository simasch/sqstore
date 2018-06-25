package store.common.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Without {@link javax.persistence.MappedSuperclass} JPA does not consider fields from a base class for persistence in a entity sub class
 */
@MappedSuperclass
public class JpaEntity {

    /**
     * Every {@link javax.persistence.Entity} needs an {@link Id} = primary key in the database.
     * The strategy defines how the primary key is generated.
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    protected Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
