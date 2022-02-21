package com.epam.tax.services;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface FileStorageService {
    void saveFile(String path, String fileName, HttpServletRequest request) throws ServletException, IOException;

    String uploadFile(String path);
//    String uploadFile(Part part);
    void deleteFile(String fileName);
}
