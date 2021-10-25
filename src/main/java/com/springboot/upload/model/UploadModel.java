package com.springboot.upload.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UploadModel {
    private String id;
    private long size;
    private long uploaded;
}
