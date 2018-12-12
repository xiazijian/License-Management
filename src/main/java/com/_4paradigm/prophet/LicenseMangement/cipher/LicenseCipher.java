package com._4paradigm.prophet.LicenseMangement.cipher;


import com._4paradigm.prophet.LicenseMangement.entity.License;

public interface LicenseCipher {

    License decrypt(String content) throws Exception;

    String encrypt(License license) throws Exception;
}
