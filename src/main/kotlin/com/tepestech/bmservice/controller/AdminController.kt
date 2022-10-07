package com.tepestech.bmservice.controller

import com.tepestech.bmservice.config.PasswordConfig
import com.tepestech.bmservice.config.UserRole
import com.tepestech.bmservice.service.UserGroupService
import com.tepestech.bmservice.service.UserService
import com.tepestech.bmservice.sqldata.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import java.lang.RuntimeException

@Controller
@RequestMapping("/userManagement")
class AdminController(private val userService: UserService,private val groupService: UserGroupService) {
    @Autowired
    private lateinit var passwordConfig: PasswordConfig
    @RequestMapping("/")
    fun adminHome(model: Model): String {
        model.addAttribute("entities", userService.allUsers())
        return "userManagement"
    }

    @GetMapping(path = ["/add"])
    fun showSaveEmployeeForm(model: Model): String {
        model.addAttribute("user", User("","","",UserRole.MANAGER, groupService.defaultGroup()))
        model.addAttribute("allRoles", userService.addUserRole())
        return "addUser"
    }

    @PostMapping("/add")
    fun saveEmployee(@ModelAttribute("user") user: User, model: Model): String {
        try {
            val userL = userService.findUserByUserName(user.username)
            if (userL!=null)throw RuntimeException("用户已存在")
            user.password = passwordConfig.passwordEncoder().encode(user.password)
            userService.addAUser(user)
        } catch (ex: RuntimeException) {
            model.addAttribute("error", ex.message)
            model.addAttribute("allRoles", userService.addUserRole())
            return "addUser"
        }
        return "redirect:/userManagement/"
    }
//
//    @GetMapping("/delete/{id}")
//    fun deleteEmployee(@PathVariable(value = "id") id: Long, model: Model): String {
//        try {
//            employeeService.deleteEmployeeById(id)
//        } catch (ex: ApplicationException) {
//            model.addAttribute("error", ex.message)
//            return adminHome(model)
//        }
//        return "redirect:/admin/"
//    }
//
//    @GetMapping("/update/{id}")
//    fun showUpdateEmployeeForm(@PathVariable(value = "id") id: Long, model: Model): String {
//        val employee = employeeService.findEmployeeById(id)
//        model.addAttribute("employee", employee)
//        val roleDTO = employeeService.allRoles.stream()
//            .sorted(Comparator.comparing { e:RoleDTO?->e!!.role })
//            .collect(Collectors.toList())
//        model.addAttribute("allRoles", roleDTO)
//        return "updateEmployee"
//    }
//
//    @PostMapping(path = ["/update"])
//    fun updateEmployee(@ModelAttribute("employee") employeeDTO: EmployeeDTO, model: Model): String {
//        try {
//            employeeService.updateEmployee(employeeDTO)
//        } catch (ex: ApplicationException) {
//            model.addAttribute("error", ex.message)
//            val roleDTOSet = employeeService.allRoles.stream()
//                .sorted(Comparator.comparing { e:RoleDTO?->e!!.role })
//                .collect(Collectors.toList())
//            model.addAttribute("allRoles", roleDTOSet)
//            return "updateEmployee"
//        }
//        return "redirect:/admin/"
//    }
}