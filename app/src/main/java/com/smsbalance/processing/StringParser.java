package com.smsbalance.processing;

import java.util.HashMap;
import java.util.Hashtable;

/**
 * Created by gioal on 16.07.2017.
 */

public class StringParser {
    public class Node {

        public Node() {
            next = null;
            prev = null;
            start = -1;
            end = -1;
        }

        public String text;
        public String name;

        public int start;
        public int end;

        public Node next;
        public Node prev;
    }

    private Node listStart = null;

    public Node getStartNode() { return listStart;}

    public void createPattern(String pattern) {

        listStart = new Node();

        Node current = listStart;

        current.start = 0;
        next(current, pattern, current.start);

    }

    public HashMap<String, String> parse(String text) {

        if (listStart == null)
            return null;

        HashMap<String, String> table = new HashMap<>();
        Node node = listStart;

        int lastIndex = 0;
        while(node != null && node.end != -1) {

            int startIndex = lastIndex + node.text.length();
            int endIndex = (node.next != null && node.next.text.length() != 0)
                    ? text.indexOf(node.next.text, startIndex)
                    : text.length();
            if (endIndex == -1) {
                // not matched
                return null;
            }

            table.put(node.name, text.substring(startIndex, endIndex));

            lastIndex = endIndex;
            node = node.next;
        }

        return table;
    }

    private void next(Node last, String pattern, int from) {

        int openBrace = pattern.indexOf("{", from);
        if (openBrace == -1) {
            last.text = pattern.substring(from);
            return;
        }

        int closeBrace = pattern.indexOf("}", openBrace);

        last.end = openBrace;
        last.text = pattern.substring(last.start, last.end);
        last.name = pattern.substring(openBrace+1, closeBrace);

        Node current = new Node();
        current.prev = last;
        current.start = closeBrace+1;

        last.next = current;

        next(current, pattern, closeBrace+1);
    }

}
