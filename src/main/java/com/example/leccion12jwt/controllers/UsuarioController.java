package com.example.leccion12jwt.controllers;

import com.example.leccion12jwt.dao.DaoUsuario;
import com.example.leccion12jwt.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UsuarioController {
    @Autowired
    DaoUsuario dao;

    @RequestMapping(value = "/allusers", method = RequestMethod.GET)
    public List<Usuario> getAllUsers(@RequestHeader(value = "Authorization") String token){
        return dao.getAllUsers(token);
    }

    @RequestMapping(value = "/searchbyid/{id}", method = RequestMethod.GET)
    public Usuario getUserById(@PathVariable int id){
        return dao.getUserById(id);
    }

    @RequestMapping(value = "deletebyid/{id}", method = RequestMethod.DELETE)
    public void deleteUsuarioById(@ PathVariable int id){
        dao.delteUserById(id);
    }
    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    public void addUser(@RequestBody Usuario user){
        dao.addUser(user);
    }
    @RequestMapping(value = "/verify/{email}/{codigo}", method = RequestMethod.POST)
    public String verificarCodigo(@PathVariable String email, @PathVariable String codigo) {
        return "";
    }

}
