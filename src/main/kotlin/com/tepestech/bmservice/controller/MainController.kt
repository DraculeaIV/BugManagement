package com.tepestech.bmservice.controller

import com.tepestech.bmservice.config.PasswordConfig
import com.tepestech.bmservice.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.util.NoSuchElementException


@Controller
class MainController(private val userService: UserService)  {
    @Autowired
    private lateinit var passwordConfig: PasswordConfig

    /**
     * Login page
     * @return - HTML5 page
     */
    @GetMapping("/login")
    fun viewLoginPage(): String {
        return "login"
    }

    @GetMapping(path = ["/pw"])
    @ResponseBody
    fun hashPassword(
        @RequestParam raw: String
    ): String {
        return passwordConfig.passwordEncoder().encode(raw)
    }
    /**
     * Root page
     * @return - HTML5 page
     * @param model
     */
    @GetMapping("/")
    fun homePage(model: Model): String {
        return try {
            val op = userService.loggedUserDetails
            val ud = op.get()
            val c = ud.authorities
            val s = c.stream()
            val o = s.findFirst()
            val ga = o.get()
            val authority = ga.authority
            println(authority)
            model.addAttribute("authority", authority)
            "home"
        }catch (e: NoSuchElementException){
            "login"
        }
    }
}