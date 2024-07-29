package hello.proxy;

import hello.proxy.config.AppV1config;
import hello.proxy.config.AppV2config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({AppV1config.class, AppV2config.class})
@SpringBootApplication(scanBasePackages = "hello.proxy.app") // 해당 루트 패키지 이하의 모든 빈을 스캔한다.
public class ProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
	}
}
