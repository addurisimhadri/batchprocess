package com.sim.batchprocessing.config;

import java.util.Collections;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import com.sim.batchprocessing.dao.ContentProcessFTPRepository;
import com.sim.batchprocessing.entity.ContentProcessFTP;
import com.sim.batchprocessing.processor.ContentProcessFTPProcessor;
import com.sim.batchprocessing.reader.ContentProcessFTPReader;
import com.sim.batchprocessing.writer.ContentProcessFTPWriter;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	@Autowired
	private ContentProcessFTPProcessor processor;

	@Autowired
	private ContentProcessFTPWriter writer;

	@Autowired(required = true)
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	ContentProcessFTPRepository contentProcessFTPRepository;
	
	@Bean
	public Job informUser(JobBuilderFactory jobBuilderFactory) {
		return this.jobBuilderFactory.get("informUser").start(fetchContFTP()).build();
	}

	private Step fetchContFTP() {
		return stepBuilderFactory.get("fetchTransaction").<ContentProcessFTP, ContentProcessFTP>chunk(10).reader(reader())
				.processor(processor).writer(writer).build();

	}
	
	@Bean(name = "lotteryInfoReader")
    @StepScope
    public RepositoryItemReader<ContentProcessFTP> reader() {
        RepositoryItemReader<ContentProcessFTP> reader = new RepositoryItemReader<>();
        reader.setRepository(contentProcessFTPRepository);
        reader.setMethodName("findData");
        reader.setSort(Collections.singletonMap("uploadTimestamp", Sort.Direction.DESC));
        return reader;
    }
}
