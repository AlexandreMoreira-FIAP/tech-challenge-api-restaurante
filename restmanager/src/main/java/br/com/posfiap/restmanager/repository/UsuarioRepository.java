package br.com.posfiap.restmanager.repository;

import br.com.posfiap.restmanager.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

}