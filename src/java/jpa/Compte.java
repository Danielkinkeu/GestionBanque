/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author kinke
 */
@Entity
@Table(name = "compte")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Compte.findAll", query = "SELECT c FROM Compte c")
    , @NamedQuery(name = "Compte.findByNumCompte", query = "SELECT c FROM Compte c WHERE c.numCompte = :numCompte")
    , @NamedQuery(name = "Compte.findBySolde", query = "SELECT c FROM Compte c WHERE c.solde = :solde")
    , @NamedQuery(name = "Compte.findByDateOuverture", query = "SELECT c FROM Compte c WHERE c.dateOuverture = :dateOuverture")
    , @NamedQuery(name = "Compte.findByDateFermeture", query = "SELECT c FROM Compte c WHERE c.dateFermeture = :dateFermeture")
    , @NamedQuery(name = "Compte.findByTypeCompte", query = "SELECT c FROM Compte c WHERE c.typeCompte = :typeCompte")})
public class Compte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "num_compte")
    private Integer numCompte;
    @Basic(optional = false)
    @NotNull
    @Column(name = "solde")
    private double solde;
    @Column(name = "date_ouverture")
    @Temporal(TemporalType.DATE)
    private Date dateOuverture;
    @Column(name = "date_fermeture")
    @Temporal(TemporalType.DATE)
    private Date dateFermeture;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "type_compte")
    private String typeCompte;
    @OneToMany(mappedBy = "idcompteS")
    private Collection<Operation> operationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcompteD")
    private Collection<Operation> operationCollection1;
    @JoinColumn(name = "id_client", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Client idClient;

    public Compte() {
    }

    public Compte(Integer numCompte) {
        this.numCompte = numCompte;
    }

    public Compte(Integer numCompte, int solde, String typeCompte) {
        this.numCompte = numCompte;
        this.solde = solde;
        this.typeCompte = typeCompte;
    }

    public Integer getNumCompte() {
        return numCompte;
    }

    public void setNumCompte(Integer numCompte) {
        this.numCompte = numCompte;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public Date getDateOuverture() {
        return dateOuverture;
    }

    public void setDateOuverture(Date dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public Date getDateFermeture() {
        return dateFermeture;
    }

    public void setDateFermeture(Date dateFermeture) {
        this.dateFermeture = dateFermeture;
    }

    public String getTypeCompte() {
        return typeCompte;
    }

    public void setTypeCompte(String typeCompte) {
        this.typeCompte = typeCompte;
    }

    @XmlTransient
    public Collection<Operation> getOperationCollection() {
        return operationCollection;
    }

    public void setOperationCollection(Collection<Operation> operationCollection) {
        this.operationCollection = operationCollection;
    }

    @XmlTransient
    public Collection<Operation> getOperationCollection1() {
        return operationCollection1;
    }

    public void setOperationCollection1(Collection<Operation> operationCollection1) {
        this.operationCollection1 = operationCollection1;
    }

    public Client getIdClient() {
        return idClient;
    }

    public void setIdClient(Client idClient) {
        this.idClient = idClient;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numCompte != null ? numCompte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compte)) {
            return false;
        }
        Compte other = (Compte) object;
        if ((this.numCompte == null && other.numCompte != null) || (this.numCompte != null && !this.numCompte.equals(other.numCompte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.Compte[ numCompte=" + numCompte + " ]";
    }
    
}
