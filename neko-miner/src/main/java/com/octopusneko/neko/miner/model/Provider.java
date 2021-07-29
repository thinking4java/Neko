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

        private long matchId;
        private int code;
        // 0, handicap
        // 1, odds
        // 2, over under
        private int type;

        public ProviderId() {
        }

        public ProviderId(long matchId, int code, int type) {
            this.matchId = matchId;
            this.code = code;
            this.type = type;
        }

        public long getMatchId() {
            return matchId;
        }

        public int getCode() {
            return code;
        }

        public int getType() {
            return type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProviderId that = (ProviderId) o;
            return matchId == that.matchId && code == that.code && type == that.type;
        }

        @Override
        public int hashCode() {
            return Objects.hash(matchId, code, type);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Provider provider = (Provider) o;
        return Objects.equals(providerId, provider.providerId) && Objects.equals(name, provider.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return providerId.getCode() + "->" + name;
    }
}
