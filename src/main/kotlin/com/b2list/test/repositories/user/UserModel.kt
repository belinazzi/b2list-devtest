package com.b2list.test.repositories.user

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tests_users")
data class UserModel(
    @Id
    var cpf: Long,

    @Column(name = "username", updatable = false, insertable = true, unique = true, nullable = false, length = 80)
    var username: String,

    @Column(name = "email", nullable = false, insertable = true, updatable = true, length = 260)
    var email: String,

    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 50)
    var name: String,

    @Column(name = "last_name", nullable = false, insertable = true, updatable = true, length = 100)
    var lastName: String,

    @Column(name = "created_at", nullable = false, updatable = false)
    var creationDate:LocalDateTime = LocalDateTime.now()
)