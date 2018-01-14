package com.smsbalance.processing;

import java.util.ArrayList;

/**
 * Created by gioal on 16.07.2017.
 */

public class SMSParserMan {

    public class ParserRecord {

        public ParserRecord(String source, SMSParser parser){
            this.source = source;
            this.parser = parser;
        }
        public String source;
        public SMSParser parser;
    }

    protected ArrayList<ParserRecord> list = new ArrayList<>();

    public void AddParser(String source, SMSParser parser) {

        list.add(new ParserRecord(source, parser));
    }

    public SMSParser.Result parseSMS(String source, String body) {

        for(ParserRecord record: list) {
            if (record.source.compareTo(source) == 0) {
                SMSParser.Result result = record.parser.parse(body);

                if (result.isMatched())
                    return result;
            }
        }

        return null;
    }

    public static SMSParserMan createSMSParserMan() {

        SMSParser parser;
        SMSParserMan man = new SMSParserMan();

        parser = new SMSParser("tqveni baratit ...{card_mask} ganxorcielda gadaxdis operacia {amount_cur} {amount}. mimgebi:{pos} - xelmisatsvdomi nashti: {balance_cur} {balance}");
        man.AddParser("LibertyBank", parser);

        parser = new SMSParser("tqveni baratit ...{card_mask} bankomatidan {pos} gatanilia {amount_cur} {amount} - xelmisatsvdomi nashti: {balance_cur} {balance}");
        man.AddParser("LibertyBank", parser);

        //parser = new SMSParser("საბარათე ოპერაცია: {dummy_var} {dummy_var}\nბარათი: {card_desc}\nთანხა: {amount} {amount_cur}\n{pos}\nხელმისაწვდომი: {balance} {balance_cur}\nგმადლობთ");
        parser = new SMSParser("საბარათე ოპერაცია:\nთანხა: {amount} {amount_cur}\nბარათი: {card_desc}\n{pos}\nხელმისაწვდომი: {balance} {balance_cur}\nგმადლობთ");
        man.AddParser("TBC SMS", parser);

        return man;
    }

}
