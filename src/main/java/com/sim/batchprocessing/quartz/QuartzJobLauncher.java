package com.sim.batchprocessing.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class QuartzJobLauncher extends QuartzJobBean {
	private static final Logger logger = LoggerFactory.getLogger(QuartzJobLauncher.class);
	static Marker myMarker = MarkerFactory.getMarker("MYMARKER");
	
	private String jobName;
	 private JobLauncher jobLauncher;
	 private JobLocator jobLocator;

	 public String getJobName() {
	  return jobName;
	 }

	 public void setJobName(String jobName) {
	  this.jobName = jobName;
	 }

	 public JobLauncher getJobLauncher() {
	  return jobLauncher;
	 }

	 public void setJobLauncher(JobLauncher jobLauncher) {
	  this.jobLauncher = jobLauncher;
	 }

	 public JobLocator getJobLocator() {
	  return jobLocator;
	 }

	 public void setJobLocator(JobLocator jobLocator) {
	  this.jobLocator = jobLocator;
	 }
	 
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		 JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
		  
		  try {
			 
			  Job job = jobLocator.getJob(jobName);
			  JobExecution jobExecution = jobLauncher.run(job, jobParameters);
			  logger.info(myMarker, "################## Status: {} " , jobExecution.getStatus());
		 
		   
		   
		  } catch(JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException 
		    | JobParametersInvalidException | NoSuchJobException  e) {
			  logger.info(myMarker, "------------------------ Ex::: {} " , e.getMessage());
			  e.printStackTrace();
		  } 

	}

}
