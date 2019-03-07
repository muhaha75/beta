/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beta.server.emo;

import beta.server.entity.Contact;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Perform changes on the database
 *
 * @author tjark.uschakow
 */
@Stateless
public class ContactEmo {

    private final Logger L = LoggerFactory.getLogger(ContactEmo.class);

    @PersistenceContext(unitName = "beta-pu")
    private EntityManager em;

    /**
     * Create the entrys on the database for the given Contact
     *
     * @param contact
     */
    public void create(Contact contact) {

        if (contact != null && contact.getId() < 1) {

            em.persist(contact);

        }
    }

    public void update(Contact contact) {
        if (contact != null && contact.getId() > 0) {
            em.merge(contact);
        } else if (contact != null) {
            em.persist(contact);
        }

    }

}
