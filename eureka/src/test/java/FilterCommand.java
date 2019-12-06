import com.choy.thriftplus.eureka.test.gen.ExternalService;
import com.netflix.hystrix.*;

public class FilterCommand extends HystrixCommand<Object> {
    FilterService filterService;
    String token;
    protected FilterCommand(String token){
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("FilterService"))
        .andCommandKey(HystrixCommandKey.Factory.asKey("FilterCommand")));
        this.token = token;
    }
    @Override
    protected Object run() throws Exception {
        return filterService.doPreFilter(token);
    }

    @Override
    protected Object getFallback() {
        return "fall back";
    }
}
