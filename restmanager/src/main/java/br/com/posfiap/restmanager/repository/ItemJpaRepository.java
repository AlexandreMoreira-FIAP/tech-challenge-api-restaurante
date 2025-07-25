package br.com.posfiap.restmanager.repository;

import br.com.posfiap.restmanager.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface ItemJpaRepository extends JpaRepository<ItemEntity, Long> {

}
