package io.renren.modules.job.task;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import io.renren.modules.thr.entity.ThrCurrencyEntity;
import io.renren.modules.thr.service.ThrCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component("ExchangeRateRefreshTask")
public class ExchangeRateRefreshTask implements ITask {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ThrCurrencyService currencyService;

    private final static String BASE_URL = "http://web.juhe.cn:8080/";
    private final static String PART_URL = "finance/exchange/rmbquot";
    private final static String APP_KEY = "0201dc16c37bffbe472075dd03cde73d";

    @Override
    public void run(String params) {
        try {
            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append(BASE_URL);
            urlBuilder.append(PART_URL);
            urlBuilder.append("?type=1");
            urlBuilder.append("&key=");
            urlBuilder.append(APP_KEY);
            ResponseEntity<String> results = restTemplate.exchange(urlBuilder.toString(), HttpMethod.GET, null, String.class);

            String json = results.getBody();
            RMBQuot rmbQuot = new Gson().fromJson(json, RMBQuot.class);
            if ("0".equals(rmbQuot.errorCode) && rmbQuot.result != null && !rmbQuot.result.isEmpty()) {
                List<ThrCurrencyEntity> currencyList = currencyService.list();
                Map<String, RMBQuotItem> rmbQuotMap = rmbQuot.result.get(0);

                for (ThrCurrencyEntity currency : currencyList) {
                    RMBQuotItem rmbQuotItem = rmbQuotMap.get(currency.getNameCn());
                    if (rmbQuotItem != null) {
                        currency.setExchangeRate(rmbQuotItem.bankConversionPri);
                    }
                }

                currencyService.updateRateWithList(currencyList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class RMBQuot {
        @SerializedName("error_code")
        public String errorCode;
        @SerializedName("resultcode")
        public String resultCode;
        public String reason;
        public List<Map<String, RMBQuotItem>> result;
    }

    private class RMBQuotItem {
        public BigDecimal bankConversionPri;
        public String name;
    }
}
