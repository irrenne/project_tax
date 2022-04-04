package com.epam.tax.services.impl;

import com.epam.tax.services.FileStorageService;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

public class FileStorageServiceImpl implements FileStorageService {

    @Override
    public void saveFile(String path, String fileName, Collection<Part> fileParts) throws IOException {
        File dir = new File(path);
        if (dir.exists()) {
            if (dir.isDirectory()) {
                for (Part part : fileParts) {
                    part.write(path + fileName);
                }
            }
        } else {
            if (dir.mkdir()) {
                for (Part part : fileParts) {
                    part.write(path + fileName);
                }
            }
        }
    }

    @Override
    public String getGeneratedFileName(String extension) {
        return UUID.randomUUID() + "." + extension;
    }
}
