package com.vinnovations.voicenotesjava;

public class NotesEntity {
    private int id;
    private String topicName;
    private String topicDesc;

    public NotesEntity(int id, String topicName, String topicDesc) {
        this.id = id;
        this.topicName = topicName;
        this.topicDesc = topicDesc;
    }

    public NotesEntity(String topicName, String topicDesc) {
        this.topicName = topicName;
        this.topicDesc = topicDesc;
    }

    public NotesEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicDesc() {
        return topicDesc;
    }

    public void setTopicDesc(String topicDesc) {
        this.topicDesc = topicDesc;
    }
}
