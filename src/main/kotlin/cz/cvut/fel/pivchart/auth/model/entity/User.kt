package cz.cvut.fel.pivchart.auth.model.entity

import cz.cvut.fel.pivchart.auth.model.entity.base.AbstractEntity
import javax.persistence.*

@Entity
@Table(name = "users")
open class User : AbstractEntity<Long>() {

    @Column(name = "email", unique = true)
    open lateinit var email: String

    @ManyToMany
    @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    open val roles: MutableSet<Role> = HashSet()
}