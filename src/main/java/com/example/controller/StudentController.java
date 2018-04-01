package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.*;
import com.example.service.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class StudentController
{
    @Autowired
    StudentService studentDAO;

    @RequestMapping("/")
    public String index ()
    {
        return "index";
    }


    @RequestMapping("/mahasiswa/tambah")
    public String add(Model model)
    {
    	StudentModel mahasiswa = new StudentModel();
    	model.addAttribute ("mahasiswa", mahasiswa);
        return "form-add";
    }


    @RequestMapping("/mahasiswa/tambah/submit")
    public String addSubmit (@ModelAttribute StudentModel mahasiswa)
    { 	
    	ProdiModel prodi = studentDAO.selectProdi(mahasiswa.getId_prodi());
    	String tahun_masuk = mahasiswa.getTahun_masuk().substring(2);
    	log.info("tahun_masuk " +tahun_masuk);
    	String kode_univ = prodi.getFakultas().getUniversitas().getKode_univ();
    	log.info("kode_univ " +kode_univ);
    	String kode_prodi = prodi.getKode_prodi();
    	log.info("kode_prodi " +kode_prodi);
    	String jalur_masuk = getJalurMasuk(mahasiswa.getJalur_masuk());
    	log.info("jalur_masuk " +mahasiswa.getJalur_masuk());
    	log.info("id jalur_masuk " +jalur_masuk);

    	String npm_sementara = tahun_masuk + kode_univ + kode_prodi + jalur_masuk;
    	log.info("npm_sementara " +npm_sementara);
    	
    	String max_npm = studentDAO.selectNpm(npm_sementara);
    	log.info("max_npm " +max_npm);
    	String nomor_urut = getNomorUrut(max_npm);
    	log.info("nomor_urut " +nomor_urut);
    	
    	String npm = npm_sementara + nomor_urut;
    	log.info("npm " +npm);
    	
    	mahasiswa.setNpm(npm);
    	
    	if(studentDAO.selectStudent(npm) == null) {
    		studentDAO.addStudent (mahasiswa);
            return "success-add";
    	}else {
    		return "already-add";
    	}    
    }
    
    public String getNomorUrut(String max_npm) {
    	String result = "0";
    	String nomor_urut = max_npm.substring(9);
    	String new_nomor_urut = String.valueOf(Integer.parseInt(nomor_urut) + 1) ;
    	if(new_nomor_urut.length()==1) {
    		result = "00"+new_nomor_urut;
    	}else if(new_nomor_urut.length()==2) {
    		result = "0"+new_nomor_urut;
    	}else {
    		result = new_nomor_urut;
    	}
    	return result;
    }
    
    public String getJalurMasuk(String jalur_masuk) {
    	String id = "0";
    	if(jalur_masuk.equals("Undangan Olimpiade")) {
    		id = "53";
    	}else if(jalur_masuk == "Undangan Reguler/SNMPTN") {
    		id = "54";
    	}else if(jalur_masuk == "Undangan Paralel/PPKB") {
    		id = "55";
    	}else if(jalur_masuk == "Ujian Tulis Bersama/SBMPTN") {
    		id = "57";
    	}else if(jalur_masuk == "Ujian Tulis Mandiri") {
    		id = "62";
    	}
    	return id;
    }
    
    @RequestMapping("/mahasiswa")
	public String viewByNPM(Model model, @RequestParam(value="npm", required=true)String npm) {
		StudentModel mahasiswa = studentDAO.selectStudent(npm);
		if (mahasiswa != null) {
            model.addAttribute ("mahasiswa", mahasiswa);
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
	}
    
    //*****************************
    //BELUM DIPAKE
    //*****************************
    @RequestMapping("/student/view")
    public String view (Model model, @RequestParam(value = "npm", required = false) String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);
        if (student != null) {
            model.addAttribute ("student", student);
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }

    
    @RequestMapping("/student/viewall")
    public String view (Model model)
    {
    	log.info("test");
        List<StudentModel> students = studentDAO.selectAllStudents ();
        model.addAttribute ("students", students);

        return "viewall";
    }


    @RequestMapping("/student/delete/{npm}")
    public String delete (Model model, @PathVariable(value = "npm") String npm)
    {
    	StudentModel student = studentDAO.selectStudent(npm);
        if (student != null) {
        	studentDAO.deleteStudent (npm);
            return "delete";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }
    
    @RequestMapping("/student/update/{npm}")
    public String update (Model model, @PathVariable(value = "npm") String npm)
    {
    	StudentModel student = studentDAO.selectStudent(npm);
        if (student != null) {
        	model.addAttribute ("student", student);
            return "form-update";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }
    
    @RequestMapping(value="/student/update/submit", method= RequestMethod.POST)
    public String updateSubmit (@ModelAttribute StudentModel student
           /* @RequestParam(value = "npm", required = false) String npm,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "gpa", required = false) double gpa*/)
    {
    	//StudentModel student = new StudentModel (npm, name, gpa);
        studentDAO.updateStudent(student);
        return "success-update";
    }
}
