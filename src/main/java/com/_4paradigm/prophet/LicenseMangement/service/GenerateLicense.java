package com._4paradigm.prophet.LicenseMangement.service;


import com._4paradigm.prophet.LicenseMangement.cipher.RSALicenseCipher;
import com._4paradigm.prophet.LicenseMangement.entity.License;

public class GenerateLicense {
    public static String generateLicense(License license) throws Exception {
        return new RSALicenseCipher().encrypt(license);
    }
}
