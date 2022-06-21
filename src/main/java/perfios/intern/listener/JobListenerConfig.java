package perfios.intern.listener;

import javax.annotation.PostConstruct;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class JobListenerConfig {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    @Autowired MyJobExecutionListener myJobExecutionListener;
    @PostConstruct
    public void addListeners() throws SchedulerException {

        schedulerFactoryBean.getScheduler().
          getListenerManager().addJobListener(myJobExecutionListener);
    }
}