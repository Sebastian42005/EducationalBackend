package com.example.educationalbackend.dto;

import java.util.Arrays;
import org.springframework.http.HttpHeaders;

public record FileDTO(byte[] content, String contentType, HttpHeaders headers) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileDTO fileDTO = (FileDTO) o;
        return Arrays.equals(content, fileDTO.content) &&
                contentType.equals(fileDTO.contentType) &&
                headers.equals(fileDTO.headers);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(content);
        result = 31 * result + contentType.hashCode();
        result = 31 * result + headers.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "FileDTO{" +
                "content=" + Arrays.toString(content) +
                ", contentType='" + contentType + '\'' +
                ", headers=" + headers +
                '}';
    }
}
