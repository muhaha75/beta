/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beta.web.presenter;

import beta.server.eao.ContactEao;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import javax.inject.Named;

import org.primefaces.model.LazyDataModel;

/**
 * provide the resources for the filterable ContactView.xhtml pagination and
 * lazy added
 *
 * @author tjark.uschakow
 */
@Named
@ViewScoped
public class LazyContactFilterController implements Serializable {

    /**
     * The connection to the Database
     */
    @Inject
    private ContactEao contactEao;
    /**
     * LazyDataModel
     */
    private LazyDataModel model;

    public LazyContactFilterController() {

    }

    /**
     * Get the Contact List from ConatactEao
     */
    @PostConstruct
    public void init() {

        model = new LazyContactDataModel(contactEao);
    }

    public LazyDataModel getModel() {
        return model;
    }

    public void setModel(LazyContactDataModel model) {
        this.model = model;
    }

}
