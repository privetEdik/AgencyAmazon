package kettlebell.agencyamazon.service.statistic;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.List;

@Component("customKeyGenerator")
public class CustomKeyGenerator implements KeyGenerator {
    @Override
    public @NonNull Object generate(@NonNull Object target, @NonNull Method method, @NonNull Object... params) {
        if (params.length == 1 && params[0] instanceof List<?> list) {
            return list.stream().sorted().toList();
        }
        return SimpleKeyGenerator.generateKey(params);
    }
}
