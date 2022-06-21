package perfios.intern.job;

import java.util.stream.IntStream;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DisallowConcurrentExecution
public class MathsJob extends QuartzJobBean{
    
	@Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Maths job is being executed");
        System.out.println("Maths job is being executed");
        System.out.println("Maths job is being executed");
        System.out.println("Maths job is being executed");
        System.out.println("Maths job is being executed");
        System.out.println("Maths job is being executed");
    }
}
