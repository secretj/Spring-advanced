package hello.proxy.advisor;

import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

@Slf4j
public class MultiAdvisorTest {


    @Test
    @DisplayName("여러 프록시")
    // client -> proxy2(advisor2) -> proxy1(advisor1) -> target
    void multiAdvisorTest1() {
        // 프록시 1 생성
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advisor1());
        proxyFactory.addAdvisor(advisor1);
        ServiceInterface proxy1 = (ServiceInterface)proxyFactory.getProxy();

        // 프록시 2 생성
        ProxyFactory proxyFactory2 = new ProxyFactory(proxy1);       // proxy1을 target으로 설정
        DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advisor2());
        proxyFactory2.addAdvisor(advisor2);
        ServiceInterface proxy2 = (ServiceInterface)proxyFactory2.getProxy();

        proxy2.save(); //  Advisor2 호출 -> Advisor1 호출 -> save 호출
    }

    @Test
    @DisplayName("하나의 프록시, 여러 어드바이저")
        // client -> proxy -> advisor2 -> advisor1 -> target
    void multiAdvisorTest2() {
        // 어드바이저 1,2 생성
        DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advisor1());
        DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advisor2());

        // 프록시 생성 (하나의 프록시에 여러 가지 부가기능의 어드바이저들을 등록할 수 있다.) - 여러개의 AOP가 동시에 적용되어도 target마다 하나의 프록시만 생성한다.
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        // 등록하는 순서대로 호출된다.
        proxyFactory.addAdvisor(advisor2);
        proxyFactory.addAdvisor(advisor1);
        ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();

        proxy.save(); //  Advisor2 호출 -> Advisor1 호출 -> save 호출
    }

    static class Advisor1 implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("Advisor1 호출");
            return invocation.proceed();
        }
    }

    static class Advisor2 implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("Advisor2 호출");
            return invocation.proceed();
        }
    }
}
