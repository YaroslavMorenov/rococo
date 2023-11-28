package org.rococo.db;

import org.apache.commons.lang3.StringUtils;
import org.rococo.config.Config;

public enum ServiceDB {
    AUTH("jdbc:postgresql://%s:%d/rococo-auth"),
    USERDATA("jdbc:postgresql://%s:%d/rococo-userdata"),
    ARTIST("jdbc:postgresql://%s:%d/rococo-artist"),
    MUSEUM("jdbc:postgresql://%s:%d/rococo-museum"),
    PAINTING("jdbc:postgresql://%s:%d/rococo-painting"),
    COUNTRY("jdbc:postgresql://%s:%d/rococo-country");

    private final String url;
    private static final Config cfg = Config.getInstance();

    ServiceDB(String url) {
        this.url = url;
    }

    public String getUrl() {
        return String.format(
                url,
                cfg.databaseHost(),
                cfg.databasePort()
        );
    }

    public String p6spyUrl() {
        return "jdbc:p6spy:postgresql:" + StringUtils.substringAfter(getUrl(), "jdbc:postgresql:");
    }
}
