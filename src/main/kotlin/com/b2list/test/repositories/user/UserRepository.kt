package com.b2list.test.repositories.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository:JpaRepository<UserModel, Long> {
    fun findByUsername(username:String):Optional<UserModel>
    fun findByCpf(cpf: Long):Optional<UserModel>
}