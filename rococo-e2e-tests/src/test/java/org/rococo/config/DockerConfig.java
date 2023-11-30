package org.rococo.config;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.FileDownloadMode.FOLDER;

public class DockerConfig implements Config {

    static final DockerConfig config = new DockerConfig();

    static {
        Configuration.remote = "http://selenoid:4444/wd/hub";
        Configuration.timeout = 10000;
        Configuration.browserSize = "1920x1080";
        Configuration.browser = "chrome";
        Configuration.browserVersion = "110.0";
        Configuration.browserCapabilities = new ChromeOptions()
                .addArguments("--no-sandbox");
    }

    private DockerConfig() {
    }

    @Override
    public String databaseHost() {
        return "rococo-db";
    }

    @Override
    public String rococoFrontUrl() {
        return "http://client.rococo.dc";
    }

    @Override
    public String rococoAuthUrl() {
        return "http://auth.rococo.dc:9050";
    }

    @Override
    public String rococoGatewayUrl() {
        return "http://gateway.rococo.dc";
    }

    @Override
    public String rococoArtistUrl() {
        return "http://artist.rococo.dc:8095/";
    }

    @Override
    public String rococoMuseumUrl() {
        return "http://museum.rococo.dc:8096/";
    }

    @Override
    public String rococoCountryUrl() {
        return "http://country.rococo.dc:8097/";
    }

    @Override
    public String rococoPaintingUrl() {
        return "http://painting.rococo.dc:8098/";
    }
}
