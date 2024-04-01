package Controller;

import jpa.Operation;
import Controller.util.JsfUtil;
import Controller.util.PaginationHelper;
import SessionBean.OperationFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import Controller.CompteController;
import javax.inject.Inject;
import jpa.Compte;

@Named("operationController")
@SessionScoped
public class OperationController implements Serializable {
    
    @Inject
    private CompteController compteController;
    private Operation current;
    private DataModel items = null;
    @EJB
    private SessionBean.OperationFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public OperationController() {
    }

    public Operation getSelected() {
        if (current == null) {
            current = new Operation();
            selectedItemIndex = -1;
        }
        return current;
    }

    private OperationFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Operation) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Operation();
        selectedItemIndex = -1;
        return "Create";
    }

//    public String create() {
//        try {
//            getFacade().create(current);
//            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OperationCreated"));
//            return prepareCreate();
//        } catch (Exception e) {
//            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
//            return null;
//        }
//    }
    private Compte compte;
    private Compte compteS;
    private Compte compteD;
    public String create() {
        try {
               if (current != null) {
                // Gestion du type de transaction
                    compte = current.getIdcompteD();
                double solde = compte.getSolde();
                double montant = current.getMontant();
                if ("depot".equals(current.getTypeOperation())) {
                    // Gestion du dépôt sur le compte
                    double newSolde = solde + montant;
                    // Mettre à jour le solde du compte
                    compte.setSolde(newSolde);
                } else if ("retrait".equals(current.getTypeOperation())) {
                    // Gestion du retrait sur le compte
                    if (montant > solde) {
                        JsfUtil.addErrorMessage("Solde insuffisant !");
                        return null; // Arrêter le processus si le solde est insuffisant
                    } else {
                        double newSolde = solde - montant;
                        compte.setSolde(newSolde);
                    }
                } else if ("transfert".equals(current.getTypeOperation())) {
                    // Implémenter la gestion du virement ici
                    compteD = current.getIdcompteD();
                    compteS = current.getIdcompteS();
                    double soldeS = compteS.getSolde();
                    double soldeD = compteD.getSolde();
                    double montantD = current.getMontant();
                    
                    // Gestion du retrait sur le compte
                    if (montantD > soldeS) {
                        JsfUtil.addErrorMessage("Solde insuffisant !");
                        return null; // Arrêter le processus si le solde est insuffisant
                    } else {
                        double newSolde = soldeS - montantD;
                        compteS.setSolde(newSolde);
                        
                         // Gestion du dépôt sur le compte
                        double newSoldeD = soldeD + montantD;
                        // Mettre à jour le solde du compte
                        compteD.setSolde(newSoldeD);
                    }
                    
                    
                }
                //mise a jour solde                
                System.out.println("danger !");
                compteController.updateSolde(compte);
                compteController.updateSolde(compteD);
                compteController.updateSolde(compteS);
            }
            System.out.println("L'objet Compte passé en argument est null !");
            
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OperationCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Operation) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OperationUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Operation) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OperationDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Operation getOperation(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Operation.class)
    public static class OperationControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OperationController controller = (OperationController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "operationController");
            return controller.getOperation(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Operation) {
                Operation o = (Operation) object;
                return getStringKey(o.getNumOperation());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Operation.class.getName());
            }
        }

    }

}
