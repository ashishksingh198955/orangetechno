package orangefinalsystemarchitech.com.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import orangefinalsystemarchitech.com.dao.CourseRepository;
import orangefinalsystemarchitech.com.dao.EnquiryRepository;
import orangefinalsystemarchitech.com.dao.EnquirySourceRepository;
import orangefinalsystemarchitech.com.dao.FollowupRepository;
import orangefinalsystemarchitech.com.dao.InstituteRepository;
import orangefinalsystemarchitech.com.dto.EnquiryCommand;
import orangefinalsystemarchitech.com.model.Course;
import orangefinalsystemarchitech.com.model.EnquirySource;
import orangefinalsystemarchitech.com.model.Institute;
import orangefinalsystemarchitech.com.service.CommonService;

/**
 *
 * @author Ashish
 */
@Controller
public class EnquiryController {

    @Autowired
    InstituteRepository instituteRepository;

    @Autowired
    EnquirySourceRepository enquirySourceRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CommonService commonService;

    @Autowired
    EnquiryRepository enquiryRepository;

    @Autowired
    FollowupRepository followupRepository;

    @GetMapping("/")
    public String index(Model m) {
        m.addAttribute("cmd", new EnquiryCommand());
        return "/index"; //index.html page
    }

    @GetMapping("/save-enquiry")  
    public String save(@ModelAttribute EnquiryCommand cmd) {
        commonService.saveEnquiry(cmd);
        return "redirect:/enq-list";
    }

    @GetMapping("/get-courses")
    @ResponseBody
    public List<Course> getCoursesByInstituteId(@RequestParam Long instId) {
        return courseRepository.findCourseListByInstitute_instituteId(instId);
    }

    @ModelAttribute("instituteList")
    public List<Institute> getInstList() {
        return instituteRepository.getInstCostomList();
    }

    @ModelAttribute("sourceList")
    public List<EnquirySource> getSourceList() {
        return enquirySourceRepository.findAll();
    }

    @GetMapping("/enq-list")
    public String enquiryList(@RequestParam(required = false) Long instId, Model m) {
        m.addAttribute("enquiryList", enquiryRepository.getEnquiryDTOList(instId));
        return "/enq-list"; //html
    }

    @GetMapping("/test-get-inst-ids")
    @ResponseBody
    public String getInstIds() {
        return enquiryRepository.findAllIds();
    }

    @PostMapping("/save-followup")
    public String saveFollowup(@RequestParam Long enquiryId, @RequestParam String followup, @RequestParam(required = false) String go) {
        commonService.saveFollowup(enquiryId, followup);
        if (go != null && go.equals("eview")) {
            return "redirect:/enquiry-detail/" + enquiryId;
        } else {
            return "redirect:/enq-list";
        }
    }

    @GetMapping("/enquiry-detail/{id}")
    public String enquiryDetail(@PathVariable Long id, Model m) {
        m.addAttribute("dataMap", enquiryRepository.getEnquiryDetailMap(id));
        m.addAttribute("followupList", followupRepository.getFollowupsByEnquiryId(id));
        return "/enquiry-detail"; //html
    }
    
    @GetMapping("/enquiry-delete/{id}")
    public String deleteEnquiry(@PathVariable Long id) {
        commonService.deleteEnquiry(id);
        return "redirect:/enq-list";
    }

    @GetMapping("/test-enquiry-detail/{id}")
    @ResponseBody
    public Map<String, Object> getEnqMap(@PathVariable Long id) {
        return enquiryRepository.getEnquiryDetailMap(id);
    }
}
