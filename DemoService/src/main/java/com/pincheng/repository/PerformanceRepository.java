/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pincheng.repository;

import Entity.Performance;
import Entity.User;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author User
 */
@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Object> {
    
    @Query("select p from Performance p where p.eventTime >= :startTime and p.eventTime <= :endTime and p.uid = :uid")
    public List<Performance> findByDashBoard(@Param("startTime") Date startTimeDate, @Param("endTime") Date endTimeDate, @Param("uid") User user);

}

