/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.Compte;

/**
 *
 * @author kinke
 */
@Stateless
public class CompteFacade extends AbstractFacade<Compte> {

    @PersistenceContext(unitName = "GestionCompteBancairePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompteFacade() {
        super(Compte.class);
    }
    
}
