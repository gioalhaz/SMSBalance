package com.smsbalance.processing;

import java.util.HashMap;

/**
 * Created by gioal on 15.07.2017.
 */

public class SMSParser {
    public class Result {

        public boolean matched = false;
        public String cardMask;
        public String cardDescription;
        public String pos;
        public String amount;
        public String amountCurrency;
        public String balance;
        public String balanceCurrency;
    }

    protected StringParser parser;

    public SMSParser(String pattern){
        parser = new StringParser();
        parser.createPattern(pattern);
    }

    public Result parse(String smsText) {

        Result result = new Result();

        HashMap<String, String> hash = parser.parse(smsText);
        if(hash != null) {
            result.matched = true;
            result.pos = getGroup(hash, "pos");
            result.cardMask = getGroup(hash, "card_mask");
            result.cardDescription = getGroup(hash, "card_desc");
            result.amount = normalizeAmount(getGroup(hash, "amount"));
            result.amountCurrency = getGroup(hash, "amount_cur");
            result.balance = normalizeAmount(getGroup(hash, "balance"));
            result.balanceCurrency = getGroup(hash, "balance_cur");
        }

        return result;
    }

    protected String getGroup(HashMap<String, String> hash, String name) {
        String result = null;

        try {
            result = hash.get(name);
        }
        catch(Exception x){ }

        return result;
    }

    protected String normalizeAmount(String amountString) {

        if (amountString == null)
            return null;

        int dotIndex = amountString.indexOf('.');
        int comaIndex = amountString.indexOf(',');

        if (dotIndex != -1 || comaIndex != -1) {
            return amountString.replaceAll((dotIndex > comaIndex)? "," : ".", "");
        }
        else
            return amountString;
    }
}
