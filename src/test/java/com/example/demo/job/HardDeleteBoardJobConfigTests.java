package com.example.demo.job;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HardDeleteBoardJobConfigTests {

  @Autowired
  private Job hardDeleteBoardJob;

  @Autowired
  private JobLauncher jobLauncher;

  @Test
  public void run() throws Exception {
    jobLauncher.run(hardDeleteBoardJob, new JobParametersBuilder()
        .addString("createdDate", "2020-01-01")
        .toJobParameters());
  }
}
