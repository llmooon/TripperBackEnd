package org.soma.tripper.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.soma.tripper.review.service.AmazonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/storage")
@Api(value="S3BucketController",description = "S3 버켓 Controller")
public class BucketController {
    private AmazonClient amazonClient;
    @Autowired
    BucketController(AmazonClient amazonClient){
        this.amazonClient=amazonClient;
    }

    @ApiOperation(value="Upload Files to S3")
    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value="file")MultipartFile file){
        return this.amazonClient.uploadFile(file);
    }

}
