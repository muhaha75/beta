/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beta.server.assist;

import beta.server.entity.Address;
import beta.server.entity.Communication;
import beta.server.entity.Communication.Type;
import beta.server.entity.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author oliver.guenther
 */
public class Generator {

    private final GeneratorFormFileSets GEN = new GeneratorFormFileSets();

    private final Random R = new Random();

    /**
     * Generates a {@link Contact}.
     * <p>
     * @return a generated {@link Contact}.
     */
    public Contact makeContact() {
        return makeContact(new Contact(), makeAddress(), makeCommunication());
    }

    public Contact makeContactWithId(long contactId, long addressId, long communicationId) {
        return makeContact(new Contact(contactId), makeAddressWithId(addressId), makeCommunicationWithId(communicationId));
    }

    private Contact makeContact(Contact contact, Address address, Communication communication) {
        Contact generatedContact = GEN.makeContact();

        contact.setFirstName(generatedContact.getFirstName());
        contact.setLastName(generatedContact.getLastName());
        contact.setSex(generatedContact.getSex());
        contact.setTitle(generatedContact.getTitle());
        if (communication != null) {
            contact.getCommunications().add(communication);
            for (int i = 0; i < R.nextInt(5); i++) {
                contact.getCommunications().add(makeCommunication());
            }
        }
        if (address != null) {
            contact.getAddresses().add(address);
            for (int i = 0; i < R.nextInt(5); i++) {
                contact.getAddresses().add(makeAddress());
            }
        }
        return contact;
    }

    /**
     * Generates a {@link Address}. is never set.
     * <p>
     * @return a generated {@link Contact}.
     */
    public Address makeAddress() {
        return makeAddress(new Address());
    }

    public Address makeAddressWithId(long id) {
        return makeAddress(new Address(id));
    }

    private Address makeAddress(Address address) {
        Address genereratedAddress = GEN.makeAddress();
        address.setCity(genereratedAddress.getCity());
        address.setStreet(genereratedAddress.getStreet());
        address.setZipCode(genereratedAddress.getZipCode());
        address.getCountry(); //get default DE as country
        return address;
    }

    /**
     * Generates an amount of persisted {@link Address}.
     * <p>
     * @param amount the amount
     * @return the generated instances.
     */
    public List<Address> makeAddresses(int amount) {
        List<Address> addresses = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            addresses.add(makeAddress());
        }
        return addresses;
    }

    /**
     * Generates a non persisted {@link Communication}.
     * {@link Communication#prefered} is never set.
     * <p>
     * @return a generated {@link Communication}.
     */
    public Communication makeCommunication() {
        return makeCommunication(new Communication());
    }

    /**
     * Generates a non persisted valid {@link Communication} of the supplied
     * type.
     *
     * @param type the type to be generated
     * @return a new communication
     */
    public Communication makeCommunication(Type type) {
        return makeCommunication(new Communication(), type);
    }

    public Communication makeCommunicationWithId(long id) {
        return makeCommunication(new Communication(id));
    }

    private Communication makeCommunication(Communication communication) {
        return makeCommunication(communication,
                Communication.Type.values()[new Random().nextInt(Communication.Type.values().length)]);
    }

    private Communication makeCommunication(Communication c, Type type) {
        c.setType(type);
        switch (c.getType()) {
            case PHONE:
            case FAX:
            case MOBILE:
                c.setIdentifier("+49 123 45275642");
                break;
            case EMAIL:
                c.setIdentifier("test@demo.com");
                break;
            default:
                c.setIdentifier("12345abced");
                break;
        }
        return c;
    }

}
