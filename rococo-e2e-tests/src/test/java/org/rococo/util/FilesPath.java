package org.rococo.util;

public enum FilesPath {

    USER_AVATAR("avatars/userAvatar.jpg"),
    ARTIST_AVATAR("avatars/artistAvatar.jpg"),
    MUSEUM_AVATAR("avatars/museumAvatar.jpg"),
    PAINTING_AVATAR("avatars/paintingAvatar.jpg");

    private final String fileName;

    FilesPath(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }
}
