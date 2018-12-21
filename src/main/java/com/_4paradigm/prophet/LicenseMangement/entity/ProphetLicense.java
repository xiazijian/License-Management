package com._4paradigm.prophet.LicenseMangement.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ProphetLicense extends License {

    public static final String PROPHET_PRODUCT = "prophet";

    private String key;
    private String version;
    private String edition;
    private LicenseType type;
    private String customer;
    private int maxTenantCount;

    public ProphetLicense() {
        setProduct(PROPHET_PRODUCT);
    }
}