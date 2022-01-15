/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orangefinalsystemarchitech.com.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import orangefinalsystemarchitech.com.model.Followup;

/**
 *
 * @author Ashish
 */
public interface FollowupRepository extends JpaRepository<Followup, Long> {

    @Query("SELECT new Followup(f.followupId, f.doe, f.detail) FROM Followup as f WHERE f.enquiry.enquiryId=?1 ORDER BY f.doe desc")
    List<Followup> getFollowupsByEnquiryId(Long enquiryId);
}
