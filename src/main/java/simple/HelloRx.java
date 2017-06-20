package simple;

import io.reactivex.Observable;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;

/** Created by Ponmani Palanisamy on 27/05/17. */
public class HelloRx {

  public void operateSerial() throws InterruptedException {

    io.reactivex.Observable.fromArray("1", "2", "3", "4", "2", "3")
        .flatMap(s -> convertToObservable(s)) //Main thread
        .map(s -> "**" + convertToString(s) + "**")
        .distinct()
        .subscribe(
            s -> converter(s), Functions.ERROR_CONSUMER, () -> System.out.println("Completed"));
  }

  public void operateParallel1() {

    Observable.fromArray("1", "2", "3", "4", "2", "3")
        .subscribeOn(
            Schedulers
                .computation()) //A new thread takes over from Main thread. ven a observeOn would have given us the same functionality
        .flatMap(
            s -> {
              System.out.println(
                  Thread.currentThread()
                      .getName()); //RxComputationThreadPool-1 for all 6 observables
              return Observable.just(s)
                  .map(
                      item -> {
                        System.out.println(
                            "!!"
                                + Thread.currentThread()
                                    .getName()); //RxComputationThreadPool-1. Had we used subscribeOn instead of observeOn as the next operation we would have got RxComputationThreadPool-2, 3, 4, etc. Because observeon is applicable only for the operations downstream. But subscribeon is applicable for all the operations (be it upstream or downstream) for a subscription of an observable.
                        return item.toLowerCase();
                      })
                  .observeOn(Schedulers.computation())
                  .map(
                      item -> {
                        if ("200".equals(item)) {
                          Thread.sleep(100);
                        }
                        ;
                        return "**"
                            + convertToString(item)
                            + "**"; //RxComputationThreadPool-2, RxComputationThreadPool-3, etc.
                      });
            })
        .blockingSubscribe(
            s -> converter(s),
            Functions.ERROR_CONSUMER,
            () ->
                System.out.println(
                    "Completed")); //Main Thread. If we don't do a blockingSubscribe and do a subscribe(), these will be handled by RxComputationThreadPool-2, RxComputationThreadPool-3, etc.

    // Thread.sleep(1000); // This is needed if we don't use blockingSubscribe. Because the Main thread wil be free after creating the Observable from array and will just exit.
  }

  public void operateParallel2() {
    Observable.fromArray("1", "2", "3", "4", "2", "3")
        .flatMap(
            s -> {
              System.out.println(Thread.currentThread().getName()); //Main Thread
              return Observable.just(s)
                  .observeOn(Schedulers.computation())
                  .map(
                      item -> {
                        if ("200".equals(item)) {
                          Thread.sleep(100);
                        }
                        ;
                        return "**"
                            + convertToString(item)
                            + "**"; //RxComputationThreadPool-1, RxComputationThreadPool-2, etc.
                      });
            })
        .subscribe(
            s -> converter(s),
            Functions.ERROR_CONSUMER,
            () ->
                System.out.println(
                    "Completed")); //RxComputationThreadPool-1, RxComputationThreadPool-2, etc.
  }

  public String converter(String s) {
    System.out.println(s + "  : " + Thread.currentThread().getName());
    return "#" + s;
  }

  public Observable<String> convertToObservable(String s) {
    System.out.println(s + "  ^ " + Thread.currentThread().getName());
    return Observable.just(s + s.hashCode());
  }

  public String convertToString(String s) {
    System.out.println(s + "  : " + Thread.currentThread().getName());
    return s + 100;
  }
}
