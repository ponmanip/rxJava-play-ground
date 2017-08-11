package simple;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;
import simple.domain.Command;

import java.util.List;
import java.util.Random;

/** Created by Ponmani Palanisamy on 11/08/17. */
public class ObservableTest {
  @Test
  public void testObservable() throws InterruptedException {
    for (int i = 0; i < 1000; i++) {
      ObservableExample.processItem(new Command(i));
    }

    Thread.sleep(1000000);
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
                          System.out.println(
                              Thread.currentThread().getName() + ": " + j.intValue());
                          return j + "***";
                        }))
        .toList()
        .subscribe(
            list -> {
              for (String s : list) {
                System.out.println(Thread.currentThread().getName() + ": " + s);
              }
            });
    Thread.sleep(20000);
  }

  public void inspect(List<String> list) {}
}
