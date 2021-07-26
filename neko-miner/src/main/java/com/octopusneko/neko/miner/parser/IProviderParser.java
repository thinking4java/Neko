package com.octopusneko.neko.miner.parser;

import com.octopusneko.neko.miner.model.Match;
import com.octopusneko.neko.miner.model.Provider;

import java.util.List;

public interface IProviderParser {

    List<Provider> parse(final Match match, String data);

}
