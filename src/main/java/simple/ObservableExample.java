package simple;

import io.reactivex.Observable;
import io.reactivex.internal.schedulers.ComputationScheduler;
import io.reactivex.internal.schedulers.IoScheduler;
import lombok.extern.slf4j.Slf4j;
import simple.domain.Command;

import java.util.Arrays;

/** Created by Ponmani Palanisamy on 10/08/17. */
@Slf4j
public class ObservableExample {

  public static void processItem(Command command) {
    log.info("command : "+command.getId());
    Observable ob = Observable.just(command)
            .observeOn(new ComputationScheduler());
    if(!command.isSubOne()){
      ob.subscribe(new SubscriberExample());
    }else if(!command.isSubTwo()){
      ob.subscribe(new SubscriberExample2());
    }else{
      command.setPayload(Arrays.asList("Done"));
    }

  }



}
