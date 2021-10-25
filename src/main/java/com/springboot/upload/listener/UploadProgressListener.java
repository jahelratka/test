package com.springboot.upload.listener;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.springboot.upload.config.CustomMultipartResolver;
import com.springboot.upload.entity.DurationEntity;
import com.springboot.upload.entity.ProgressEntity;
import com.springboot.upload.model.DurationModel;
import com.springboot.upload.model.UploadModel;
import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.Instant;

@Component
public class UploadProgressListener implements ProgressListener {

    private HttpSession session;
    private UploadModel uploadModel = new UploadModel();
    private DurationModel  durationModel = new DurationModel();
    Instant startTime = Instant.now();

    public void setSession(HttpSession session){
        var id = String.format("%s-%s", session.getAttribute("fileName"), Instant.now().toEpochMilli());
        uploadModel.setId(id);
        durationModel.setUploadDuration("upload_duration{id=”"+id+"”} ");
        this.session = session;
        ProgressEntity pe = new ProgressEntity();
        DurationEntity de = new DurationEntity();
        CustomMultipartResolver.uploadModelList.add(uploadModel);
        session.setAttribute("durationStatus", de);
        session.setAttribute("uploadStatus", pe);
    }


    @Override
    public void update(long readBytes, long allBytes, int index) {
        uploadModel.setUploaded(readBytes);
        uploadModel.setSize(allBytes);
        if(readBytes == allBytes){
            CustomMultipartResolver.uploadModelList.remove(uploadModel);
            Instant endTime = Instant.now();
            long timeElapsed = Duration.between(startTime, endTime).toMillis();
            durationModel.setUploadDuration(durationModel.getUploadDuration()+timeElapsed);
            CustomMultipartResolver.durationList.add(durationModel);
        }
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String jsonArray =  gson.toJson(CustomMultipartResolver.uploadModelList);
        System.out.println(String.valueOf(readBytes)+" - "+session.getAttribute("fileName")+" -- "+jsonArray);
    }
}
