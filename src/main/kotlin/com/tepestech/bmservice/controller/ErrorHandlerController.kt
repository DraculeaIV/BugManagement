package com.tepestech.bmservice.controller

import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ErrorHandlerController : ErrorController {
    /**
     * Handles /error HTTP.Get requests
     */
    @GetMapping("/error")
    fun errorPage(): String {
        return "errorPage"
    }
}