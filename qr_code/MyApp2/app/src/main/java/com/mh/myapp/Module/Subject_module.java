package com.mh.myapp.Module;

import java.io.Serializable;

public class Subject_module implements Serializable {

    int subject_id;
    String code;
    String name;
    boolean status;

    public Subject_module(int subject_id, String code, String name,boolean status) {
        this.subject_id = subject_id;
        this.code = code;
        this.name = name;
        this.status=status;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
