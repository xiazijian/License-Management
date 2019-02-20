package com._4paradigm.prophet.LicenseMangement.service;

import com._4paradigm.prophet.LicenseMangement.entity.LicenseRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class LicenseRecordService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int getLiceneRecordCount(){
        String sql = "SELECT count(*) FROM license_record WHERE  isEffect = 0";
        return jdbcTemplate.queryForObject(sql,Integer.class);
    }

    public int getLicenePassRecordCount(){
        String sql = "SELECT count(*) FROM license_record WHERE  isEffect = 1";
        return jdbcTemplate.queryForObject(sql,Integer.class);
    }

    public int getLiceneRefuseRecordCount(){
        String sql = "SELECT count(*) FROM license_record WHERE  isEffect = 2";
        return jdbcTemplate.queryForObject(sql,Integer.class);
    }

    public List<LicenseRecord> getLimitList(int start ,int count){
        String sql = "SELECT * FROM license_record WHERE  isEffect = 0 limit "+(start-1)*count+","+count;
        return (List<LicenseRecord>) jdbcTemplate.query(sql, new RowMapper<LicenseRecord>() {
                    @Override
                    public LicenseRecord mapRow(ResultSet resultSet, int i) throws SQLException {
                        LicenseRecord licenseRecord = new LicenseRecord();
                        licenseRecord.setId(resultSet.getInt("id"));
                        licenseRecord.setConfiguration(resultSet.getString("configuration"));
                        licenseRecord.setName(resultSet.getString("name"));
                        licenseRecord.setIsEffect(resultSet.getInt("isEffect"));
                        return licenseRecord;
                    }
                }
        );
    }

    public List<LicenseRecord> getList() {
        String sql = "SELECT * FROM license_record WHERE  isEffect = 0";

        return (List<LicenseRecord>) jdbcTemplate.query(sql, new RowMapper<LicenseRecord>() {
                    @Override
                    public LicenseRecord mapRow(ResultSet resultSet, int i) throws SQLException {
                        LicenseRecord licenseRecord = new LicenseRecord();
                        licenseRecord.setId(resultSet.getInt("id"));
                        licenseRecord.setConfiguration(resultSet.getString("configuration"));
                        licenseRecord.setName(resultSet.getString("name"));
                        licenseRecord.setIsEffect(resultSet.getInt("isEffect"));
                        return licenseRecord;
                    }
                }
        );
    }
    public List<LicenseRecord> getAllPassLimitList(int start ,int count) {
        String sql = "SELECT * FROM license_record WHERE  isEffect = 1 limit "+(start-1)*count+","+count;
        return (List<LicenseRecord>) jdbcTemplate.query(sql, new RowMapper<LicenseRecord>() {
                    @Override
                    public LicenseRecord mapRow(ResultSet resultSet, int i) throws SQLException {
                        LicenseRecord licenseRecord = new LicenseRecord();
                        licenseRecord.setId(resultSet.getInt("id"));
                        licenseRecord.setConfiguration(resultSet.getString("configuration"));
                        licenseRecord.setName(resultSet.getString("name"));
                        licenseRecord.setLicense(resultSet.getString("license"));
                        licenseRecord.setIsEffect(resultSet.getInt("isEffect"));
                        return licenseRecord;
                    }
                }
        );
    }
    public List<LicenseRecord> getAllPassList() {
        String sql = "SELECT * FROM license_record WHERE  isEffect = 1";

        return (List<LicenseRecord>) jdbcTemplate.query(sql, new RowMapper<LicenseRecord>() {
                    @Override
                    public LicenseRecord mapRow(ResultSet resultSet, int i) throws SQLException {
                        LicenseRecord licenseRecord = new LicenseRecord();
                        licenseRecord.setId(resultSet.getInt("id"));
                        licenseRecord.setConfiguration(resultSet.getString("configuration"));
                        licenseRecord.setName(resultSet.getString("name"));
                        licenseRecord.setLicense(resultSet.getString("license"));
                        licenseRecord.setIsEffect(resultSet.getInt("isEffect"));
                        return licenseRecord;
                    }
                }
        );
    }
    public List<LicenseRecord> geAllRefusetLimitList(int start ,int count) {
        String sql = "SELECT * FROM license_record WHERE  isEffect = 1 limit "+(start-1)*count+","+count;

        return (List<LicenseRecord>) jdbcTemplate.query(sql, new RowMapper<LicenseRecord>() {
                    @Override
                    public LicenseRecord mapRow(ResultSet resultSet, int i) throws SQLException {
                        LicenseRecord licenseRecord = new LicenseRecord();
                        licenseRecord.setId(resultSet.getInt("id"));
                        licenseRecord.setConfiguration(resultSet.getString("configuration"));
                        licenseRecord.setName(resultSet.getString("name"));
                        licenseRecord.setIsEffect(resultSet.getInt("isEffect"));
                        return licenseRecord;
                    }
                }
        );
    }
    public List<LicenseRecord> geAllRefusetList() {
        String sql = "SELECT * FROM license_record WHERE  isEffect = 2";

        return (List<LicenseRecord>) jdbcTemplate.query(sql, new RowMapper<LicenseRecord>() {
                    @Override
                    public LicenseRecord mapRow(ResultSet resultSet, int i) throws SQLException {
                        LicenseRecord licenseRecord = new LicenseRecord();
                        licenseRecord.setId(resultSet.getInt("id"));
                        licenseRecord.setConfiguration(resultSet.getString("configuration"));
                        licenseRecord.setName(resultSet.getString("name"));
                        licenseRecord.setIsEffect(resultSet.getInt("isEffect"));
                        return licenseRecord;
                    }
                }
        );
    }
    public List<LicenseRecord> getUserList(String user,int isEffect) {
        String sql = "SELECT * FROM license_record WHERE  isEffect = "+isEffect+" and name='"+user+"'";

        return (List<LicenseRecord>) jdbcTemplate.query(sql, new RowMapper<LicenseRecord>() {
                    @Override
                    public LicenseRecord mapRow(ResultSet resultSet, int i) throws SQLException {
                        LicenseRecord licenseRecord = new LicenseRecord();
                        licenseRecord.setId(resultSet.getInt("id"));
                        licenseRecord.setConfiguration(resultSet.getString("configuration"));
                        licenseRecord.setName(resultSet.getString("name"));
                        licenseRecord.setIsEffect(resultSet.getInt("isEffect"));
                        // 这里不set License 是因为不能给用户在没通过前返回License
                        return licenseRecord;
                    }
                }
        );
    }
    public List<LicenseRecord> getUserPassList(String user,int isEffect) {
        String sql = "SELECT * FROM license_record WHERE  isEffect = "+isEffect+" and name='"+user+"'";

        return (List<LicenseRecord>) jdbcTemplate.query(sql, new RowMapper<LicenseRecord>() {
                    @Override
                    public LicenseRecord mapRow(ResultSet resultSet, int i) throws SQLException {
                        LicenseRecord licenseRecord = new LicenseRecord();
                        licenseRecord.setId(resultSet.getInt("id"));
                        licenseRecord.setConfiguration(resultSet.getString("configuration"));
                        licenseRecord.setName(resultSet.getString("name"));
                        licenseRecord.setLicense(resultSet.getString("license"));
                        licenseRecord.setIsEffect(resultSet.getInt("isEffect"));
                        return licenseRecord;
                    }
                }
        );
    }
}
