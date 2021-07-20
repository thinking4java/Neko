package com.octopusneko.neko.miner.repository;

import com.octopusneko.neko.miner.model.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends JpaRepository<League, Integer> {

}
