package org.rococo.util;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Base64;

public class FileReaderUtils {

    private FileReaderUtils() {
    }

    @SneakyThrows
    public static String getPath(String fileName) {
        var url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        assert url != null;
        try {
            return new File(url.toURI().getPath()).getAbsolutePath();
        } catch (URISyntaxException e) {
            throw new FileNotFoundException(e.getMessage());
        }
    }

    @SneakyThrows
    public static String encodeFileToBase64Binary(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fileInputStream.read(data);
            fileInputStream.close();
            return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(data);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Cannot encode file " + e.getMessage());
        }
    }
}
