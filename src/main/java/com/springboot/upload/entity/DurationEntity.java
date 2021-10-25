package com.springboot.upload.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.springboot.upload.config.CustomMultipartResolver;
import com.springboot.upload.model.DurationModel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@RequiredArgsConstructor
public class DurationEntity {

    public List<DurationModel> durationList = CustomMultipartResolver.durationList;

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String jsonArray =  gson.toJson(durationList);
        return jsonArray;
    }
}

