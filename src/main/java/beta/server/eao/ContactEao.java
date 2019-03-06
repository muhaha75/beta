/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beta.server.eao;

import beta.server.assist.SingletonDatabase;
import beta.server.entity.Contact;
import beta.server.entity.Country;
import beta.server.entity.Sex;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author oliver.guenther
 */
@Stateless
public class ContactEao {

    @Inject
    private SingletonDatabase db;
    private int rowCount;

    private final Logger L = LoggerFactory.getLogger(ContactEao.class);

    /**
     * Returns a random contact.
     *
     * @return a random contact.
     */
    public Contact findAny() {
        Random r = new Random();
        int size = db.allContacts().size();
        return db.allContacts().get(r.nextInt(size));
    }

    /**
     * Returns all contacts.
     *
     * @return all contacts.
     */
    public List<Contact> findAll() {
        L.info("Hashcode in findall {} ", System.identityHashCode(db.allContacts()));
        return new ArrayList<>(db.allContacts());
    }

    /**
     * Returns all contacts, within a range.
     *
     * @param start start in the total result
     * @param limit amount to return
     * @return null if the start is bigger than the colection, all contacts,
     * within a range.
     */
    public List<Contact> findAll(int start, int limit) {
        if (start >= db.allContacts().size()) {
            return null;
        }

        return db.allContacts().subList(start, limit);
    }

    /**
     * Searching for Contact with this name first or lastname is not import
     *
     * @param searchString
     * @return a List of Contact with this SearchString
     */
    public List<Contact> find(String searchString) {
        L.debug("find: " + db.allContacts().size());
        List<Contact> firstNameList = db.allContacts().stream()
                .filter(c -> c.getFirstName().contains(searchString))
                .collect(Collectors.toList());

        List<Contact> lastNameList = db.allContacts().stream()
                .filter(c -> c.getLastName().contains(searchString))
                .collect(Collectors.toList());

        List<Contact> streetNameList = findByStreetName(searchString);
        List<Contact> findByZipCode = findByZipCode(searchString);

        //first concat first and lastName List together
        List<Contact> outputFullNames = Stream.concat(firstNameList.stream(), lastNameList.stream())
                .collect(Collectors.toList());

        //adding Streetname List this list
        List<Contact> withStreetNames = Stream.concat(outputFullNames.stream(), streetNameList.stream())
                .collect(Collectors.toList());

        return Stream.concat(withStreetNames.stream(), findByZipCode.stream())
                .collect(Collectors.toList());
    }

    /**
     * Find all Contact in List with given StreetName
     *
     * @param searchString
     * @return List<Contact> with this StreetName
     */
    private List<Contact> findByStreetName(String searchString) {
        List<Contact> streetNameList = db.allContacts().stream().collect(Collectors.toMap(c -> c,
                c -> c.getAddresses().stream().filter(a -> a.getStreet().contains(searchString))
                        .collect(Collectors.toList())
        )).entrySet()
                .stream()
                .filter(v -> !v.getValue().isEmpty())
                .map(c -> c.getKey())
                .collect(Collectors.toList());
        L.info("size collect {}", streetNameList.size());
        for (Contact c : streetNameList) {
            L.info(c.toFullName());
        }
        return streetNameList;
    }

    /**
     * Find all Contact in List with given ZipCode
     *
     * @param searchString
     * @return List<Contact> with this ZipCode
     */
    private List<Contact> findByZipCode(String searchString) {
        List<Contact> zipCodeList = db.allContacts().stream().collect(Collectors.toMap(c -> c,
                c -> c.getAddresses().stream().filter(a -> a.getZipCode().contains(searchString))
                        .collect(Collectors.toList())
        )).entrySet()
                .stream()
                .filter(v -> !v.getValue().isEmpty())
                .map(c -> c.getKey())
                .collect(Collectors.toList());
        L.info("size collect {}", zipCodeList.size());
        for (Contact c : zipCodeList) {
            L.info(c.toFullName());
        }
        return zipCodeList;
    }

    public int getRowCount() {
        return rowCount;
    }

    /**
     * Return a SubList of Contacts, matching the filters, in range from start
     * to limit
     *
     * @param filter
     * @param start
     * @param limit
     * @return
     */
    public List<Contact> filterContactsInRange(Map<String, Object> filters, int start, int limit) {
        List<Contact> filteredList = db.allContacts();

        //{firstName=sdf, lastName=sdfs, zipCode=sdfsd, country=[Ljava.lang.String;@68ca7542, communications=sdf, city=sdfs, street=sdfsdf, sex=MALE, title=Dr.}
        for (Map.Entry<String, Object> filter : filters.entrySet()) {
            L.info("Filter {}", filter.getValue());
            switch (filter.getKey()) {
                case "title":
                    L.info("Title Filter!! {}", filter.getValue());
                    filteredList = filteredList.stream()
                            .filter(cont -> {return cont.getTitle() !=null &&((String) filter.getValue()).contains(cont.getTitle());})
                            .collect(Collectors.toList());
                    break;
                case "firstName":
                    filteredList = filteredList.stream()
                            .filter(cont -> cont.getFirstName().contains((String) filter.getValue()))
                            .collect(Collectors.toList());
                    break;
                case "lastName":
                    filteredList = filteredList.stream()
                            .filter(cont -> cont.getLastName().contains((String) filter.getValue()))
                            .collect(Collectors.toList());
                    break;
                case "zipCode":
                    filteredList = filteredList.stream()
                            .filter(cont -> cont.getAddresses().stream()
                            .map(add -> add.getZipCode())
                            .collect(Collectors.joining()).contains((String) filter.getValue()))
                            .collect(Collectors.toList());
                    break;
                case "country":

                    filteredList = filteredList.stream()
                            .filter(cont -> cont.getAddresses()
                            .stream()
                            .filter(add -> filterContainsCountry((String[])filter.getValue(), add.getCountry())).collect(Collectors.counting())!=0).collect(Collectors.toList());
                            

                    break;
                case "city":
                    filteredList = filteredList.stream()
                            .filter(cont -> cont.getAddresses().stream()
                            .map(add -> add.getCity())
                            .collect(Collectors.joining())
                            .contains((String) filter.getValue()))
                            .collect(Collectors.toList());
                    break;
                case "street":
                    filteredList = filteredList.stream()
                            .filter(cont -> cont.getAddresses().stream()
                            .map(add -> add.getStreet())
                            .collect(Collectors.joining())
                            .contains((String) filter.getValue()))
                            .collect(Collectors.toList());
                    break;
                case "sex":
                    filteredList = filteredList.stream()
                            .filter(cont -> cont.getSex().name().equals(filter.getValue()))
                            .collect(Collectors.toList());
                    break;
                case "communications":
                    filteredList = filteredList.stream()
                            .filter(cont -> cont.getCommunications().stream()
                            .map(com -> com.getType().name() + " " + com.getIdentifier())
                            .collect(Collectors.joining())
                            .contains((String) filter.getValue()))
                            .collect(Collectors.toList());
                    break;

                default:
                    throw new AssertionError("Error in filterContactInRange : unexpected key in filterList Key: " + filter.getKey());
            }
        }

        this.rowCount = filteredList.size();

        if (limit >= filteredList.size()) {
            return filteredList.subList(start, filteredList.size());
        } else {
            return filteredList.subList(start, limit);
        }

    }
    
    private boolean filterContainsCountry(String[] filter, Country country){
    
    boolean contains=false;
    
        for (String countryName : filter) {
            contains = country.name().equals(countryName);
            if(contains){
            return contains;
            }
        }
        return contains;   
    }

}
