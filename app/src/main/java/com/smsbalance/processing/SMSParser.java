package com.smsbalance.processing;

import java.util.HashMap;

/**
 * Created by gioal on 15.07.2017.
 */

public class SMSParser {
    public class Result {

        private boolean matched = false;
        private String cardMask;
        private String cardDescription;
        private String pos;
        private String amount;
        private String amountCurrency;
        private String balance;
        private String balanceCurrency;

        public boolean isMatched() {
            return matched;
        }

        public void setMatched(boolean matched) {
            this.matched = matched;
        }

        public String getCardMask() {
            return cardMask;
        }

        public void setCardMask(String cardMask) {
            this.cardMask = cardMask;
        }

        public String getCardDescription() {
            return cardDescription;
        }

        public void setCardDescription(String cardDescription) {
            this.cardDescription = cardDescription;
        }

        public String getCardCoalesce() {
            return (cardMask == null) ? cardDescription : cardMask;
        }

        public String getPos() {
            return pos;
        }

        public void setPos(String pos) {
            this.pos = pos;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getAmountCurrency() {
            return amountCurrency;
        }

        public void setAmountCurrency(String amountCurrency) {
            this.amountCurrency = amountCurrency;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getBalanceCurrency() {
            return balanceCurrency;
        }

        public void setBalanceCurrency(String balanceCurrency) {
            this.balanceCurrency = balanceCurrency;
        }
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
            result.setMatched(true);
            result.setPos(getGroup(hash, "pos"));
            result.setCardMask(getGroup(hash, "card_mask"));
            result.setCardDescription(getGroup(hash, "card_desc"));
            result.setAmount(normalizeAmount(getGroup(hash, "amount")));
            result.setAmountCurrency(getGroup(hash, "amount_cur"));
            result.setBalance(normalizeAmount(getGroup(hash, "balance")));
            result.setBalanceCurrency(getGroup(hash, "balance_cur"));
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
