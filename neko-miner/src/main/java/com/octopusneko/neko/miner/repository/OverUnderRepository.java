package com.octopusneko.neko.miner.repository;

import com.octopusneko.neko.miner.model.OverUnder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OverUnderRepository extends JpaRepository<OverUnder, String> {

}
