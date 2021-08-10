package com.octopusneko.neko.miner.job;

public class JobKey {

    public JobKey() {
    }

    public JobKey(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
