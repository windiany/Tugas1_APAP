package com.example.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.model.*;

public interface StudentService
{
    StudentModel selectStudent (String npm);
    
    ProdiModel selectProdi (Integer id_prodi);
    
    FakultasModel selectFakultas (Integer id_fakultas);
    
    String selectNpm (String npm);

    List<StudentModel> selectAllStudents ();

    void addStudent (StudentModel mahasiswa);

    void deleteStudent (String npm);
    
    void updateStudent (StudentModel student);
    
    ProdiModel selectProdiName (Integer id_prodi); 
}