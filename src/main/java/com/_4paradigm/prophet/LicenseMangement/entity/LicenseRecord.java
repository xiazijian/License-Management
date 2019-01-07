package com._4paradigm.prophet.LicenseMangement.entity;
import lombok.Data;

@Data
public class LicenseRecord {
    int id;
    String name;
    String configuration;
    String license;
    int isEffect;

}
