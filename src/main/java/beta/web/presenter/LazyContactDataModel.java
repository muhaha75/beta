/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beta.web.presenter;

import beta.server.eao.ContactEao;
import beta.server.entity.Contact;
import beta.server.entity.Country;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tjark.uschakow
 */
public class LazyContactDataModel extends LazyDataModel<Contact> {

    private final Logger L = LoggerFactory.getLogger(LazyContactDataModel.class);

    private ContactEao contactEao;

    public LazyContactDataModel(ContactEao cont) {
        
        this.contactEao = cont;
        
    }

    @Override
    public List<Contact> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        L.info("inside Load");
        L.info("Filter Values: {}", filters);       
        L.info("Rowcount: {}", this.getRowCount());

        
        List<Contact> filteredList;

       
        filteredList = contactEao.filterContactsInRange(filters, first, first + pageSize);
        
        this.setRowCount(contactEao.getRowCount());
        return filteredList;
    }

    /**
     * Filter the DataList and return the filtered list
     *
     * @param filters
     * @return
     */
    public Map<String, Object> prepareFilter(Map<String, Object> filters) {

        L.info("inside prepareFilter");
        L.info("Filter: {}", filters);
        for (Map.Entry<String, Object> entry : filters.entrySet()) {

            switch (entry.getKey()) {
                case "sex":
                    break;
                case "country":
                    break;
                default:
                    break;
            }
        }
        return null;
    }

}
