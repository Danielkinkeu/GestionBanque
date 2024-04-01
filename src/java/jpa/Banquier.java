/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author kinke
 */
@Entity
@Table(name = "banquier")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Banquier.findAll", query = "SELECT b FROM Banquier b")
    , @NamedQuery(name = "Banquier.findByIdBanquier", query = "SELECT b FROM Banquier b WHERE b.idBanquier = :idBanquier")
    , @NamedQuery(name = "Banquier.findByNom", query = "SELECT b FROM Banquier b WHERE b.nom = :nom")
    , @NamedQuery(name = "Banquier.findByPrenom", query = "SELECT b FROM Banquier b WHERE b.prenom = :prenom")
    , @NamedQuery(name = "Banquier.findByTel", query = "SELECT b FROM Banquier b WHERE b.tel = :tel")})
public class Banquier implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_banquier")
    private Integer idBanquier;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "nom")
    private String nom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "prenom")
    private String prenom;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tel")
    private int tel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBanquier")
    private Collection<Operation> operationCollection;

    public Banquier() {
    }

    public Banquier(Integer idBanquier) {
        this.idBanquier = idBanquier;
    }

    public Banquier(Integer idBanquier, String nom, String prenom, int tel) {
        this.idBanquier = idBanquier;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
    }

    public Integer getIdBanquier() {
        return idBanquier;
    }

    public void setIdBanquier(Integer idBanquier) {
        this.idBanquier = idBanquier;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    @XmlTransient
    public Collection<Operation> getOperationCollection() {
        return operationCollection;
    }

    public void setOperationCollection(Collection<Operation> operationCollection) {
        this.operationCollection = operationCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBanquier != null ? idBanquier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Banquier)) {
            return false;
        }
        Banquier other = (Banquier) object;
        if ((this.idBanquier == null && other.idBanquier != null) || (this.idBanquier != null && !this.idBanquier.equals(other.idBanquier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.Banquier[ idBanquier=" + idBanquier + " ]";
    }
    
}
