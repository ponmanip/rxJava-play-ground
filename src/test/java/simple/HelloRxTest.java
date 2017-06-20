package simple;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by gyo7 on 27/05/17.
 */
public class HelloRxTest {

    HelloRx helloRx;

    @Before
    public void setup(){
        helloRx = new HelloRx();
    }

    @Test
    public void playWithSingleThread() throws InterruptedException {
        helloRx.operateSerial();
    }

    @Test
    public void playWithMultiThread1() throws InterruptedException {
        helloRx.operateParallel1();
    }

    @Test
    public void playWithMultiThread2() throws InterruptedException {
        helloRx.operateParallel2();
    }

}
