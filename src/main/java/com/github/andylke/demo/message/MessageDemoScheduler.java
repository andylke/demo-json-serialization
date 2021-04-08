package com.github.andylke.demo.message;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@Component
public class MessageDemoScheduler {

  private static final Logger LOGGER = LoggerFactory.getLogger(MessageDemoScheduler.class);

  @Scheduled(initialDelay = 1000, fixedDelay = 15000)
  public void runDemo() {
    try {
      serializeMessageUsingObjectMapper();
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    serializeMessageUsingGson();
  }

  private void serializeMessageUsingObjectMapper()
      throws JsonMappingException, JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    final String jsonString = "{\"id\":123, \"code\":\"foo\" ,\"text\":\"abc\"}";

    final long beginTime = System.nanoTime();
    for (int index = 0; index < 1000; index++) {
      final Message message = objectMapper.readValue(jsonString, Message.class);
      objectMapper.writeValueAsString(message);
    }
    LOGGER.info(
        "[serializeJsonUsingObjectMapper] elapsed [{}]",
        Duration.ofNanos(System.nanoTime() - beginTime));
  }

  private void serializeMessageUsingGson() {
    Gson gson = new Gson();
    final String jsonString = "{\"id\":123, \"code\":\"foo\" ,\"text\":\"abc\"}";

    final long beginTime = System.nanoTime();
    for (int index = 0; index < 1000; index++) {
      final Message message = gson.fromJson(jsonString, Message.class);
      gson.toJson(message);
    }
    LOGGER.info(
        "[serializeJsonUsingGson] elapsed [{}]", Duration.ofNanos(System.nanoTime() - beginTime));
  }
}
