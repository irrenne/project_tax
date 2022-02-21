package com.epam.tax.services.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.epam.tax.services.FileStorageService;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.UUID;

public class FileStorageServiceImpl implements FileStorageService {
    private AmazonS3 s3client;
    private static final String bucketName = "testlviv";
    private static final String accessKey = "AKIAXE2BMEHETQDLLIOP";
    private static final String secretAccessKey ="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";
    private String targetPath;


    public String getTargetPath() throws URISyntaxException {
        targetPath = Paths.get(FileStorageServiceImpl.class.getResource("/").toURI()).getParent().getParent()+"/";
        return targetPath;
    }

    @Override
    public void saveFile(String path, String fileName, HttpServletRequest request) throws ServletException, IOException {
        File dir = new File(path);
        if (dir.exists()) {
            if (dir.isDirectory()) {
                for (Part part : request.getParts()) {
                    part.write(path + fileName);
                }
            }
        } else {
            if (dir.mkdir()) {
                for (Part part : request.getParts()) {
                    part.write(path + fileName);
                }
            }
        }
    }

    @Override
    public String uploadFile(String path) {
        String fileName  = "";
        //            File file = convertPartToFile(part);
        fileName = getGeneratedFileName();
//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentType(part.getContentType());
//        metadata.setContentLength(part.getSize());
        File file = new File(path);
        uploadFileTos3bucket(fileName, file);
//            uploadFileTos3bucket(fileName, part.getInputStream(), metadata);
        return fileName;
    }

    @Override
    public void deleteFile(String fileName) {

    }

    private void uploadFileTos3bucket(String fileName, File file) {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretAccessKey);
        s3client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_CENTRAL_1).build();

        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
//        s3client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, metadata)
//                .withCannedAcl(CannedAccessControlList.PublicRead));
    }


    private File convertPartToFile(Part part) throws IOException {
        File convertedFile = new File(part.getSubmittedFileName());
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(IOUtils.toByteArray(part.getInputStream()));
        fos.close();
        return convertedFile;
    }

    private String getGeneratedFileName() {
        return UUID.randomUUID().toString();
    }
}
