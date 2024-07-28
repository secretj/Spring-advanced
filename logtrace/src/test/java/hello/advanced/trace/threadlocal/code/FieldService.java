package hello.advanced.trace.threadlocal.code;

import lombok.extern.slf4j.Slf4j;

/**
 * 여러 쓰레드가 하나의 인스턴스를 참조하게 되면
 * 동시성 문제가 발생할 수 있다.
 * 동시에 호출하면 어떤 쓰레드에서 가지고 있는 값인지 구분을 할 수가 없다.
 */
@Slf4j
public class FieldService {

    private String nameStore;

    public String logic(String name) {
        log.info("저장 name={} -> nameStore={}", name, nameStore);
        nameStore = name;
        sleep(1000);
        log.info("조회 nameStore={}", nameStore);
        return nameStore;
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
