package simple;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import simple.domain.Command;

import java.util.Random;

/** Created by Ponmani Palanisamy on 11/08/17. */
@Slf4j
public class ObservableTest {
  @Test
  public void testObservable() throws InterruptedException {
    for (int i = 0; i < 1000; i++) {
      ObservableExample.processItem(new Command(i));
    }

    Thread.sleep(1000);
  }

  @Test
  public void flatMapTest() throws InterruptedException {
    Observable<Integer> vals = Observable.range(1, 200);

    vals.flatMap(
            i ->
                Observable.just(i)
                    .subscribeOn(Schedulers.computation())
                    .map(
                        j -> {
                          Thread.sleep(new Random().nextInt(200));
                          log.info(Thread.currentThread().getName() + ": " + j.intValue());
                          return j + "***";
                        }))
        .toList()
        .subscribe(
            list -> {
              for (String s : list) {
                log.info(Thread.currentThread().getName() + ": " + s);
              }
            });
    Thread.sleep(1000);
  }
}
