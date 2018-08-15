package org.soma.tripper.review.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tripper.review.dto.ImagePath;
import org.soma.tripper.review.dto.PhotoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;

@Service
public class AmazonClient {

    private AmazonS3 s3client;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;


    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = new AmazonS3Client(credentials);
    }

    public ImagePath uploadFile(MultipartFile multipartFile) {

        String fileUrl = "";
        String realbucket = bucketName;
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");

            DateTime date = new DateTime();
            String DateName = date.getYear() + "/" + date.getMonthOfYear() + "/" + date.getDayOfMonth();
            bucketName = bucketName.concat("/" + date.getYear() + "/" + date.getMonthOfYear() + "/" + date.getDayOfMonth());
            uploadFileTos3bucket(fileName, file);

            String path = System.getProperty("user.dir");
            File thumbnail = new File(path+"\\thumbnail.jpg");

            thumbnail.getParentFile().mkdirs();
            Thumbnails.of(file).size(190,150).outputFormat("jpg").toFile(thumbnail);
            uploadThumbnailFileTos3bucket(fileName,thumbnail);
            thumbnail.delete();

            file.delete();
            bucketName = realbucket;
            ImagePath path1 = ImagePath.builder()
                    .fileName(fileName)
                    .dateName(DateName)
                    .build();
            return path1;

        } catch (Exception e) {
            e.printStackTrace();
            bucketName = realbucket;
        }
        return null;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    private void uploadThumbnailFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName+"/thumb", fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        return "Successfully deleted";
    }
}