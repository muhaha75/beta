/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beta.web.presenter;

import beta.server.eao.ContactEao;
import beta.server.entity.Contact;
import beta.server.entity.Country;
import beta.server.entity.Sex;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import javax.faces.view.ViewScoped;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * provide the resources for the filterable ContactView.xhtml
 *
 * @author tjark.uschakow
 */
@Named
@ViewScoped
public class ContactFilterController implements Serializable {

    @Inject
    private ContactEao contactEao;
    /**
     * contains the List of Contacts
     */
    private List<Contact> contacts = new ArrayList<>();

    /**
     * Contains all CountryNames of the Country-enum
     */
    private List<String> countrys = new ArrayList<>();

    /**
     * COntains the filtered ContactList
     */
    private List<Contact> filteredContacts;

    /**
     * Contains the Strings for titelfilter
     */
    private List<String> titelfilter = new ArrayList<>();

    /**
     * Get the Contact List from ConatactEao
     */
    @PostConstruct
    public void init() {
        titelfilter.add("Dr.");
        titelfilter.add("Prof.");
        titelfilter.add("Prof. Dr.");

        for (Country country : Country.values()) {
            countrys.add(country.getCountryName());
        }
        this.contacts = contactEao.findAll();
        this.filteredContacts = new ArrayList<>(contacts);
    }

    /**
     * Returns a List of filtered contacts
     *
     * @return
     */
    public List<Contact> getFilteredContacts() {
        return filteredContacts;
    }

    /**
     * Returns a List of all available Titels for the filter
     *
     * @return
     */
    public List<String> getTitelfilter() {
        return titelfilter;
    }

    /**
     * Returns a List of all available Countrys for the filter
     *
     * @return
     */
    public List<String> getCountrys() {
        return countrys;
    }

    /**
     * Returns the List of Contacts
     *
     * @return List of Contacts
     */
    public List<Contact> getContacts() {
        return contacts;
    }

    /**
     * Build a String of all streets of the given contact
     *
     * @param contact
     * @return a String of streets or a blank String if contact is Null
     */
    public String streetsToString(Contact contact) {
        if (contact == null) {
            return "";
        }
        return contact.getAddresses()
                .stream()
                .map(add -> add.getStreet())
                .collect(Collectors.joining(","));
    }

    /**
     * Build a String of all ZipCodes of the given contact
     *
     * @param contact
     * @return a String of zipcodes or a blank String if contact is Null
     */
    public String zipCodeToString(Contact contact) {
        if (contact == null) {
            return "";
        }
        return contact.getAddresses()
                .stream()
                .map(add -> add.getZipCode())
                .collect(Collectors.joining(","));
    }

    /**
     * Build a String of all communication-infos of the given contact
     *
     * @param contact
     * @return a String of communication-infos or a blank String if contact is
     * Null
     */
    public String communicationsToString(Contact contact) {
        if (contact == null) {
            return "";
        }
        return contact.getCommunications()
                .stream()
                .map(com -> com.getType() + ": " + com.getIdentifier())
                .collect(Collectors.joining(","));
    }

    /**
     * Build a String of all countrys of the given contact
     *
     * @param contact
     * @return a String of countrys or a blank String if contact is Null
     */
    public String countryToString(Contact contact) {

        if (contact == null) {
            return "";
        }

        return contact.getAddresses()
                .stream()
                .map(add -> add.getCountry().getCountryName())
                .collect(Collectors.joining(","));

    }

    /**
     * Build a String of all citys of the given contact
     *
     * @param contact
     * @return a String of citys or a blank String if contact is Null
     */
    public String cityToString(Contact contact) {
        if (contact == null) {
            return "";
        }

        return contact.getAddresses()
                .stream()
                .map(add -> add.getCity())
                .collect(Collectors.joining(","));
    }

    /**
     * Translate the given Sex to german and return it If sex is null it returns
     * a blank-String
     *
     * @param sex
     * @return A String represents the sex in german or a blank-String
     */
    public String translateSex(Sex sex) {

        if (sex != null) {
            if (sex == Sex.MALE) {
                return "männlich";
            } else {
                return "weiblich";
            }
        }
        return "";
    }

}
