package ru.itis.examproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.itis.examproject.dto.RequestDto;

import java.nio.charset.StandardCharsets;


@Service
@RequiredArgsConstructor
public class FileService {

    private final RabbitTemplate rabbitTemplate;
    private final static String FIO_ROUTING_KEY = "fio";
    private final static String QA_ROUTING_KEY = "qa";
//    private final static String JPG_ROUTING_KEY = "files.images.jpg";
//    private final static String PDF_ROUTING_KEY = "files.documents.pdf";
//    private final static String HTML_ROUTING_KEY = "files.documents.html";




    public String generateDoc(RequestDto requestDto){

        String currentRouting = requestDto.getType();



        switch (currentRouting) {
            case "fio":
                currentRouting = FIO_ROUTING_KEY;
                break;
            case "qa":
                currentRouting = QA_ROUTING_KEY;
        }


        rabbitTemplate.send("files_topic_exchange",
                currentRouting,
                new Message(requestDto.infoToJson().getBytes(StandardCharsets.UTF_8)));
        return null;
    }
}
