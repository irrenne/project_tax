package com.epam.tax.services;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;

/**
 * Interface, that contains methods for
 * saving file into upload directory and generating file names.
 */
public interface FileStorageService {

    /**
     * Method for saving file to the directory.
     *
     * @param path total path, where file must be saved.
     * @param fileName specified name of the file.
     * @param fileParts collections of all Part objects.
     */
    void saveFile(String path, String fileName, Collection<Part> fileParts) throws IOException;

    /**
     * Method for inserting objects in database.
     *
     * @param extension is extension of file.
     * @return generated filename.
     */
    String getGeneratedFileName(String extension);
}
