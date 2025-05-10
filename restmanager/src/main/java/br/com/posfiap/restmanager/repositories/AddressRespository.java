package br.com.posfiap.restmanager.repositories;

import br.com.posfiap.restmanager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AddressRespository  extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
}
