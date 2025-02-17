package com.example.leccion12jwt.dao;

import com.example.leccion12jwt.models.Usuario;

import java.util.List;

public interface DaoUsuario {
    List<Usuario> getAllUsers(String token);
    Usuario getUserById(int id);
    void delteUserById(int id);
    void addUser(Usuario user);
    String login(Usuario user);
}
