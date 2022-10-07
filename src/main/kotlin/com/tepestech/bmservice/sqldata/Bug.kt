package com.tepestech.bmservice.sqldata

import com.tepestech.bmservice.config.BugLevel
import com.tepestech.bmservice.config.BugLifeCycle
import com.tepestech.bmservice.config.BugStatus
import org.hibernate.annotations.NotFound
import org.hibernate.annotations.NotFoundAction
import javax.persistence.*

@Entity
class Bug {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var bid = 0

    lateinit var bugName: String

    lateinit var bugDescribe: String

    lateinit var recurrenceMethod: String

    lateinit var bugLevel: BugLevel

    lateinit var lifeCycle: BugLifeCycle

    lateinit var bugStatus: BugStatus

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="uid")
    var assignedTo: User? = null

    @OneToMany(mappedBy="did")
    @NotFound(action= NotFoundAction.IGNORE)
     lateinit var changeDetails:List<BugDetail>
}