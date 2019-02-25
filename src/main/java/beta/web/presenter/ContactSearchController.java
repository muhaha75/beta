/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beta.web.presenter;

import beta.server.eao.ContactEao;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * provide the search mechanismen for the ContactSearchView 
 * @author tjark.uschakow
 */
@Named
@ViewScoped
public class ContactSearchController implements Serializable {

    private final Logger L = LoggerFactory.getLogger(ContactSearchController.class);

    @Inject
    private ContactEao contactEao;
    @Inject
    private ContactController contactController;
   
    /**
     * Contains the searchTerm
     */
    private String searchTerm = "";
    /**
     * Returns a String that represents the searchTerm
     * @return 
     */
    public String getSearchTerm() {
        return searchTerm;
    }
    /**
     * Set the searchTerm
     * @param searchTerm 
     */
    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    /**
     * Performes the search in the contacts List
     */
    public void search() {

        contactController.setContacts(contactEao.find(searchTerm));

    }

    /**
     * Set the searchTerm to blank an reload the whole Contact-List
     */
    public void reset() {
        this.searchTerm = "";
        contactController.init();
    }

}
