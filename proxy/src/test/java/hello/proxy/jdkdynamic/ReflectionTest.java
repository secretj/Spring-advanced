package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;

/**
 * 리플렉션 테스트 코드
 */
@Slf4j
public class ReflectionTest {

    @Test
    void reflection() {
        Hello target = new Hello();

        // 공통 로직1 시작
        log.info("start");
        String result1 = target.callA();
        log.info("result = {}", result1);
        // 공통 로직1 종료

        // 공통 로직2 시작
        log.info("start");
        String result2 = target.callB();
        log.info("result = {}", result2);
        // 공통 로직2 종료
    }

    @Test
    void reflection1 () throws Exception {
        //클래스 정보
        Class<?> classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        //callA 메서드 메타 정보
        Method methodCallA = classHello.getMethod("callA");
        Object result1 = methodCallA.invoke(target);
        log.info("result1 ={}", result1);

        //callB 메서드 메타 정보
        Method methodCallB = classHello.getMethod("callB");
        Object result2 = methodCallB.invoke(target);
        log.info("result1 ={}", result2);
    }

    @Test
    void reflection2() throws Exception {
        //클래스 정보
        Class<?> classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        //callA 메서드 메타 정보
        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA, target);

        //callB 메서드 메타 정보
        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);
    }

    // 리플렉션을 이용한 동적 프록시, 메타정보로 호출할 메서드를 동적으로 제공
    // 단, 리플렉션은 런타임에 에러가 발생할 수 있으므로 컴파일 시점에 확인할 수 없다.
    // 따라서 왠만하면 리플렉션은 지양해야함
    private void dynamicCall(Method method, Object target) throws Exception {
        log.info("start");
        Object result = method.invoke(target); // 추상화 시킨 것, 기존 코드는 callA로 직접 들어가 있어서 수정이 안된다.
        log.info("result = {}", result);

    }

    @Slf4j
    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }

        public String callB() {
            log.info("callB");
            return "B";
        }
    }
}
