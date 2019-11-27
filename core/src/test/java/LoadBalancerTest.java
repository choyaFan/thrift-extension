import com.choy.thriftplus.core.loadBalancer.DynamicLoadBalancer;
import com.choy.thriftplus.core.loadBalancer.RoundRobinDynamicLoadBalancer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoadBalancerTest {
    @Test
    public void test(){
        DynamicLoadBalancer<String> lb = new RoundRobinDynamicLoadBalancer<>();
        assertEquals(1, lb.add("aaa"));
        assertEquals("aaa", lb.next());
        assertEquals("aaa", lb.next());
        assertEquals(1, lb.size());

        assertEquals(2, lb.add("bbb"));
        assertEquals("aaa", lb.next());
        assertEquals("bbb", lb.next());
        assertEquals(2, lb.size());

        assertEquals(3, lb.add("ccc"));
        assertEquals("bbb", lb.next());
        assertEquals("ccc", lb.next());
        assertEquals(3, lb.size());

        assertEquals(2, lb.remove("bbb"));
        assertEquals("aaa", lb.next());
        assertEquals("ccc", lb.next());
        assertEquals(2, lb.size());
    }


    @Test
    public void testConcurrent1() {
        final DynamicLoadBalancer<String> lb = new RoundRobinDynamicLoadBalancer<>();
        final int[] stat = new int[10000];
        Thread nextThread = new Thread(() -> {
            while (!Thread.interrupted()) {
                String item = lb.next();
                if (item != null)
                    stat[Integer.parseInt(item)] ++;
            }
        });
        Thread addThread = new Thread(() -> {
            for (int i = 0; i < 10000; i ++) {
                lb.add(String.valueOf(i));
            }
            nextThread.interrupt();
        });

        try {
            nextThread.start();
            addThread.start();
            nextThread.join();
            addThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConcurrent2() {
        final DynamicLoadBalancer<String> lb = new RoundRobinDynamicLoadBalancer<>();
        for (int i = 0; i < 10000; i ++)
            lb.add(String.valueOf(i));
        final int[] stat = new int[10000];

        Thread nextThread = new Thread(() -> {
            while (!Thread.interrupted()) {
                String item = lb.next();
                if (item != null)
                    stat[Integer.valueOf(item)] ++;
            }
        });

        Thread removeThread = new Thread(() -> {
            for (int i = 0; i < 10000; i ++) {
                lb.remove(String.valueOf(i));
            }
            nextThread.interrupt();
        });

        try {
            nextThread.start();
            removeThread.start();
            nextThread.join();
            removeThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
