package com.octopusneko.neko.miner.parser;

import com.octopusneko.neko.miner.model.League;

import java.util.List;

public interface IMatchParser {
    List<League> parse(String data);
}
