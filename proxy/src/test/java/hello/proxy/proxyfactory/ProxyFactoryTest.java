package hello.proxy.proxyfactory;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ConcreteService;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ProxyFactoryTest {

    @Test
    @DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
    void interfaceProxy() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();
        log.info("targetClass={}", target.getClass());      //targetClass=class hello.proxy.common.service.ServiceImpl
        log.info("proxyClass={}", proxy.getClass());        //proxyClass=class jdk.proxy2.$Proxy9

        proxy.save();  // 실제 실행은 여기 (impl내의 함수 호출)

        // AopUtils에서 제공하는 함수들은 proxyFactory를 통해 생성된 프록시에 대해서만 제공됨
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
    }

    @Test
    @DisplayName("구체 클래스만 있으면 CGLIB 사용")
    void concreteProxy() {
        ConcreteService target = new ConcreteService();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        ConcreteService proxy = (ConcreteService)proxyFactory.getProxy();
        log.info("targetClass={}", target.getClass());      //targetClass=class ConcreteService
        log.info("proxyClass={}", proxy.getClass());        //proxyClass=class ConcreteService$$EnhancerBySpringCGLIB$$a75801d4

        proxy.call();  // 실제 실행은 여기

        // AopUtils에서 제공하는 함수들은 proxyFactory를 통해 생성된 프록시에 대해서만 제공됨
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }

    /**
     * 구체클래스를 상속받아서 CGLIB를 사용하도록 설정 - class 기반
     */
    @Test
    @DisplayName("ProxyTargetClass 옵션을 사용하면 인터페이스가 있어도 CGLIB를 사용하고, 클래스 기반 프록시 사용")
    void proxyTargetClass() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); //여기서 설정
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();
        log.info("targetClass={}", target.getClass());      //targetClass=class ServiceImpl
        log.info("proxyClass={}", proxy.getClass());        //proxyClass=class ServiceImpl$$EnhancerBySpringCGLIB$$8a061afd

        proxy.save();  // 실제 실행은 여기 (impl내의 함수 호출)

        // AopUtils에서 제공하는 함수들은 proxyFactory를 통해 생성된 프록시에 대해서만 제공됨
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }

}
