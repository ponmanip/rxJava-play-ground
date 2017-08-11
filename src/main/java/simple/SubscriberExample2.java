package simple;

import io.reactivex.observers.DefaultObserver;
import lombok.extern.slf4j.Slf4j;
import simple.domain.Command;

/** Created by Ponmani Palanisamy on 11/08/17. */
@Slf4j
public class SubscriberExample2 extends DefaultObserver<Command> {

  @Override
  public void onNext(Command command) {
    log.info("onNext: " + command.getId());
    command.setSubTwo(true);
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
