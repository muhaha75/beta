package beta.server.assist;

import javax.annotation.ManagedBean;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Pu for beta
 * @author tjark.uschakow
 */
@ManagedBean
public class BetaPu {

    /**
     * static persistence unit name definition
     */
    public final static String PERSISTENCE_UNIT = "beta-pu";
    
    /**
     * initially produced entity manager for cdi purpose
     */
    @Produces
    @PersistenceContext(unitName = PERSISTENCE_UNIT)
    private EntityManager em;
    
    
  
}
