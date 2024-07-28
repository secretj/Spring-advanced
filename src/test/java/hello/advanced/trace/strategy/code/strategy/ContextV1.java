package hello.advanced.trace.strategy.code.strategy;

import lombok.extern.slf4j.Slf4j;

/**
 * 전략 패턴 : 스프링에서 사용하고 있는 의존관계 방식
 * Context는 변하지 않는 템플릿 역할을 하고 변하는 부분을 Strategy에 위임한다.
 * 필드에 전략을 보관하는 방식
 */
@Slf4j
public class ContextV1 {

    private Strategy strategy;

    public ContextV1(Strategy strategy) {
        this.strategy = strategy;
    }

    public void execute() {
        long startTime = System.currentTimeMillis();
        // 비지니스 로직 실행
        strategy.call(); // 위임
        //비지니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("완료 시간 = {}", resultTime);
    }
}
