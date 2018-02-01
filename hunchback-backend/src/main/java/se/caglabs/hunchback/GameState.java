package se.caglabs.hunchback;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Named("stateBean")
public class GameState {
  private static GameState instance = new GameState();
  public static GameState getInstance() {
    return instance;
  }

  private enum State {
    RUNNING,
    FINISHED,
    ARMED
  }

  private Long time = 0L;

  private State state = State.FINISHED;


  public void start() {
    if (state == State.ARMED) {
      state = State.RUNNING;
      tick();
    }
  }

  public void arm() {
    state = State.ARMED;
  }

  public void tick() {
    if (state == State.RUNNING) {
      if (System.currentTimeMillis() - time >= 60 * 1000) {
        state = State.FINISHED;
      } else {
        time = System.currentTimeMillis();
      }
    }
  }

  public ObjectNode toJSON() {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode rootNode = mapper.createObjectNode();
    rootNode.put("state", state.name());
    rootNode.put("time",  getTimeInSeconds());
    return rootNode;
  }

  private String getTimeInSeconds() {
    return "" + (System.currentTimeMillis() - time/1000);
  }
}