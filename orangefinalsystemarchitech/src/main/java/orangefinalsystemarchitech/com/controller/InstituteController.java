package orangefinalsystemarchitech.com.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import orangefinalsystemarchitech.com.dao.InstituteRepository;
import orangefinalsystemarchitech.com.dto.InstituteDto;
import orangefinalsystemarchitech.com.model.Institute;
import orangefinalsystemarchitech.com.service.CommonService;

/**
 *
 * @author Ashish
 */
@RestController
public class InstituteController {

    @Autowired
    CommonService commonService;
    
    @Autowired
    InstituteRepository instituteRepository;

    @PostMapping("/api/saveinstitute")
    public String save(@RequestBody Institute inst) {
        inst.getContact().setName(inst.getName());
        inst.setDoe(new Date());
        commonService.saveInstitute(inst);
       
        return "Data Saved Succefully";
    }
    @GetMapping("/api/institute-list")
    public List<InstituteDto> list() {
    	List<InstituteDto> insituteDtoList=	instituteRepository.getInstList();
        return insituteDtoList;
    }
}
