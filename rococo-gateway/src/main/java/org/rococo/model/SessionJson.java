package org.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public record SessionJson(@JsonProperty("username")
                          String username,
                          @JsonProperty("issuedAt")
                          Date issuedAt,
                          @JsonProperty("expiresAt")
                          Date expiresAt) {
}
