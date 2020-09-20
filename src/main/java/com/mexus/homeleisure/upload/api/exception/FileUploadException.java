package com.mexus.homeleisure.upload.api.exception;

public class FileUploadException extends RuntimeException {
    public FileUploadException(String fileName, Throwable cause) {
        super("fileName: '" + fileName + "' 파일 업로드에 실패하였습니다.", cause);
    }
}
