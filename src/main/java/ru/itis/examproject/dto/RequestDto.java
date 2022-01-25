package ru.itis.examproject.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Builder
@Data
@ToString
public class RequestDto {
    private static Gson gson = new GsonBuilder().create();

    private Map<String, String> info;
    private String type;

    public String infoToJson() {
        return gson.toJson(info);
    }

}
