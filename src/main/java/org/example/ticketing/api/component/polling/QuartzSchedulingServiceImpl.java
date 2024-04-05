package org.example.ticketing.api.component.polling;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

@Component
public class QuartzSchedulingServiceImpl implements QuartzSchedulingService {
    private final Scheduler scheduler;


    public QuartzSchedulingServiceImpl() throws SchedulerException {
        // 스케줄러 초기화
        this.scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
    }

    @Override
    public void schedulePollingTask() {
        try {
            JobDetail job = JobBuilder.newJob(QuartzPollingTask.class)
                    .withIdentity("pollingJob", "ticketing")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("pollingTrigger", "ticketing")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(5)
                            .repeatForever())
                    .build();

            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
