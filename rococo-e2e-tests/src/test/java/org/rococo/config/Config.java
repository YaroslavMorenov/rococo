package org.rococo.config;

public interface Config {

    static Config getInstance() {
        if ("docker".equals(System.getProperty("testEnv"))) {
            return DockerConfig.config;
        }
        return LocalConfig.config;
    }

    String databaseHost();

    String rococoFrontUrl();

    default String databaseUser() {
        return "postgres";
    }

    default String databasePassword() {
        return "secret";
    }

    default int databasePort() {
        return 5432;
    }

    String rococoAuthUrl();
    String rococoGatewayUrl();
    String rococoArtistUrl();
    String rococoMuseumUrl();
    String rococoCountryUrl();
    String rococoPaintingUrl();
}
