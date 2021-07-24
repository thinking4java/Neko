package com.octopusneko.neko.miner.repository;

import com.octopusneko.neko.miner.model.Odds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OddsRepository extends JpaRepository<Odds, String> {

}
