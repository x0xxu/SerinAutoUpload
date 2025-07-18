package com.serinautoupload.service.impl;

import com.serinautoupload.constants.Constants;
import com.serinautoupload.service.OEEKitService;
import com.serinautoupload.utils.FileUtils;
import com.serinautoupload.utils.MailUtils;
import com.serinautoupload.utils.ResultSetToCsvExporter;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.sql.DriverManager.getConnection;


@Slf4j
@Service
public class OEEKitServiceImpl implements OEEKitService {

    @Resource
    private MailUtils mailUtils;

    @Override
    public void uploadOEEKitA145() throws MessagingException {
        String ServerName = "10.52.16.16";
        String DBName = "PALReport";
        String UserName = "qmsapp";
        String Password = "qms#9app";
        String ConnStr = "jdbc:sqlserver://" + ServerName + ";trustServerCertificate=true";
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String OEEFileName = time + "-stationlevel.csv";
        String MSGFileName = time + "-alarmlog.csv";
        String OutputPath = Constants.A145ZipPath + time + "-stationlevel.csv.zip";

        try {
            Statement statement = getConnection(ConnStr, UserName, Password).createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String Sql = "EXEC " + DBName + ".[dbo].[spMES_AutoUploadMES_OEEKit_BB]";
            ResultSet resultSet = statement.executeQuery(Sql);
            if (!resultSet.next()) {
                mailUtils.sendSimpleMail("(A145)OEEKit上传数据异常,未查询到最新数据请检查程序是否异常!!!!");
                log.warn("{}(A145)MES_OEEKit_BB抓取数据失败", time);
            } else {
                resultSet.beforeFirst();
                ResultSetToCsvExporter.exportToCsv(resultSet, Constants.UploadPath + time + "-stationlevel.csv");
            }
            Sql = "EXEC " + DBName + ".dbo.[spMES_AutoUpload_MES_OEEKit_DownTimeMsg]";
            statement.executeQuery(Sql);
            resultSet = statement.getResultSet();
            if (!resultSet.next()) {
                mailUtils.sendSimpleMail("(A145)OEEKit上传数据异常,未查询到最新数据请检查程序是否异常!!!!");
                log.warn("{}(A145)MES_OEEKit_DownTimeMsg抓取数据失败", time);
            } else {
                resultSet.beforeFirst();
                ResultSetToCsvExporter.exportToCsv(resultSet, Constants.UploadPath + time + "-alarmlog.csv");
            }
            File[] files = {new File(Constants.UploadPath + OEEFileName), new File(Constants.UploadPath + MSGFileName)};
            FileUtils.createZipWithCommons(files, OutputPath);
            FileUtils.deleteFile(Constants.UploadPath + OEEFileName);
            FileUtils.deleteFile(Constants.UploadPath + MSGFileName);
            statement.close();
            log.info("{}-A145OEEKit上传成功", time);
        } catch (Exception e) {
            log.error("(A145)运行时异常:{}", e.getMessage());
            mailUtils.sendSimpleMail("(A145)OEEKit上传数据异常,请检查程序是否异常!!!!" + e.getMessage());
        }
    }

    @Override
    public void uploadOEEKitA155() throws MessagingException {
        String ServerName = "10.52.16.19";
        String DBName = "PALReport";
        String UserName = "qmsapp";
        String Password = "qms#9app";
        String ConnStr = "jdbc:sqlserver://" + ServerName + ";trustServerCertificate=true";
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String OEEFileName = time + "-stationlevel.csv";
        String MSGFileName = time + "-alarmlog.csv";
        String OutputPath = Constants.A155ZipPath + time + "-stationlevel.csv.zip";

        try {
            Statement statement = getConnection(ConnStr, UserName, Password).createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String Sql = "EXEC " + DBName + ".[dbo].[spMES_AutoUploadMES_OEEKit_BB]";
            ResultSet resultSet = statement.executeQuery(Sql);
            if (!resultSet.next()) {
                mailUtils.sendSimpleMail("(A155)OEEKit上传数据异常,未查询到最新数据请检查程序是否异常!!!!");
                log.warn("{}(A155)MES_OEEKit_BB抓取数据失败", time);
            } else {
                resultSet.beforeFirst();
                ResultSetToCsvExporter.exportToCsv(resultSet, Constants.UploadPath + time + "-stationlevel.csv");
            }
            Sql = "EXEC " + DBName + ".dbo.[spMES_AutoUpload_MES_OEEKit_DownTimeMsg]";
            statement.executeQuery(Sql);
            resultSet = statement.getResultSet();
            if (!resultSet.next()) {
                mailUtils.sendSimpleMail("(A155)OEEKit上传数据异常,未查询到最新数据请检查程序是否异常!!!!");
                log.warn("{}(A155)MES_OEEKit_DownTimeMsg抓取数据失败", time);
            } else {
                resultSet.beforeFirst();
                ResultSetToCsvExporter.exportToCsv(resultSet, Constants.UploadPath + time + "-alarmlog.csv");
            }
            File[] files = {new File(Constants.UploadPath + OEEFileName), new File(Constants.UploadPath + MSGFileName)};
            FileUtils.createZipWithCommons(files, OutputPath);
            FileUtils.deleteFile(Constants.UploadPath + OEEFileName);
            FileUtils.deleteFile(Constants.UploadPath + MSGFileName);
            statement.close();
            log.info("{}-A155OEEKit上传成功", time);
        } catch (Exception e) {
            log.error("(A155)运行时异常:{}", e.getMessage());
            mailUtils.sendSimpleMail("(A155)OEEKit上传数据异常,请检查程序是否异常!!!!" + e.getMessage());
        }
    }
}
