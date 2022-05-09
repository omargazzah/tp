package com.sip.tp.repositories;

import com.sip.tp.entities.Commande;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeRepository extends CrudRepository<Commande, Long> {
}
