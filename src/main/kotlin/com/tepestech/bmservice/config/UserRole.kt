package com.tepestech.bmservice.config

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.GrantedAuthority

enum class UserRole {
    ADMIN, PROGRAMMER, TESTER, MANAGER;

    val roleAuthority: GrantedAuthority
        get() = SimpleGrantedAuthority("ROLE_$name")
}