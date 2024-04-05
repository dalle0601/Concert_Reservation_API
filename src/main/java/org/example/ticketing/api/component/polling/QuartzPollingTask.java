package org.example.ticketing.api.component.polling;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzPollingTask implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("토큰 발급 요청 후 수행되는지 확인 !!");
    }
}
