package com.satyampay.pg.merchant.exception;

public class MerchantNotFoundException extends RuntimeException{

    public MerchantNotFoundException(String message) {
        super("Merchant with code " + message + " not found.");
    }
}
