/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.victor.fishhub.service.sheduler;

import com.victor.fishhub.dao.entity.Location;


public interface SheduledAction {
    
    void preExecution();
    
    void executeApiRequest();
    
    void postExecution();
    
}
