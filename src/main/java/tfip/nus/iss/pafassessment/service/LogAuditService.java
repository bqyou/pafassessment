package tfip.nus.iss.pafassessment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import tfip.nus.iss.pafassessment.model.Transfer;

@Service
public class LogAuditService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void logTransaction(final Transfer transfer) {
        redisTemplate.opsForValue().set(transfer.getTransactionId(),
                Transfer.toJson(transfer).toString());
    }

}
