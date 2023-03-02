package gr.codehub.sacchon.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * This configuration file is used to enable support for scheduling tasks for our application.
 * We initialise the internal thread managements in order to
 * be able to handle asynchronous potential executions
 * @author Alexandros Ntagkonikos
 * @version 1.0
 * @since 2023-03-01
 */

@Configuration
@EnableScheduling
public class ScheduleConfig {
    @Bean
    public TaskScheduler getTaskSchedule() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(1);
        taskScheduler.setThreadNamePrefix("scheduleBean-");
        return taskScheduler;
    }
}
