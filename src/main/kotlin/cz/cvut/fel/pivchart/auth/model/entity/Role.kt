package cz.cvut.fel.pivchart.auth.model.entity

import cz.cvut.fel.pivchart.auth.model.entity.base.AbstractEntity
import cz.cvut.fel.pivchart.auth.model.enums.RoleType
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "roles")
open class Role : AbstractEntity<Long>(), Serializable {

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    open lateinit var roleType: RoleType

    @ManyToMany(mappedBy = "roles")
    private val users: MutableSet<User> = HashSet()
}