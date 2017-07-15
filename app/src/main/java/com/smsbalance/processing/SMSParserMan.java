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

    protected ArrayList<ParserRecord> list = new ArrayList<ParserRecord>();

    public void AddParser(String source, SMSParser parser) {

        list.add(new ParserRecord(source, parser));
    }

    public SMSParser.Result parseSMS(String source, String body) {

        for(ParserRecord record: list) {
            if (record.source.compareTo(source) == 0) {
                SMSParser.Result result = record.parser.parse(body);

                if (result.matched)
                    return result;
            }
        }

        return null;
    }

}
