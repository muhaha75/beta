/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beta;

import beta.server.assist.Generator;
import beta.server.eao.ContactEao;
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
    ContactEao contactEao;

    public void onLoad() {

        Generator gen = new Generator();
        if (contactEao.count() < 1) {
        gen.makeContact();
//            for (int i = 0; i < 2; i++) {
//                
//            }
        }
    }
}
