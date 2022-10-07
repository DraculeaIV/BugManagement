package com.tepestech.bmservice.repository

import com.tepestech.bmservice.sqldata.UserGroup
import com.tepestech.bmservice.sqldata.User
import org.springframework.data.repository.CrudRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException


interface UserRepository : CrudRepository<User, Int> {
    fun findByUid(uid:Int): User
    fun findByUsergroup(group: UserGroup): List<User>
    @Throws(UsernameNotFoundException::class)
    fun findByUsername(username:String): User?
}