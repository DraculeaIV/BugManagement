package com.tepestech.bmservice.sqldata

import com.tepestech.bmservice.config.BugLevel
import com.tepestech.bmservice.config.BugLifeCycle
import com.tepestech.bmservice.config.BugStatus
import javax.persistence.*

@Entity
class BugDetail{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var did = 0

    lateinit var changeDetails:String

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="bid")
    var bug:Bug? = null

    var bugName: String? = null

    var bugDescribe: String? = null

    var recurrenceMethod: String? = null

    var bugLevel: BugLevel? = null

    var lifeCycle: BugLifeCycle? = null

    var bugStatus: BugStatus? = null

    var assignedTo:Int = 0
}
