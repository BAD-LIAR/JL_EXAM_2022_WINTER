package ru.itis.qagenerator.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FIODto {
    private String firstName;
    private String lastName;
    private static Gson gson = new GsonBuilder()
            .create();
    public String toString() {
        return gson.toJson(this);
    }

    public static FIODto getFIODto(String json){
        return gson.fromJson(json, FIODto.class);
    }
}
