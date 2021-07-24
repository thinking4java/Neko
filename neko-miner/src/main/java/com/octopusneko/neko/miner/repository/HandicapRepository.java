package com.octopusneko.neko.miner.repository;

import com.octopusneko.neko.miner.model.Handicap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HandicapRepository extends JpaRepository<Handicap, String> {

}
