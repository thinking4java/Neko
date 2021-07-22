package com.octopusneko.neko.miner.service;

import com.octopusneko.neko.miner.model.League;

import java.util.List;

public interface IMatchService {

    List<League> saveAll(List<League> leagues);
}
