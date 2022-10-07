package com.tepestech.bmservice.config

import com.tepestech.bmservice.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.RedirectStrategy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
class WebSecConf(
    @field:Autowired private var passwordConfig: PasswordConfig,
    @field:Autowired private var applicationUserService: UserService
) : WebMvcConfigurer {

    @Autowired
    private lateinit var authenticationSuccessHandler: MyAuthenticationSuccessHandler

    @Autowired
    private lateinit var authenticationFailureHandler: MyAuthenticationFailureHandler


    /**
     * Provide AuthenticationServices to the class
     *
     * @return DaoAuthenticationProvider
     */
    @Bean
    fun ldapAuthenticationManager(): AuthenticationManager {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(passwordConfig.passwordEncoder())
        provider.isHideUserNotFoundExceptions = false
        provider.setUserDetailsService(applicationUserService)
        return ProviderManager(provider)
    }


    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .httpBasic(withDefaults())
            .authenticationManager(ldapAuthenticationManager())
            .authorizeRequests() // 授权配置
            .antMatchers("/*.js").permitAll()
            .antMatchers("/*.css").permitAll()
            .antMatchers("/pw").permitAll()
            .and().formLogin().loginPage("/login")
            .loginProcessingUrl("/login")
            .successHandler(authenticationSuccessHandler) // 处理登录成功
            .failureHandler(authenticationFailureHandler) // 处理登录失败
            .permitAll()
            .and().logout().logoutSuccessUrl("/login").invalidateHttpSession(true)
            .deleteCookies("JSESSIONID").permitAll()
            .and().build()
    }

    @Component
    class MyAuthenticationSuccessHandler : AuthenticationSuccessHandler {
        private val redirectStrategy: RedirectStrategy = DefaultRedirectStrategy()

        @Throws(IOException::class, ServletException::class)
        override fun onAuthenticationSuccess(
            request: HttpServletRequest, response: HttpServletResponse,
            authentication: Authentication
        ) {
            redirectStrategy.sendRedirect(request, response, "/");
        }
    }

    @Component
    class MyAuthenticationFailureHandler : AuthenticationFailureHandler {

        @Throws(IOException::class)
        override fun onAuthenticationFailure(
            request: HttpServletRequest, response: HttpServletResponse,
            exception: AuthenticationException
        ) {
            when (exception) {
                is UsernameNotFoundException -> {
                    1==1
                }
            }
        }
    }
}