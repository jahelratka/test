package com.testapp;

import com.springboot.upload.UploadApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(classes = {UploadApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TestBase {

}
