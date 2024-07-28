package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.strategy.ContextV1;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic1;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic2;
import org.junit.jupiter.api.Test;

/**
 * 전략 패턴
 * 장점 : 부모가 바뀌어도 영향을 받지 않는다. (상속이 아닌 위임이기때문에)
 */
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
}
