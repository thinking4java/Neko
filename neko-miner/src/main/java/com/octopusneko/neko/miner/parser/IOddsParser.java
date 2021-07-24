package com.octopusneko.neko.miner.parser;

import com.octopusneko.neko.miner.model.Match;
import com.octopusneko.neko.miner.model.Odds;

import java.util.List;

public interface IOddsParser {

    List<Odds> parse(final Match match, int providerId, String data);

}
