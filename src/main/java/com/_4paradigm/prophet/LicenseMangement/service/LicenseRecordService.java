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

    public List<LicenseRecord> getList() {
        String sql = "SELECT * FROM license_record WHERE  isEffect = 0";

        return (List<LicenseRecord>) jdbcTemplate.query(sql, new RowMapper<LicenseRecord>() {
                    @Override
                    public LicenseRecord mapRow(ResultSet resultSet, int i) throws SQLException {
                        LicenseRecord licenseRecord = new LicenseRecord();
                        licenseRecord.setId(resultSet.getInt("id"));
                        licenseRecord.setConfiguration(resultSet.getString("configuration"));
                        licenseRecord.setName(resultSet.getString("name"));
                        licenseRecord.setLicense(resultSet.getString("license"));
                        return licenseRecord;
                    }
                }
        );
    }
}
