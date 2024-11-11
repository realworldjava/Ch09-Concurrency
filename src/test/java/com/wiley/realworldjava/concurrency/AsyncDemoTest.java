package com.wiley.realworldjava.concurrency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EnableAsync
class AsyncDemoTest{
  @Mock
  Logger log;
  @InjectMocks
//    @Spy
  AsyncDemo asyncDemo;

  @BeforeEach
  void setUp(){
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testPerformHeadsAndTails(){
    asyncDemo.performHeads("message");
    asyncDemo.performTails("message");
  }


  @Test
  void testIncorrectAsync(){
    asyncDemo.incorrectAsync();
  }

//  @Test
//  void testGetLong() throws ExecutionException, InterruptedException{
//    CompletableFuture<Long> result = asyncDemo.getLong();
//    Assertions.assertEquals(Long.valueOf(1), result.get());
//  }

//  @Test
//  void testUseTaskExecutor(){
//    asyncDemo.useTaskExecutor(null);
//  }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme