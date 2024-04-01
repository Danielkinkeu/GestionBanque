/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.Operation;

/**
 *
 * @author kinke
 */
@Stateless
public class OperationFacade extends AbstractFacade<Operation> {

    @PersistenceContext(unitName = "GestionCompteBancairePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OperationFacade() {
        super(Operation.class);
    }
    
}
