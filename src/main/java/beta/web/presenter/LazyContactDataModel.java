/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beta.web.presenter;

import beta.server.entity.Contact;
import beta.server.entity.Sex;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
    
    private List<Contact> data;
    
    public LazyContactDataModel(List<Contact> data) {
        this.data = data;
        this.setRowCount(this.data.size());
        L.info("LazyModelCreated");
    }
    
    @Override
    public List<Contact> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        L.info("inside Load");
        
        List<Contact> filteredList;
        
        filteredList = filterList(filters);
        
        this.setRowCount(filteredList.size());
        
        if (this.getRowCount() < this.getPageSize()) {
            this.setPageSize(this.getRowCount());
            return filteredList;
        } else {
            return filteredList.subList(first, first + pageSize);
        }
        
    }

    /**
     * Filter the DataList and return the filtered list
     *
     * @param filters
     * @return
     */
    public List<Contact> filterList(Map<String, Object> filters) {
        List<Contact> filteredList = new ArrayList<>(data);
        L.info("inside Filter");
        L.info("Filter: {}",filters);
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            
            switch (entry.getKey()) {
                case "title":
                    filteredList = filteredList.stream()
                            .filter(cont -> cont.getTitle().equals(entry.getValue()))
                            .collect(Collectors.toList());
                    break;
                case "firstName":
                    filteredList = filteredList.stream()
                            .filter(cont -> cont.getFirstName().contains((String) entry.getValue()))
                            .collect(Collectors.toList());
                    break;
                case "lastName":
                    filteredList = filteredList.stream()
                            .filter(cont -> cont.getLastName().contains((String) entry.getValue()))
                            .collect(Collectors.toList());
                    break;
                case "translateSex(contact)":
                    filteredList = filteredList.stream()
                            .filter(cont -> cont.getSex().equals(((String) entry.getValue()).equals("männlich") ? Sex.MALE : Sex.FEMALE))
                            .collect(Collectors.toList());
                    break;
                case "streetsToString(contact)":
                    filteredList = filteredList.stream()
                            .filter(cont -> cont.getAddresses().stream()
                            .map(add -> add.getStreet())
                            .collect(Collectors.joining())
                            .contains((String) entry.getValue()))
                            .collect(Collectors.toList());
                    break;
                case "zipCodeToString(contact)":
                    filteredList = filteredList.stream()
                            .filter(cont -> cont.getAddresses().stream()
                            .map(add -> add.getZipCode())
                            .collect(Collectors.joining())
                            .contains((String) entry.getValue()))
                            .collect(Collectors.toList());
                    break;
                case "cityToString(contact)":
                    filteredList = filteredList.stream()
                            .filter(cont -> cont.getAddresses().stream()
                            .map(add -> add.getCity())
                            .collect(Collectors.joining())
                            .contains((String) entry.getValue()))
                            .collect(Collectors.toList());
                    break;
                case "countryToString(contact)":
                    filteredList = filteredList.stream()
                            .filter(cont -> cont.getAddresses().stream()
                            .map(add -> add.getCountry().name())
                            .collect(Collectors.joining())
                            .contains((String) entry.getValue()))
                            .collect(Collectors.toList());
                    break;
                case "communicationsToString(contact)":
                    filteredList = filteredList.stream()
                            .filter(cont -> cont.getCommunications().stream()
                            .map(com -> com.getType().name() + " " + com.getIdentifier())
                            .collect(Collectors.joining())
                            .contains((String) entry.getValue()))
                            .collect(Collectors.toList());
                    break;
                default:
                    throw new AssertionError("Error with " + entry.getKey()+" and " + entry.getValue());
            }
            
        }
        
        return filteredList;
    }
    
}
