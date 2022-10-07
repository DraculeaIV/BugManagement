package com.tepestech.bmservice.sqldata

import com.tepestech.bmservice.config.BugLevel
import com.tepestech.bmservice.config.BugLifeCycle
import com.tepestech.bmservice.config.BugStatus
import org.hibernate.annotations.NotFound
import org.hibernate.annotations.NotFoundAction
import javax.persistence.*


@Entity
class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var gid = 0

    lateinit var groupName: String

    var groupFounderId: Int = 0

    @OneToMany(mappedBy = "usergroup")
    @NotFound(action = NotFoundAction.IGNORE)
    lateinit var groupMembers: List<User>
}