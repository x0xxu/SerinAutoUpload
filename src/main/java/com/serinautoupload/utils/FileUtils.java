package com.serinautoupload.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.Deflater;

@Slf4j
public class FileUtils {

    public static void createZipWithCommons(File[] files, String zipFilePath) throws IOException {
        Path zipPath = Paths.get(zipFilePath);

        // 确保输出目录存在
        File zipFile = zipPath.toFile();
        File parentDir = zipFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (ZipArchiveOutputStream zos = new ZipArchiveOutputStream(zipFile)) {
            zos.setLevel(Deflater.BEST_COMPRESSION);  // 最高压缩级别
            zos.setMethod(ZipArchiveOutputStream.DEFLATED);  // 确保使用 DEFLATED（而非 STORED）
            zos.setUseZip64(Zip64Mode.Always);
            for (File file : files) {
                if (!file.exists()) {
                    log.warn("跳过不存在的文件: {}", file.getName());
                    continue;
                }

                ZipArchiveEntry entry = new ZipArchiveEntry(file, file.getName());
                zos.putArchiveEntry(entry);

                try (InputStream is = Files.newInputStream(file.toPath())) {
                    byte[] buffer = new byte[8192];
                    int len;
                    while ((len = is.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                }

                zos.closeArchiveEntry();
            }

        }
    }


    public static void deleteFile(String s) {
        try {
            File file = new File(s);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
