package com.mh.myapp.Module;

import java.io.Serializable;

public class Section_module implements Serializable {
    int section_id;
    String section;

    public Section_module(int section_id, String section) {
        this.section_id = section_id;
        this.section = section;
    }

    public int getSection_id() {
        return section_id;
    }

    public void setSection_id(int section_id) {
        this.section_id = section_id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
