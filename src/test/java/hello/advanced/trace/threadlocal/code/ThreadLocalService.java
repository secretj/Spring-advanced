package hello.advanced.trace.threadlocal.code;

import lombok.extern.slf4j.Slf4j;

/**
 * ThreadLocal을 사용하면 쓰레드마다 고유의 저장공간을 가져서 구분이 가능하다.
 * 따라서 동시성 문제를 해결할 수 있다.
 * EX) 각 쓰레드마다 창구가 있다고 생각하면 됨.
 */
@Slf4j
public class ThreadLocalService {

    private ThreadLocal<String> nameStore = new ThreadLocal<>();

    public String logic(String name) {
        log.info("저장 name={} -> nameStore={}", name, nameStore.get());
        nameStore.set(name);
        sleep(1000);
        log.info("조회 nameStore={}", nameStore.get());
        return nameStore.get();
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
