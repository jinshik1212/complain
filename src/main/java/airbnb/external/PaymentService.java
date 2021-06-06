
package airbnb.external;

import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="Payment", url="${prop.room.url}")
public interface PaymentService {

    @RequestMapping(method= RequestMethod.GET, path="/paycheck/chkPayment")
    public boolean chkPayment(@RequestParam("payId") long payId);

}