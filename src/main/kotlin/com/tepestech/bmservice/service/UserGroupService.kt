package com.tepestech.bmservice.service

import com.tepestech.bmservice.repository.UserGroupRepository
import com.tepestech.bmservice.sqldata.UserGroup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserGroupService {
    @Autowired
    private lateinit var groupRepository: UserGroupRepository

    fun defaultGroup():UserGroup{
        return groupRepository.findByGid(0)
    }
}