package com.springboot.upload.config;

import com.springboot.upload.listener.UploadProgressListener;
import com.springboot.upload.model.DurationModel;
import com.springboot.upload.model.UploadModel;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomMultipartResolver extends CommonsMultipartResolver {

    public static List<UploadModel> uploadModelList = new ArrayList<>();
    public static List<DurationModel> durationList = new ArrayList<>();

    @Autowired
    private UploadProgressListener progressListener;

    @Override
    protected MultipartParsingResult parseRequest(HttpServletRequest request) throws MultipartException {
        String encoding = super.determineEncoding(request);
        request.getSession().setAttribute("fileName",request.getHeader("X-Upload-File"));
        progressListener.setSession(request.getSession());
        FileUpload fileUpload = prepareFileUpload(encoding);
        fileUpload.setProgressListener(progressListener);
        try {
            List<FileItem> fileItemList =  ((ServletFileUpload)fileUpload).parseRequest(request);
            return super.parseFileItems(fileItemList, encoding);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        return null;
    }
}
