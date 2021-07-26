package com.octopusneko.neko.miner.parser;

import com.octopusneko.neko.miner.model.Match;
import com.octopusneko.neko.miner.model.Provider;
import com.octopusneko.neko.miner.utils.ParserUtils;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("OverUnderProviderParserImpl")
public class OverUnderProviderParserImpl implements IProviderParser {


    private static final Pattern REGEX = Pattern.compile("showDetail\\( '[0-9]+' ,'([0-9]+)'\\)");


    @Override
    public List<Provider> parse(final Match match, String data) {
        if (ObjectUtils.isEmpty(data)) {
            return Collections.emptyList();
        }
        Elements trs = Jsoup.parse(data).select("table#oTable > tbody > tr[onclick*=\"showDetail\"]");
        List<Provider> providers = new ArrayList<>();
        trs.forEach(tr -> {
            String value = tr.attr("onclick");
            Matcher matcher = REGEX.matcher(value);
            if (matcher.matches()) {
                int providerId = ParserUtils.parseInt(matcher.group(1));
                String providerName = tr.selectFirst("td").text();
                Provider provider = new Provider();
                provider.setProviderId(new Provider.ProviderId(match.getId(), providerId, 2));
                provider.setName(providerName);
                providers.add(provider);
            }
        });
        return providers;
    }
}
