package com.mh.myapp.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import com.mh.myapp.Module.Mystudent_list;
import com.mh.myapp.Module.Section_module;
import com.mh.myapp.Module.Subject_module;
import com.mh.myapp.Module.Subject_module1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String dbName = "Mydb.db";
    public static int dbVersion = 1;
    public static String dbPath = "";
    Context myContext;
    SQLiteDatabase db;
    Cursor cursor=null,curs=null;

    public DatabaseHelper(Context context) {
        super(context, dbName, null, dbVersion);
        myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            db.disableWriteAheadLogging();
        }
    }

    private boolean ExistDatabase() {
        File myFile = new File(dbPath + dbName);
        return myFile.exists();
    }

    private void CopyDatabase() {
        try {
            InputStream myInput = myContext.getAssets().open(dbName);
            OutputStream myOutput = new FileOutputStream(dbPath + dbName);
            byte[] myBuffer = new byte[2048];
            int length;
            while ((length = myInput.read(myBuffer)) > 0) {
                myOutput.write(myBuffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    public void StartWork(){
        dbPath=myContext.getFilesDir().getParent()+"/databases/";
        if(!ExistDatabase()){
            this.getWritableDatabase();
            CopyDatabase();
        }else{
            this.getWritableDatabase();
        }
    }


    public boolean checkqr(String qr) {
        boolean status=false;
        try{
            db=getReadableDatabase();
            curs=db.rawQuery("select code from student_info where code='"+qr+"'",null);
            if(curs.moveToFirst()){
                if(curs.getString(0).equals("")) {
                    status = true;
                }else{
                    status=false;
                }
            }else{
                status=true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return status;
    }

    public void insertToStudent_info(String fname, String mname, String lname, String student_num, String password, String code,String created,String created_by) {
        db=getWritableDatabase();
        try{
            db.execSQL("insert into student_info(fname,mname,lname,student_no,password,code,created,created_by) values('"+fname+"','"+mname+"','"+lname+"','"+student_num+"','"+password+"','"+code+"','"+created+"','"+created_by+"')");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean check_student_num(String student_num) {
        boolean status=false;
        try{
            db=getReadableDatabase();
            curs=db.rawQuery("select student_no from student_info where student_no='"+student_num+"'",null);
            if(curs.moveToFirst()){
                status=false;
            }else{
                status=true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return status;
    }

    public boolean checkmy_qr(String code) {
        boolean status=false;
        try{
            db=getReadableDatabase();
            curs=db.rawQuery("select code from student_info where code='"+code+"'",null);
            if(curs.moveToFirst()){
                if(curs.getString(0).equals("")) {
                    status = false;
                }else{
                    status=true;
                }
            }else{
                status=false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return status;
    }

    public boolean checkqr1(String qrcode) {
        boolean status=false;
        try{
            db=getReadableDatabase();
            curs=db.rawQuery("select code from student_info where not code='"+qrcode+"'",null);
            if(curs.moveToFirst()){
                if(curs.getString(0).equals("")) {
                    status = true;
                }else{
                    status=false;
                }
            }else{
                status=true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return status;

    }

    public void updateStudent_info(String fname, String mname, String lname, String student_num, String password, String code, String updated, String updated_by) {

        db=getWritableDatabase();
        try{
            db.execSQL("update student_info set fname='"+fname+"',mname='"+mname+"',lname='"+lname+"',student_no='"+student_num+"',password='"+password+"',code='"+code+"',updated='"+updated+"',updated_by='"+updated_by+"'");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String[] getMy_info(String mycode) {
        db=getReadableDatabase();
        String[] array=new String[7];
        try{
            cursor=db.rawQuery("select fname,mname,lname,student_no,password,code,student_info_id from student_info where code='"+mycode+"'",null);
            cursor.moveToFirst();
            array[0]=cursor.getString(0);
            array[1]=cursor.getString(1);
            array[2]=cursor.getString(2);
            array[3]=cursor.getString(3);
            array[4]=cursor.getString(4);
            array[5]=cursor.getString(5);
            array[6]=cursor.getString(6);
        }catch (Exception e){
            e.printStackTrace();
        }
        return array;
    }

    public String[] getMy_info1(String num) {
        db=getReadableDatabase();
        String[] array=new String[7];
        try{
            cursor=db.rawQuery("select fname,mname,lname,student_no,password,code,student_info_id from student_info where student_no='"+num+"'",null);
            cursor.moveToFirst();
            array[0]=cursor.getString(0);
            array[1]=cursor.getString(1);
            array[2]=cursor.getString(2);
            array[3]=cursor.getString(3);
            array[4]=cursor.getString(4);
            array[5]=cursor.getString(5);
            array[6]=cursor.getString(6);
        }catch (Exception e){
            e.printStackTrace();
        }
        return array;
    }

    public boolean check_student_num1(String student_num, int student_id) {
        boolean status=false;
        try{
            db=getReadableDatabase();
            curs=db.rawQuery("select student_no from student_info where student_no='"+student_num+"' and not student_info_id='"+student_id+"'",null);
            if(curs.moveToFirst()){
                    status=false;
            }else{
                status=true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return status;
    }

    public boolean checksubject(String code, String name) {
        boolean status=false;
        try{
            db=getReadableDatabase();
            curs=db.rawQuery("select subject_id from subject where subject_code='"+code+"' or subject_name='"+name+"'",null);
            if(curs.moveToFirst()){
                status=false;
            }else{
                status=true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return status;

    }

    public void insertSubject(String code, String name,String created,String created_by) {

        db=getWritableDatabase();
        try{
            db.execSQL("insert into subject(subject_code,subject_name,created,created_by) values('"+code+"','"+name+"','"+created+"','"+created_by+"')");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public boolean checkyear(String Year) {
        boolean status=false;
        try{
            db=getReadableDatabase();
            curs=db.rawQuery("select year_level_id from year_level where year='"+Year+"'",null);
            if(curs.moveToFirst()){
                status=false;
            }else{
                status=true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return status;
    }

    public void insertYear(String year, String created, String created_by) {
        db=getWritableDatabase();
        try{
            db.execSQL("insert into year_level(year,created,created_by) values('"+year+"','"+created+"','"+created_by+"')");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean checksection(String section) {
        boolean status=false;
        try{
            db=getReadableDatabase();
            curs=db.rawQuery("select section_id from section where section='"+section+"'",null);
            if(curs.moveToFirst()){
                status=false;
            }else{
                status=true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return status;
    }

    public void insertSection(String section, String created, String created_by) {

        db=getWritableDatabase();
        try{
            db.execSQL("insert into section(section,created,created_by) values('"+section+"','"+created+"','"+created_by+"')");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean checkcourse(String code, String name) {

        boolean status=false;
        try{
            db=getReadableDatabase();
            curs=db.rawQuery("select course_id from course where course_code='"+code+"' or course_name='"+name+"'",null);
            if(curs.moveToFirst()){
                status=false;
            }else{
                status=true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return status;
    }

    public void insertCourse(String code, String name, String created, String created_by) {
        db=getWritableDatabase();
        try{
            db.execSQL("insert into course(course_code,course_name,created,created_by) values('"+code+"','"+name+"','"+created+"','"+created_by+"')");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Mystudent_list> getStudent_list(int section,int subject_id) {
        List<Mystudent_list> content=new ArrayList<>();
        SQLiteDatabase db= getReadableDatabase();
        try {


            curs = db.rawQuery("SELECT fname,mname,lname,student_no,password,code,student_info_id from student_info where section_id='"+section+"'", null);
            curs.moveToFirst();
            if (curs.getCount() > 0) {
                do {
                    String fname=curs.getString(0);
                    String mname=curs.getString(1);
                    String lname=curs.getString(2);
                    String student_num=curs.getString(3);
                    String password=curs.getString(4);
                    String code=curs.getString(5);
                    int id=curs.getInt(6);

                    boolean check_student=this.check_student(id,subject_id);
                    if(check_student){
                        Mystudent_list student_list = new Mystudent_list(fname,mname,lname,student_num,password,code,id);

                        content.add(student_list);
                    }else {

                    }




                } while (curs.moveToNext());

            }
            if (curs!= null){
                curs.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return content;
    }

    private boolean check_student(int id, int subject_id) {
        db=getReadableDatabase();
        boolean status=false;
        try{
            cursor=db.rawQuery("select subject_taken_id from subject_taken where student_info_id='"+id+"' and subject_id='"+subject_id+"'",null);
            if(cursor.moveToFirst()){
                status=true;
            }else{
                status=false;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        if (cursor!= null){
            cursor.close();
        }
        return status;
    }

    public ArrayList<String> getCourse(){
        ArrayList<String>list=new ArrayList<String>();
        db=this.getReadableDatabase();
        db.beginTransaction();

        try{
            String selectQuery="select course_code from course order by course_code";
            cursor=db.rawQuery(selectQuery,null);
            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    String course=cursor.getString(0);
                    list.add(course);
                }
            }
            db.setTransactionSuccessful();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getYear(){
        ArrayList<String>list=new ArrayList<String>();
        db=this.getReadableDatabase();
        db.beginTransaction();

        try{
            String selectQuery="select year from year_level order by year";
            cursor=db.rawQuery(selectQuery,null);
            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    String year=cursor.getString(0);
                    list.add(year);
                }
            }
            db.setTransactionSuccessful();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getSection(){
        ArrayList<String>list=new ArrayList<String>();
        db=this.getReadableDatabase();
        db.beginTransaction();

        try{
            String selectQuery="select section from section order by section";
            cursor=db.rawQuery(selectQuery,null);
            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    String section=cursor.getString(0);
                    list.add(section);
                }
            }
            db.setTransactionSuccessful();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getSubjectlist(){
        ArrayList<String>list=new ArrayList<String>();
        db=this.getReadableDatabase();
        db.beginTransaction();

        try{
            String selectQuery="select subject_name from subject order by subject_name";
            cursor=db.rawQuery(selectQuery,null);
            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    String subject=cursor.getString(0);
                    list.add(subject);
                }
            }
            db.setTransactionSuccessful();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }



    public int getCourse_id(String name){
        int id=0;
        try{
            db=getReadableDatabase();
            cursor=db.rawQuery("select course_id from course where course_code='"+name+"'",null);
            cursor.moveToFirst();
            id=cursor.getInt(0);

        }catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    public int getYear_id(String name){
        int id=0;
        try{
            db=getReadableDatabase();
            cursor=db.rawQuery("select year_level_id from year where year='"+name+"'",null);
            cursor.moveToFirst();
            id=cursor.getInt(0);

        }catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    public int getSection_id(String name){
        int id=0;
        db=getReadableDatabase();
        try{

            cursor=db.rawQuery("select section_id from section where section='"+name+"'",null);
            cursor.moveToFirst();
            id=cursor.getInt(0);

        }catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    public void updateAdmin(String password, String code, String updated, String updated_by) {
        db=getWritableDatabase();
        try{
            db.execSQL("update admin set admin_password='"+password+"',admin_code='"+code+"',updated='"+updated+"',updated_by='"+updated_by+"'");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean check_admin(String old) {
        boolean status=false;
        try{
            db=getReadableDatabase();
            curs=db.rawQuery("select admin_password from admin where admin_password='"+old+"'",null);
            if(curs.moveToFirst()){
                status=true;
            }else{
                status=false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return status;
    }

    public void UpdateStudent(int section_id,String mycode) {
        db=getWritableDatabase();
        try{
            db.execSQL("update student_info set section_id='"+section_id+"' where code='"+mycode+"'");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void UpdateStudent1(int section_id,String num) {
        db=getWritableDatabase();
        try{
            db.execSQL("update student_info set section_id='"+section_id+"' where student_no='"+num+"'");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Subject_module> getSubject(int user_id) {
        List<Subject_module> content=new ArrayList<>();
        SQLiteDatabase db= getReadableDatabase();
        try {



            curs = db.rawQuery("select subject_id,subject_code,subject_name from subject order by subject_name", null);
            curs.moveToFirst();
            if (curs.getCount() > 0) {
                do {
                    int id=curs.getInt(0);
                    String subject_code=curs.getString(1);
                    String subject_name=curs.getString(2);
                    boolean status=this.check_take_subject(user_id,id);

                    Subject_module subject_module = new Subject_module(id,subject_code,subject_name,status);

                    content.add(subject_module);


                } while (curs.moveToNext());

            }else {
                Subject_module subject_module = new Subject_module(0, "", "No Subject Yet",false);

                content.add(subject_module);
            }
            if (curs!= null){
                curs.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return content;
    }

    public boolean check_take_subject(int id,int subject_id){
        boolean statu=false;
        db=getReadableDatabase();
        try{
            cursor=db.rawQuery("select subject_taken_id from subject_taken where subject_id='"+subject_id+"' and student_info_id='"+id+"'",null);
            if(cursor.moveToFirst()){
                statu=true;
            }else statu=false;
        }catch (Exception e){
            e.printStackTrace();
        }
        return statu;

    }

    public int getStudent_id(String mycode) {
        int id=0;
        try{
            db=getReadableDatabase();
            cursor=db.rawQuery("select student_info_id from student_info where code='"+mycode+"'",null);
            cursor.moveToFirst();
            id=cursor.getInt(0);

        }catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }


    public int getStudent_id1(String num) {
        int id=0;
        try{
            db=getReadableDatabase();
            cursor=db.rawQuery("select student_info_id from student_info where student_no='"+num+"'",null);
            cursor.moveToFirst();
            id=cursor.getInt(0);

        }catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    public String[] getStudent_name(int user_id) {
        String[] array=new String[3];
        db=getReadableDatabase();
        try{
            cursor=db.rawQuery("select fname,mname,lname from student_info where student_info_id='"+user_id+"'",null);
            cursor.moveToFirst();
            array[0]=cursor.getString(0);
            array[1]=cursor.getString(1);
            array[2]=cursor.getString(2);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  array;
    }

    public void insertSubject_taken(int user_id, int subject_id, String created, String created_by) {
        db=getWritableDatabase();
        try{
            db.execSQL("insert into  subject_taken(student_info_id,subject_id,updated,updated_by) values('"+user_id+"','"+subject_id+"','"+created+"','"+created_by+"')");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteSubject_taken(int user_id, int subject_id) {
        db=getWritableDatabase();
        try{
            db.execSQL("delete from subject_taken where student_info_id='"+user_id+"' and subject_id='"+subject_id+"'");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Subject_module1> getSubject1() {
        List<Subject_module1> content=new ArrayList<>();
        SQLiteDatabase db= getReadableDatabase();
        try {



            curs = db.rawQuery("select subject_id,subject_code,subject_name from subject order by subject_name", null);
            curs.moveToFirst();
            if (curs.getCount() > 0) {
                do {
                    int id=curs.getInt(0);
                    String subject_code=curs.getString(1);
                    String subject_name=curs.getString(2);

                    Subject_module1 subject_module1 = new Subject_module1(id,subject_code,subject_name);

                    content.add(subject_module1);


                } while (curs.moveToNext());

            }else {
                Subject_module1 subject_module = new Subject_module1(0, "", "No Subject Yet");

                content.add(subject_module);
            }
            if (curs!= null){
                curs.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return content;
    }

    public void deleteSubject_taken1(int subject_id) {
        db=getWritableDatabase();
        try{
            db.execSQL("delete from subject_taken where  subject_id='"+subject_id+"'");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateSubjects(String code, String name, int subject_id, String updated, String updated_by) {
        db=getWritableDatabase();
        try{
            db.execSQL("update subject set subject_code='"+code+"',subject_name='"+name+"',updated='"+updated+"',updated_by='"+updated_by+"' where subject_id='"+subject_id+"'");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean checksubject1(String code, String name, int subject_id) {
        boolean statu=false;
        db=getReadableDatabase();
        try{
            cursor=db.rawQuery("select subject_id from subject where subject_code='"+code+"' and subject_name='"+name+"' and not subject_id='"+subject_id+"'",null);
            if(cursor.moveToFirst()){
                statu=false;
            }else statu=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return statu;
    }

    public boolean checksubject2(String code, String name) {

        boolean statu=false;
        db=getReadableDatabase();
        try{
            cursor=db.rawQuery("select subject_id from subject where subject_code='"+code+"' and subject_name='"+name+"'",null);
            if(cursor.moveToFirst()){
                statu=false;
            }else statu=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return statu;
    }

    public void insertSubjects(String code, String name, String created, String created_by) {
        db=getWritableDatabase();
        try{
            db.execSQL("insert into subject(subject_code,subject_name,created,created_by) values('"+code+"','"+name+"','"+created+"','"+created_by+"')");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getSubject_id(String subject) {

        int id=0;
        try{
            db=getReadableDatabase();
            cursor=db.rawQuery("select subject_id from subject where subject_name='"+subject+"'",null);
            cursor.moveToFirst();
            id=cursor.getInt(0);

        }catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    public void deleteStudent(int id) {
        db=getWritableDatabase();
        try{
            db.execSQL("delete from student_info where student_info_id='"+id+"'");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Section_module> getSection_list() {
        List<Section_module> content=new ArrayList<>();
        SQLiteDatabase db= getReadableDatabase();
        try {


            curs = db.rawQuery("SELECT section_id,section from section", null);
            curs.moveToFirst();
            if (curs.getCount() > 0) {
                do {
                    int id=curs.getInt(0);
                    String section=curs.getString(1);

                        Section_module section_module = new Section_module(id,section);

                        content.add(section_module);

                } while (curs.moveToNext());

            }
            if (curs!= null){
                curs.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return content;
    }

    public void deleteSection(int section_id) {
        db=getWritableDatabase();
        try{
            db.execSQL("delete from section where section_id='"+section_id+"'");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean checkSection(String section, int section_id) {

        boolean statu=false;
        db=getReadableDatabase();
        try{
            cursor=db.rawQuery("select section_id from section where section='"+section+"' and not section_id='"+section_id+"'",null);
            if(cursor.moveToFirst()){
                statu=false;
            }else statu=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return statu;

    }

    public void updateSection(String name, int section_id, String updated, String updated_by) {
        db=getWritableDatabase();
        try{
            db.execSQL("update section set section='"+name+"',updated='"+updated+"',updated_by='"+updated_by+"' where section_id='"+section_id+"'");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean checkSection2(String name) {
        boolean statu=false;
        db=getReadableDatabase();
        try{
            cursor=db.rawQuery("select section_id from section where section='"+name+"'",null);
            if(cursor.moveToFirst()){
                statu=false;
            }else statu=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return statu;
    }

    public void deleteSubject(int subject_id) {
        db=getWritableDatabase();
        try{
            db.execSQL("delete from subject where  subject_id='"+subject_id+"'");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String[] getStudent(int student_id) {
        String[] array=new String[3];
        db=getReadableDatabase();
        try{
            curs=db.rawQuery("select fname,mname,lname from student_info where student_id='"+student_id+"'",null);
            curs.moveToFirst();
            array[0]=curs.getString(0);
            array[1]=curs.getString(1);
            array[2]=curs.getString(2);
        }catch (Exception e){
            e.printStackTrace();
        }
        curs.close();

        return array;
    }

    public String[] getIdentifier() {
        db=getReadableDatabase();
        String[] array=new String[3];
        try{
            cursor=db.rawQuery("select active,present,late from identifier",null);
            cursor.moveToFirst();
            array[0]=cursor.getString(0);
            array[1]=cursor.getString(1);
            array[2]=cursor.getString(2);

        }catch (Exception e){
            e.printStackTrace();
        }
        return array;
    }

    public void updateidentifier(int section,int subject) {
        db=getWritableDatabase();
        try{
            db.execSQL("update identifier set section_id='"+section+"', subject_id='"+subject+"',active='Y', present='N',late='N'");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateidentifier1() {
        db=getWritableDatabase();
        try{
            db.execSQL("update identifier set  present='Y',late='N'");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateidentifier2() {
        db=getWritableDatabase();
        try{
            db.execSQL("update identifier set  present='N',late='Y'");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void updateidentifier3() {
        db=getWritableDatabase();
        try{
            db.execSQL("update identifier set  subject_id=0,section_id=0,active='N',present='N',late='N'");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public String[] getIds() {
        String[] array=new String[5];
        db=getReadableDatabase();
        try{
            cursor=db.rawQuery("select section_id,subject_id,active,present,late from identifier",null);
            cursor.moveToFirst();
            array[0]=cursor.getString(0);
            array[1]=cursor.getString(1);
            array[2]=cursor.getString(2);
            array[3]=cursor.getString(3);
            array[4]=cursor.getString(4);
        }catch (Exception e){
            e.printStackTrace();
        }
        return array;
    }

    public String getSection_name(int section_id) {
        db=getReadableDatabase();
        String name = null;
        try{
            cursor=db.rawQuery("select section from section where section_id='"+section_id+"'",null);
            cursor.moveToFirst();
            name=cursor.getString(0);
            
        }catch (Exception e){
            e.printStackTrace();
        }
        return name;
    }

    public String getSubject_name(int subject_id) {
        db=getReadableDatabase();
        String name = null;
        try{
            cursor=db.rawQuery("select subject_name from subject where subject_id='"+subject_id+"'",null);
            cursor.moveToFirst();
            name=cursor.getString(0);

        }catch (Exception e){
            e.printStackTrace();
        }
        return name;
    }

    public String getStudent_name1(String mycode) {

        db=getReadableDatabase();
        String name = null;
        try{
            cursor=db.rawQuery("select fname from student_info where code='"+mycode+"'",null);
            cursor.moveToFirst();
            name=cursor.getString(0);

        }catch (Exception e){
            e.printStackTrace();
        }
        return name;
    }

    public String getStudent_name2(String num) {

        db=getReadableDatabase();
        String name = null;
        try{
            cursor=db.rawQuery("select fname from student_info where student_no='"+num+"'",null);
            cursor.moveToFirst();
            name=cursor.getString(0);

        }catch (Exception e){
            e.printStackTrace();
        }
        return name;
    }

    public boolean checkbelong(int section, int subject) {

        db=getReadableDatabase();
        boolean status=false;
        try{
            cursor=db.rawQuery("select student_info_id from student_info where section_id='"+section+"'",null);
            if(cursor.moveToFirst()){
                boolean check=this.checkuser(cursor.getInt(0),subject);
                if(check){
                    status=true;
                }else{
                    status=false;
                }
            }else{
                status=false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return status;
    }

    private boolean checkuser(int student_id, int subject) {
        db=getReadableDatabase();
        boolean status=false;
        try{
            curs=db.rawQuery("select * from subject_taken where student_id='"+student_id+"' and subject_id='"+subject+"'",null);
            if(curs.moveToFirst()){
                status=true;
            }else{
                status=false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        curs.close();
        return status;
    }

    public void insertIntoTime_log(int student_id, int section_id, int subject_id, String mydate,String status, String created, String name) {
        db=getWritableDatabase();
        try{
            db.execSQL("insert into time_log(student_id,section_id,subject_id,mydate,status,created,created_by) values('"+student_id+"','"+section_id+"','"+subject_id+"','"+mydate+"','"+status+"','"+created+"','"+name+"')");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getStatus() {
        db=getReadableDatabase();
        String status = null;
        try{
            cursor=db.rawQuery("select present,late from identifier ",null);
            if(cursor.moveToFirst()){
                if(cursor.getString(0).equals("N") && cursor.getString(1).equals("Y")){
                    status="L";
                }else if(cursor.getString(0).equals("Y") && cursor.getString(1).equals("N")){
                    status="P";
                }
            }else{

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return status;
    }

    public boolean check_attendance_mode() {

        db=getReadableDatabase();
        boolean status=false;
        try{
            cursor=db.rawQuery("select active from identifier",null);
           cursor.moveToFirst();
           if(cursor.getString(0).equals("Y")){
               status=true;
           }else{
               status=false;
           }
        }catch (Exception e){
            e.printStackTrace();
        }
        cursor.close();
        return status;
    }

    public boolean check_user_pass(String pass, String num) {
        db=getReadableDatabase();
        boolean status=false;
        try{
            curs=db.rawQuery("select * from student_info where password='"+pass+"' and student_no='"+num+"'",null);
            if(curs.moveToFirst()){
                status=true;
            }else{
                status=false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        curs.close();
        return status;
    }

    public boolean getadmin_qr(String contents) {
        db=getReadableDatabase();
        boolean status=false;
        try{
            cursor=db.rawQuery("select * from admin where admin_code='"+contents+"'",null);

            if(cursor.moveToFirst()){
                status=true;
            }else{
                status=false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        cursor.close();
        return status;
    }
}
