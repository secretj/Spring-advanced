package hello.proxy.cglib;

import hello.proxy.cglib.code.TimeMethodInterceptor;
import hello.proxy.common.service.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

/**
 * 인터페이스가 없을 때, 동적 프록시 사용은 Cglib를 이용한다.
 */
@Slf4j
public class CglibTest {

    @Test
    void cglib() {
        ConcreteService target = new ConcreteService();

        //Cglib에서 지원하는 프록시 생성 객체
        Enhancer enhancer = new Enhancer();
        //proxy의 부모타입
        enhancer.setSuperclass(ConcreteService.class);
        //메서드 주입
        enhancer.setCallback(new TimeMethodInterceptor(target));
        ConcreteService proxy = (ConcreteService)enhancer.create();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.call();
    }
}
