package com.octopusneko.neko.miner.repository;

import com.octopusneko.neko.miner.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Provider.ProviderId> {

}
