package ru.itis.generator.listners;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class FilesMessageListener {

    private final RabbitTemplate rabbitTemplate;

    private static Gson gson = new GsonBuilder()
            .create();
    @RabbitListener(queues = "#{queue.name}", containerFactory = "containerFactory")
    public void onMessage(Message message) {
        try {
            String json = new String(message.getBody());

            Map<String, String> map = gson.fromJson(json, Map.class);
            System.out.println("FirstName " + map.get("firstName") + " lastName " + map.get("lastName"));
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream.newLineAtOffset(25, 700);
            contentStream.showText(String.format(
                    "First Name: %s, Last Name: %s",
                    map.get("firstName"),
                    map.get("lastName")));
            contentStream.endText();
            contentStream.close();

            System.out.println(UUID.randomUUID().toString().substring(0, 4) + "fio.pdf");
            document.save(UUID.randomUUID().toString().substring(0, 4) + "fio.pdf");
            document.close();
            rabbitTemplate.send("files_topic_exchange",
                    "save",
                    new Message(("Generated fio pdf: " + String.format(
                            "FirstName: %s, LastNAme: %s",
                            map.get("firstName"),
                            map.get("LastName"))).getBytes()));
            System.out.println(Thread.currentThread().getName() + " " + (new String(message.getBody())));
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }

}
