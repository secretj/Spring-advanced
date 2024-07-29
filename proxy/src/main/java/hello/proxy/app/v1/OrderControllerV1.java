package hello.proxy.app.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// 인터페이스가 있는 구현 클래스
/**
 * 스프링은 @Controller 또는 @RequestMapping 이 있어야 스프링 컨트롤러로 인식한다.
 * 하지만 @Controller는 자동 컴포넌트 스캔의 대상이 되기 때문에, 수동 Bean 등록을 하려면 @RequestMapping을 사용해야 한다.
 */
@RequestMapping
@ResponseBody
public interface OrderControllerV1 {

    @GetMapping("/v1/request")
    String request(@RequestParam("itemId") String itemId);  //인터페이스에는 @RequestParam을 안 넣어주면 컴파일할 때, 인식 못할때가 있다.

    @GetMapping("v1/no-log")
    String noLog();
}
