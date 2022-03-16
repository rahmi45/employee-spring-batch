package fr.atos.spring.batch.runner;

import fr.atos.spring.batch.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JobRunner {

    private static final Logger logger = LoggerFactory.getLogger(JobRunner.class);

    private JobLauncher simpleJobLauncher;

    private Job jobA;

    public JobRunner(Job jobA, JobLauncher jobLauncher) {
        this.simpleJobLauncher = jobLauncher;
        this.jobA = jobA;
    }

    @Async
    public void runBatchJob() {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        //jobParametersBuilder.addString(Constants.FILE_NAME_CONTEXT_KEY, "employees.csv");
        jobParametersBuilder.addDate("date", new Date(), true);
        runJob(jobA, jobParametersBuilder.toJobParameters());
    }

    public void runJob(Job job, JobParameters parameters) {
        try {
            JobExecution jobExecution = simpleJobLauncher.run(job, parameters);
        } catch (JobExecutionAlreadyRunningException e) {
            logger.info("Job is already running.", parameters.getParameters());
        } catch (JobRestartException e) {
            logger.info("Job was not restarted.", parameters.getParameters());
        } catch (JobInstanceAlreadyCompleteException e) {
            logger.info("Job already completed.", parameters.getParameters());
        } catch (JobParametersInvalidException e) {
            logger.info("Invalid job parameters.", parameters.getParameters());
        }
    }


}
