package Service;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

public class FilterCommand extends HystrixCommand<Boolean> {
    FilterService filterService = new FilterService();
    String token;
    protected FilterCommand(String token){
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("FilterService"))
        .andCommandKey(HystrixCommandKey.Factory.asKey("FilterCommand")));
        this.token = token;
    }
    @Override
    protected Boolean run() throws NullPointerException {
//        try{
            return filterService.doPreFilter(token);
//        }catch (NullPointerException e){
//            throw new HystrixBadRequestException("Business exception occurred", e);
//        }
    }

    @Override
    protected Boolean getFallback() {
        return false;
    }
}
