package com.sip.tp.repositories;

import com.sip.tp.entities.Livreur;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivreurRepository extends CrudRepository<Livreur, Long> {

}
