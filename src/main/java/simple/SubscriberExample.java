package simple;

import io.reactivex.observers.DefaultObserver;
import lombok.extern.slf4j.Slf4j;
import simple.domain.Command;

import java.util.Random;

/** Created by Ponmani Palanisamy on 10/08/17. */
@Slf4j
public class SubscriberExample extends DefaultObserver<Command> {

  @Override
  public void onNext(Command command) {
    log.info("***** onNext: " + command.getId());
    command.setSubOne(true);
    try {
      Thread.sleep(new Random().nextInt(200));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    ObservableExample.processItem(command);
  }

  @Override
  public void onError(Throwable throwable) {
    log.info("onError: " + throwable.getMessage());
  }

  @Override
  public void onComplete() {
    log.info("completed");
  }
}
