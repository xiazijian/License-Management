package com._4paradigm.prophet.LicenseMangement.entity;

import lombok.Data;

@Data
public class LicensePo {
    private String product;
    private String version;
    private String issued_date;
    private String expired_date;

}
