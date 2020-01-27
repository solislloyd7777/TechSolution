package com.mh.myapp.Module;

import java.io.Serializable;

public class Subject_module1 implements Serializable {

    int subject_id;
    String code;
    String name;

    public Subject_module1(int subject_id, String code, String name) {
        this.subject_id = subject_id;
        this.code = code;
        this.name = name;
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
}
