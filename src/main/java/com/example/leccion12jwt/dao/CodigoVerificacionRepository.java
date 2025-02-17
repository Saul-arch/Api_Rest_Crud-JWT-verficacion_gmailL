package com.example.leccion12jwt.dao;

import com.example.leccion12jwt.models.CodigoVerificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodigoVerificacionRepository extends JpaRepository<CodigoVerificacion, Integer> {
    CodigoVerificacion findByEmailAndCodigo(String email, String codigo);
}
