package hello.proxy.pureproxy.proxy;

import hello.proxy.pureproxy.proxy.code.ProxyPatternClient;
import hello.proxy.pureproxy.proxy.code.RealSubject;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {

    @Test
    void noProxyTest() {
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);
        // proxy가 없으니 캐싱이 되지 않고, 개당 1초씩 소모됨 - 불 필요한 리소스
        client.execute();
        client.execute();
        client.execute();
    }
}
