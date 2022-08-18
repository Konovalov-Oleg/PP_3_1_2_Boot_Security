package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String showViewAllUsers(Model model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        return "AllUsers";
    }

    @GetMapping("/user")
    public String showViewUser(Model model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        return "User";
    }

    @GetMapping("/admin/addUser")
    public String showViewAddNewUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("listRoles", userService.getAllRoles());
        return "AddNewUser";
    }

    @PostMapping("/admin/createUser")
    public String addNewUser(User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/edit/{id}")
    public String showViewUpdateUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUserByIdWithoutPassword(id));
        model.addAttribute("listRoles", userService.getAllRoles());
        return "UpdateUser";
    }

    @PatchMapping("/admin/updateUser/{id}")
    public String updateUserById(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/delete/{id}")
    public String deleteUserById(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
