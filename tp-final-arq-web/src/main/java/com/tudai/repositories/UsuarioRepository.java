package com.tudai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import com.tudai.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}
