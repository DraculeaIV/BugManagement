package com.tepestech.bmservice.repository

import com.tepestech.bmservice.sqldata.UserGroup
import org.springframework.data.repository.CrudRepository

interface UserGroupRepository : CrudRepository<UserGroup, Int> {
    fun findByGid(id:Int):UserGroup
}