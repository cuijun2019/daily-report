package com.etone.project.utils;

public class XMLTextMessage extends XMLMessage {
    public XMLTextMessage(String toUserName, String fromUserName, String msgType) {
        super(toUserName, fromUserName, msgType);
    }

    @Override
    public String subXML() {
        return null;
    }
}
