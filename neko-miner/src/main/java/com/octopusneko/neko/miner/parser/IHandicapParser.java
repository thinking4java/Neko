package com.octopusneko.neko.miner.parser;

import com.octopusneko.neko.miner.model.Handicap;
import com.octopusneko.neko.miner.model.Match;

import java.util.List;

public interface IHandicapParser {

    List<Handicap> parse(final Match match, int providerId, String data);

}
