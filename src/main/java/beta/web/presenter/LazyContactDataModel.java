/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beta.web.presenter;

import beta.server.eao.ContactEao;
import beta.server.entity.Contact;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provide the data for the lazy loaded DataTable
 *
 * @author tjark.uschakow
 */
public class LazyContactDataModel extends LazyDataModel<Contact> {

    private final Logger L = LoggerFactory.getLogger(LazyContactDataModel.class);

    private ContactEao contactEao;

    public LazyContactDataModel(ContactEao cont) {

        this.contactEao = cont;

    }

    /**
     * Load the Data for the DataTable
     *
     * @param first
     * @param pageSize
     * @param sortField
     * @param sortOrder
     * @param filters
     * @return
     */
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

}
