package com.example.leccion12jwt.dao;

import com.example.leccion12jwt.Utils.JWTUtil;
import com.example.leccion12jwt.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

 @Transactional
 @Repository
public class DaoImplemetsUsuario implements DaoUsuario{
     @Autowired
     EntityManager entityManager;
     Argon2 argon = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

     @Autowired
     JWTUtil jwtutil;

    @Override
    public List<Usuario> getAllUsers(String token) {
        String quey = "from Usuario";

        if (jwtutil.getKey(token) == null){
            System.out.println("El usuario no tiene activa una session");
            return null;
        }
        return entityManager.createQuery(quey).getResultList();
    }
    @Override
    public Usuario getUserById(int id) {
        return entityManager.find(Usuario.class, id);
    }

    @Override
    public void delteUserById(int id) {
        try{
            if (entityManager.find(Usuario.class, id) != null){
                entityManager.remove(entityManager.find(Usuario.class, id));
                System.out.println("Usuario eliminado");
            }else {
                System.out.println("Usuario no encontrado");
            }
        }catch (Exception e){
            System.out.println("Error al eliminar el usuario");
        }
    }

    @Override
    public void addUser(Usuario user) {
        try {
            String passworduser = user.getPassword();

            //encripto la password
            user.setPassword(argon.hash(1, 1024, 1, passworduser));
            entityManager.persist(user);
            System.out.println("Usuario creado exitosamente");
        }catch (Exception e){
            System.out.println("Error al agregar el usuario");
        }
    }
    @Override
    public String login(Usuario user) {
        //Solo recibimos el gmail y el password

        //primero verificamos que el gmail del usuario exista
        String query = "from Usuario where gmail=: gmail";

        List<Usuario> listUsuario = entityManager.createQuery(query)
                .setParameter("gmail", user.getGmail())
                .getResultList();

        if (listUsuario != null){
            if (argon.verify(listUsuario.get(0).getPassword(), user.getPassword())){
                String token = jwtutil.create(String.valueOf(listUsuario.get(0).getId()), user.getGmail());
                System.out.println("Usuario ah iniciado session correctamente");
                return token;
            }else {
                System.out.println("Usuario o contraseña incorrectos");
            }
        }else{
            System.out.println("Usuario o contraseña incorrectos");
            return null;
        }
        return null;
    }
}
