package com.serinautoupload.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class ResultSetToCsvExporter {

    public static void exportToCsv(ResultSet resultSet, String filePath) throws SQLException, IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // 写入列标题
            for (int i = 1; i <= columnCount; i++) {
                writer.write(metaData.getColumnName(i));
                if (i < columnCount) {
                    writer.write(",");
                }
            }
            writer.write("\n");

            // 写入数据行
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    if (value instanceof java.util.Date) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        writer.write(simpleDateFormat.format(value));
                    } else if (value != null) {
                        // 处理包含逗号或换行符的值
                        String stringValue = value.toString();
                        if (stringValue.contains(",") || stringValue.contains("\n")) {
                            writer.write("\"" + stringValue.replace("\"", "\"\"") + "\"");
                        } else {
                            writer.write(stringValue);
                        }
                    }
                    if (i < columnCount) {
                        writer.write(",");
                    }
                }
                writer.write("\n");
            }
        }
    }
}