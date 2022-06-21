package perfios.intern.job;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.IntStream;

import org.hibernate.boot.archive.scan.spi.ScanResult;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.cglib.core.ClassInfo;
import org.springframework.scheduling.quartz.QuartzJobBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DisallowConcurrentExecution
public class FileMoverJob extends QuartzJobBean {
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		JobKey key = context.getJobDetail().getKey();
		System.out.println("File moving Job is being executed");
		System.out.println("File moving Job is being executed");
		System.out.println("File moving Job is being executed");
		System.out.println("File moving Job is being executed");
		System.out.println("File moving Job is being executed");
		System.out.println(key);

		File file=new File("file1.txt");	
		if(file.exists()) {
			boolean success=file.renameTo(new File("movedto/file2.txt"));			
		}
		else {
			File file2=new File("movedto/file2.txt");
			boolean success=file2.renameTo(new File("file1.txt"));
			
		}

	}
}