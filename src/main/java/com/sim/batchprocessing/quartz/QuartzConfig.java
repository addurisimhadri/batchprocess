package com.sim.batchprocessing.quartz;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfig {
	private static final Logger logger = LoggerFactory.getLogger(QuartzConfig.class);
	static Marker myMarker = MarkerFactory.getMarker("MYMARKER");
	
	
	@Autowired
	 private JobLauncher jobLauncher;
	 
	 @Autowired
	 private JobLocator jobLocator;
	 
	 @Bean
	 public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
	  JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
	  jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
	  
	  return jobRegistryBeanPostProcessor;
	 }
	 
	 @Bean
	 public JobDetailFactoryBean jobDetailFactoryBean() {
	  JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
	  jobDetailFactoryBean.setJobClass(QuartzJobLauncher.class);
	  Map<String, Object> map = new HashMap<>();
	  map.put("jobName", "informUser");
	  map.put("jobLauncher", jobLauncher);
	  map.put("jobLocator", jobLocator);
	  
	  jobDetailFactoryBean.setJobDataAsMap(map);
	  
	  return jobDetailFactoryBean;
	 }
	 
	 @Bean
	 public CronTriggerFactoryBean cronTriggerFactoryBean() {
	  CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
	  cronTriggerFactoryBean.setJobDetail(jobDetailFactoryBean().getObject());
	  //run every 10 seconds
	  cronTriggerFactoryBean.setCronExpression("0 31 */1 * * ? *");
	   logger.info(myMarker, "################## cronTriggerFactoryBean : {}  " ,cronTriggerFactoryBean.getJobDataMap());
	  return cronTriggerFactoryBean;
	 }
	 
	 @Bean
	 public SchedulerFactoryBean schedulerFactoryBean() {
	  SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
	  schedulerFactoryBean.setTriggers(cronTriggerFactoryBean().getObject());
	  
	  return schedulerFactoryBean;
	 }

}
