package ru.itis.auditservice.listner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.auditservice.entity.AuditEntity;
import ru.itis.auditservice.repository.AuditRepository;

@Component
public class SaveListner {

    private static Gson gson = new GsonBuilder().create();

    @Autowired
    private AuditRepository auditRepository;

    @RabbitListener(queues = "#{queue.name}", containerFactory = "containerFactory")
    public void onMessage(Message message) {
        try {
            String mess = new String(message.getBody());
            AuditEntity auditEntity = auditRepository.save(AuditEntity.builder()
                    .message(mess)
                    .build());

            System.out.println(auditEntity);
            System.out.println(Thread.currentThread().getName() + " " + (new String(message.getBody())));
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
