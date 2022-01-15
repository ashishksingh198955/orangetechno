/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orangefinalsystemarchitech.com.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import orangefinalsystemarchitech.com.dao.AddressRepository;
import orangefinalsystemarchitech.com.dao.ContactRepository;
import orangefinalsystemarchitech.com.dao.CourseRepository;
import orangefinalsystemarchitech.com.dao.EnquiryCourseRepository;
import orangefinalsystemarchitech.com.dao.EnquiryRepository;
import orangefinalsystemarchitech.com.dao.EnquirySourceRepository;
import orangefinalsystemarchitech.com.dao.FollowupRepository;
import orangefinalsystemarchitech.com.dao.InstituteRepository;
import orangefinalsystemarchitech.com.dto.EnquiryCommand;
import orangefinalsystemarchitech.com.model.Address;
import orangefinalsystemarchitech.com.model.Contact;
import orangefinalsystemarchitech.com.model.Enquiry;
import orangefinalsystemarchitech.com.model.EnquiryCourse;
import orangefinalsystemarchitech.com.model.Followup;
import orangefinalsystemarchitech.com.model.Institute;

/**
 *
 * @author Ashish
 */
@Service
public class CommonService {

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    InstituteRepository instituteRepository;
    @Autowired
    EnquirySourceRepository enquirySourceRepository;
    @Autowired
    EnquiryRepository enquiryRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    EnquiryCourseRepository enquiryCourseRepository;
    @Autowired
    FollowupRepository followupRepository;

    @Transactional
    public void saveInstitute(Institute inst) {
        //address
        addressRepository.save(inst.getContact().getPermanentAddress());
        //contact
        contactRepository.save(inst.getContact());
        //institute
        instituteRepository.save(inst);
    }

    @Transactional
    public void saveEnquiry(EnquiryCommand cmd){
        Address a = cmd.getEnquiry().getContact().getPermanentAddress();
        addressRepository.save(a);
        
        Contact c = cmd.getEnquiry().getContact();
        contactRepository.save(c);
        
        Enquiry e = cmd.getEnquiry();
        e.setInstitute(instituteRepository.getOne(e.getInstitute().getInstituteId()));
        e.setEnquirySource(enquirySourceRepository.getOne(e.getEnquirySource().getEnquirySourceId()));
        Date d = new Date();
        e.setDoe(d);
        e.setLastUpdate(d);        
        enquiryRepository.save(e);
        
        Long[] courseIds = cmd.getCourses();
        for (Long courseId : courseIds) {
            EnquiryCourse ec = new EnquiryCourse();
            ec.setEnquiry(e);
            ec.setCourse(courseRepository.getOne(courseId));
            enquiryCourseRepository.save(ec);
        }
        
    }   
    @Transactional
    public void saveFollowup(Long enquiryId, String detail){
        Followup fo = new Followup();
        fo.setDetail(detail);
        fo.setEnquiry(enquiryRepository.getOne(enquiryId)); //FK
        fo.setDoe(new Date());
        followupRepository.save(fo);
    }    
    
    @Transactional
    public void deleteEnquiry(Long enquiryId){
        Enquiry enquiry = enquiryRepository.findById(enquiryId).get();
        Contact contact = enquiry.getContact();
        //delete contact
        contactRepository.delete(contact);
        //delete enquiry
        enquiryRepository.delete(enquiry);        
    }    
    
}
