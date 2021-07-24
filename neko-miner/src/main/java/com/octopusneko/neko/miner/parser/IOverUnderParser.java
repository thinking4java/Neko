package com.octopusneko.neko.miner.parser;

import com.octopusneko.neko.miner.model.Match;
import com.octopusneko.neko.miner.model.OverUnder;

import java.util.List;

public interface IOverUnderParser {

    List<OverUnder> parse(final Match match, int providerId, String data);

}
