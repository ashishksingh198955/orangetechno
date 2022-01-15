/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orangefinalsystemarchitech.com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import orangefinalsystemarchitech.com.dto.InstituteDto;
import orangefinalsystemarchitech.com.model.Institute;

/**
 *
 * @author Ashish
 */
public interface InstituteRepository extends JpaRepository<Institute, Long> {
    @Query("SELECT i.instituteId AS instituteId, i.name AS name, i.contact.phone AS phone, i.contact.email AS email, i.contact.permanentAddress.city AS city  FROM Institute  AS i ")
    public List<InstituteDto> getInstList();
    
    @Query("SELECT new orangefinalsystemarchitech.com.model.Institute(i.instituteId, i.name)  FROM Institute  AS i ")
    public List<Institute> getInstCostomList();
}
