/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beta;

import beta.server.assist.Generator;
import beta.server.eao.ContactEao;
import beta.server.emo.ContactEmo;
import beta.server.entity.Contact;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Called from the startpage (index.xhtml) and calls the generator if no data in
 * database exsist
 *
 * @author tjark.uschakow
 */
@Named
@Startup
@Singleton
public class StartUp {
    
    @Inject
    private ContactEao contactEao;
    
    @Inject    
    private ContactEmo contactEmo;
    
    public void onLoad() {
        
        Generator gen = new Generator();
        if (contactEao.count() < 1) {
            Contact makeContact = gen.makeContact();
            contactEmo.create(makeContact);
            
        }
    }
}
