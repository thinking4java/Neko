package com.octopusneko.neko.miner.model;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Embeddable
public class Provider {

    @EmbeddedId
    private ProviderId providerId;

    private String name;

    public ProviderId getProviderId() {
        return providerId;
    }

    public void setProviderId(ProviderId providerId) {
        this.providerId = providerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class ProviderId implements Serializable {

        private final long matchId;
        private final int code;

        public ProviderId(long matchId, int code) {
            this.matchId = matchId;
            this.code = code;
        }

        public long getMatchId() {
            return matchId;
        }

        public int getCode() {
            return code;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Provider provider = (Provider) o;
        return name.equals(provider.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
