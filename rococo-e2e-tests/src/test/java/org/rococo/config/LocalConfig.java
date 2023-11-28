package org.rococo.config;

import com.codeborne.selenide.Configuration;

import static com.codeborne.selenide.FileDownloadMode.FOLDER;

public class LocalConfig implements Config {

    static final LocalConfig config = new LocalConfig();

    static {
        Configuration.browserSize = "1980x1024";
        Configuration.fileDownload = FOLDER;
    }

    private LocalConfig() {
    }

    @Override
    public String databaseHost() {
        return "localhost";
    }

    @Override
    public String rococoFrontUrl() {
        return "http://127.0.0.1:3000";
    }

    @Override
    public String rococoAuthUrl() {
        return "http://127.0.0.1:9050";
    }

    @Override
    public String rococoGatewayUrl() {
        return "http://127.0.0.1:8080";
    }

    @Override
    public String rococoArtistUrl() {
        return "http://127.0.0.1:8095";
    }

    @Override
    public String rococoMuseumUrl() {
        return "http://127.0.0.1:8096";
    }

    @Override
    public String rococoCountryUrl() {
        return "http://127.0.0.1:8097";
    }

    @Override
    public String rococoPaintingUrl() {
        return "http://127.0.0.1:8098";
    }
}
