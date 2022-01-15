/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orangefinalsystemarchitech.com.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import orangefinalsystemarchitech.com.model.EnquiryCourse;

/**
 *
 * @author Ashish
 */
public interface EnquiryCourseRepository extends JpaRepository<EnquiryCourse, Long> {
    
}
