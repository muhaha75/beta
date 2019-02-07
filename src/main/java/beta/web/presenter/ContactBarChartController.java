/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beta.web.presenter;

import beta.server.eao.ContactEao;
import beta.server.entity.Sex;
import java.io.Serializable;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import javax.inject.Named;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
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

    @Inject
    private ContactEao eao;

    private BarChartModel barModel = null;

    @PostConstruct
    public void init() {
        createBarChartModel();
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void createBarChartModel() {
        
        barModel = new BarChartModel();

        ChartSeries male = new ChartSeries();
        male.setLabel("männlich");
        for (int i = 0; i < 100000; i += 1000) {
            male.set(i, countContactsInRange(Sex.MALE, i, 1000));
        }

        ChartSeries female = new ChartSeries();
        female.setLabel("weiblich");
        for (int i = 0; i < 100000; i += 1000) {
            female.set(i, countContactsInRange(Sex.FEMALE, i, 1000));
        }

        barModel.addSeries(male);
        barModel.addSeries(female);
        
        barModel.setTitle("Kontakte in Postleitzahlenbereich");
        Axis xAxis = barModel.getAxis(AxisType.X);
        Axis yAxis = barModel.getAxis(AxisType.Y);
        xAxis.setLabel("PLZ-Bereich");
        yAxis.setLabel("Kontakte");
        
    }

    /**
     * Returns the count of Contacts with the given sex from the start-Postcode
     * in range of range
     *
     * @param sex The sex of the Contacts
     * @param start the start postcode
     * @param range the range of the postcode
     * @return int count of contacts
     */
    private int countContactsInRange(Sex sex, int start, int range) {

        int count = eao.find("")
                .stream()
                .filter(contact -> contact.getSex().equals(sex))
                .flatMap(contact -> contact.getAddresses()
                .stream().filter(address -> ((Integer.parseInt(address.getZipCode()) < (start + range)) && ((Integer.parseInt(address.getZipCode()) >= start)))))
                .collect(Collectors.toList()).size();

        return count;
    }

}
