package com.tepestech.bmservice.sqldata

import com.tepestech.bmservice.config.UserRole
import org.hibernate.annotations.NotFound
import org.hibernate.annotations.NotFoundAction
import javax.persistence.*

@Suppress( "unused")
@Entity
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var uid = 0

    lateinit var password: String

    lateinit var username: String

    lateinit var usernickname: String

    lateinit var usertype: UserRole

    @ManyToOne(fetch= FetchType.EAGER, optional = true)
    @JoinColumn(name="groupid")
    lateinit var usergroup: UserGroup

    @OneToMany(mappedBy = "bid")
    lateinit var bugList: List<Bug>


    constructor(
        password: String,
        username: String,
        usernickname: String,
        usertype: UserRole,
        usergroup: UserGroup
    ) {
        this.password = password
        this.username = username
        this.usernickname = usernickname
        this.usertype = usertype
        this.usergroup = usergroup
    }

    constructor()
}