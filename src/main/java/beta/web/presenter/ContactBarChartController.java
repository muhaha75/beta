/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beta.web.presenter;


import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

import javax.inject.Named;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for Contract
 *
 * @author jens.papenhagen
 */
@Named
@ViewScoped
public class ContactBarChartController implements Serializable {

    private static final Logger L = LoggerFactory.getLogger(ContactBarChartController.class);

    private BarChartModel barModel = null;
    
    @PostConstruct
    public void init() {
        
    }

    public void createBarChartModel(){
        
        
    barModel = new BarChartModel();
        
    ChartSeries male = new ChartSeries();
    male.setLabel("männlich");
        
    ChartSeries female = new ChartSeries();
    female.setLabel("weiblich");
        
    
    } 
    
    
}
