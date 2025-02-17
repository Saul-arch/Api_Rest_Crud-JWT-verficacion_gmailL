package com.example.leccion12jwt.controllers;

import com.example.leccion12jwt.dao.DaoUsuario;
import com.example.leccion12jwt.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/login", method = RequestMethod.POST)
public class AuthController {
    @Autowired
    DaoUsuario dao;

    @RequestMapping(value = "/infouser", method = RequestMethod.POST)
    public String login(@RequestBody Usuario user){
        return dao.login(user);
    }
}
