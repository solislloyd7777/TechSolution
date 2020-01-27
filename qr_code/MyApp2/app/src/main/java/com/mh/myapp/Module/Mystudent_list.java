package com.mh.myapp.Module;

public class Mystudent_list {

    String fname,mname,lname,student_num,password,code;
    int id;

    public Mystudent_list(String fname, String mname, String lname, String student_num, String password, String code,int id) {
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.student_num = student_num;
        this.password = password;
        this.code = code;
        this.id=id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getStudent_num() {
        return student_num;
    }

    public void setStudent_num(String student_num) {
        this.student_num = student_num;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
