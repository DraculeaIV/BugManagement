package com.tepestech.bmservice.service

import com.tepestech.bmservice.config.UserRole
import com.tepestech.bmservice.repository.UserRepository
import com.tepestech.bmservice.sqldata.User
import com.tepestech.bmservice.utils.MapUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService : UserDetailsService {
    @Autowired
    private lateinit var userRepository: UserRepository

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username) ?: throw UsernameNotFoundException("")
        return MapUtil.mapToUserDetail(user)
    }

    fun allUsers(): List<User> {
        return userRepository.findAll().sortedBy { user -> user.uid.toInt() }
    }

    fun addUserRole():List<UserRole>{
        return listOf(UserRole.MANAGER,UserRole.PROGRAMMER,UserRole.TESTER)
    }

    fun addAUser(user: User) {
        userRepository.save(user)
    }

    fun findUserByUserName(username: String):User?{
        return userRepository.findByUsername(username)
    }


    val loggedUserDetails: Optional<UserDetails>
        get() {
            val loggedUser: Optional<UserDetails>
            val principal = SecurityContextHolder.getContext().authentication.principal
            loggedUser = if (principal is UserDetails) {
                Optional.of(principal)
            } else Optional.empty()
            return loggedUser
        }
}