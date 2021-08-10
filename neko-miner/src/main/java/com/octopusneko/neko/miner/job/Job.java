package com.octopusneko.neko.miner.job;

public abstract class Job implements Runnable {

    private JobKey jobKey;

    protected Job(JobKey jobKey) {
        this.jobKey = jobKey;
    }

    public JobKey getJobKey() {
        return jobKey;
    }

    public void setJobKey(JobKey jobKey) {
        this.jobKey = jobKey;
    }
}
