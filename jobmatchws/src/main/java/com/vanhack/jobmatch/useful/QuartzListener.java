package com.vanhack.jobmatch.useful;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import com.vanhack.jobmatch.jobs.IndexQuartzJob;
import com.vanhack.jobmatch.jobs.SkillsDetectionQuartzJob;

public class QuartzListener implements ServletContextListener {
        Scheduler scheduler = null;

        public void contextInitialized(ServletContextEvent servletContext) {
                System.out.println("Context Initialized");
                
                try {
                        // Setup the Job class and the Job group
                        JobDetail job = newJob(IndexQuartzJob.class).withIdentity(
                                        "CronQuartzJob", "Group").build();

                        JobDetail job2 = newJob(SkillsDetectionQuartzJob.class).withIdentity(
                                "CronQuartzJob2", "Group2").build();
                        
                        // Create a Trigger that fires every 5 minutes.
                        Trigger trigger = newTrigger()
                        .withIdentity("TriggerName", "Group")
                        .withSchedule(CronScheduleBuilder.cronSchedule("0/30 * * * * ?"))
                        .build();

                        Trigger trigger2 = newTrigger()
                                .withIdentity("TriggerName", "Group2")
                                .withSchedule(CronScheduleBuilder.cronSchedule("0/60 * * * * ?"))
                                .build();
                        
                        // Setup the Job and Trigger with Scheduler & schedule jobs
                        scheduler = new StdSchedulerFactory().getScheduler();
                        scheduler.start();
                        scheduler.scheduleJob(job, trigger);
                        scheduler.scheduleJob(job2, trigger2);
                }
                catch (SchedulerException e) {
                        e.printStackTrace();
                }
        }

        public void contextDestroyed(ServletContextEvent servletContext) {
                System.out.println("Context Destroyed");
                try 
                {
                        scheduler.shutdown();
                } 
                catch (SchedulerException e) 
                {
                        e.printStackTrace();
                }
        }

}