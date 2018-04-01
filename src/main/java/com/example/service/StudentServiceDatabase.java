package com.example.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.ProdiMapper;
import com.example.dao.StudentMapper;
import com.example.model.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StudentServiceDatabase implements StudentService
{
    @Autowired
    private StudentMapper studentMapper;
    private ProdiMapper prodiMapper;
    
    @Override
    public ProdiModel selectProdiName(Integer id_prodi)
    {
    	log.info ("select prodi_name with id_prodi {}", id_prodi);
        return prodiMapper.selectProdiName(id_prodi);
    }
    
    //ini uda dipake
    @Override
    public StudentModel selectStudent (String npm)
    {
        log.info ("select student with npm {}", npm);
        return studentMapper.selectStudent (npm);
    }
    
    //ini uda dipake
    @Override
    public ProdiModel selectProdi (Integer id_prodi)
    {
        log.info ("select prodi name with id {}", id_prodi);
        return studentMapper.selectProdi(id_prodi);
    }
    
    //ini uda dipake
    @Override
    public FakultasModel selectFakultas (Integer id_fakultas)
    {
    	log.info ("select fakultas with id {}", id_fakultas);
        return studentMapper.selectFakultas(id_fakultas);
    }

    @Override
    public List<StudentModel> selectAllStudents ()
    {
        log.info ("select all students");
        return studentMapper.selectAllStudents ();
    }
    
    //ini uda dipake
    @Override
    public void addStudent (StudentModel mahasiswa)
    {
        studentMapper.addStudent (mahasiswa);
    }


    @Override
    public void deleteStudent (String npm)
    {
    	log.info("student "+npm+" deleted");
    	studentMapper.deleteStudent(npm);
    }
    
    @Override
    public void updateStudent (StudentModel student)
    {
    	log.info("update student");
    	studentMapper.updateStudent(student);
    }  
    
    @Override
    public String selectNpm (String npm)
    {
    	log.info("select NPM like (to calculate)");
    	return studentMapper.selectNpm(npm);
    }  
}