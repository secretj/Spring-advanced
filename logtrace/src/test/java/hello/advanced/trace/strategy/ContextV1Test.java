package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.strategy.ContextV1;
import hello.advanced.trace.strategy.code.strategy.Strategy;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic1;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 전략 패턴
 * 장점 : 부모가 바뀌어도 영향을 받지 않는다. (상속이 아닌 위임이기때문에)
 */
@Slf4j
public class ContextV1Test {

    @Test
    void strategyV1() {
        StrategyLogic1 strategyLogic1 = new StrategyLogic1();
        ContextV1 contextV1 = new ContextV1(strategyLogic1);
        contextV1.execute();

        StrategyLogic2 strategyLogic2 = new StrategyLogic2();
        ContextV1 contextV2 = new ContextV1(strategyLogic1);
        contextV2.execute();
    }

    @Test
    void strategyV2() {
        Strategy strategyLogic1 = new Strategy() {
            @Override
            public void call() {
                log.info("비지니스 로직1 실행");
            }
        };
        ContextV1 context1 = new ContextV1(strategyLogic1);
        context1.execute();

        Strategy strategyLogic2 = new Strategy() {
            @Override
            public void call() {
                log.info("비지니스 로직2 실행");
            }
        };
        ContextV1 context2 = new ContextV1(strategyLogic2);
        context2.execute();
    }

    @Test
    void strategyV3() {
        ContextV1 context1 = new ContextV1( new Strategy() {
            @Override
            public void call() {
                log.info("비지니스 로직1 실행");
            }
        });
        context1.execute();

        ContextV1 context2 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("비지니스 로직2 실행");
            }
        });
        context2.execute();
    }

    /**
     * 람다로 변경하려면 interface에 메소드가 하나만 있어야 가능
     */
    @Test
    void strategyV4() {
        ContextV1 context1 = new ContextV1(() -> log.info("비지니스 로직1 실행"));
        context1.execute();

        ContextV1 context2 = new ContextV1(() -> log.info("비지니스 로직2 실행"));
        context2.execute();
    }
}
