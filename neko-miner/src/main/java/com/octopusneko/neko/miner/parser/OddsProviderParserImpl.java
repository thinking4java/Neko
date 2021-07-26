package com.octopusneko.neko.miner.parser;

import com.octopusneko.neko.miner.model.Match;
import com.octopusneko.neko.miner.model.Provider;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("OddsProviderParserImpl")
public class OddsProviderParserImpl implements IProviderParser {

    private static final Pattern REGEX = Pattern.compile("var hData\\s*=\\s*(.*);");

    @Override
    public List<Provider> parse(Match match, String data) {
        if (ObjectUtils.isEmpty(data)) {
            return Collections.emptyList();
        }
        Elements elements = Jsoup.parse(data).select("script[type=\"text/javascript\"]");
        for (Element element : elements) {
            String html = element.html();
            if (!html.contains("var hData")) {
                continue;
            }
            Matcher matcher = REGEX.matcher(html);
            if (matcher.matches() && matcher.groupCount() >= 1) {
                String jsonArray = matcher.group(1);
                return parseJsonArray(match, jsonArray);
            }
        }
        return Collections.emptyList();
    }

    private List<Provider> parseJsonArray(Match match, String strJsonArray) {
        JSONArray jsonArray = new JSONArray(strJsonArray);
        List<Provider> providers = new ArrayList<>();
        jsonArray.forEach(item -> {
            JSONObject jsonObject = (JSONObject) item;
            Provider provider = new Provider();
            int providerId = jsonObject.getInt("cId");
            provider.setProviderId(new Provider.ProviderId(match.getId(), providerId));
            String name = jsonObject.getString("cn");
            provider.setName(name);
            providers.add(provider);
        });
        return providers;
    }
}
