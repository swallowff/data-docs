package cn.huapu.power.core.util;

import org.springframework.util.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Author: shenyu
 * @Date: 2019-11-27 13:55
 * @Description:
 */
public class DownloadFileInfoBo {
    private String fileName;
    private File file;

    public DownloadFileInfoBo(String fileName, File file) throws IOException {
        Assert.notNull(file,"download file could not be null");
        if (file.exists() && file.isFile()){
            this.fileName = fileName;
            this.file = file;
        }else {
            throw new FileNotFoundException("download file not found : "+file.getAbsolutePath());
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
