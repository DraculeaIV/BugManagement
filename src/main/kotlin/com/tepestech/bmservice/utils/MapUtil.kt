package com.tepestech.bmservice.utils

import com.tepestech.bmservice.sqldata.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class MapUtil {

    class UserToUserDetail(private val user: User):UserDetails{
        override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
            val authorities  = ArrayList<GrantedAuthority>()
              authorities.add(user.usertype.roleAuthority)
            return authorities
        }

        override fun getPassword(): String {
            return user.password
        }

        override fun getUsername(): String {
            return user.username
        }

        override fun isAccountNonExpired(): Boolean {
            return true
        }

        override fun isAccountNonLocked(): Boolean {
            return true
        }

        override fun isCredentialsNonExpired(): Boolean {
            return true
        }

        override fun isEnabled(): Boolean {
            return true
        }

    }
    companion object{
        fun mapToUserDetail(user: User): UserDetails {
            return UserToUserDetail(user)
        }
    }
}