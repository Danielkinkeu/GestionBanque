/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kinke
 */
@Entity
@Table(name = "operation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Operation.findAll", query = "SELECT o FROM Operation o")
    , @NamedQuery(name = "Operation.findByNumOperation", query = "SELECT o FROM Operation o WHERE o.numOperation = :numOperation")
    , @NamedQuery(name = "Operation.findByDateOperation", query = "SELECT o FROM Operation o WHERE o.dateOperation = :dateOperation")
    , @NamedQuery(name = "Operation.findByMontant", query = "SELECT o FROM Operation o WHERE o.montant = :montant")})
public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "num_operation")
    private Integer numOperation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_operation")
    @Temporal(TemporalType.DATE)
    private Date dateOperation;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "libelle")
    private String libelle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "montant")
    private long montant;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "type_operation")
    private String typeOperation;
    @JoinColumn(name = "id_compteS", referencedColumnName = "num_compte")
    @ManyToOne
    private Compte idcompteS;
    @JoinColumn(name = "id_banquier", referencedColumnName = "id_banquier")
    @ManyToOne(optional = false)
    private Banquier idBanquier;
    @JoinColumn(name = "id_compteD", referencedColumnName = "num_compte")
    @ManyToOne(optional = false)
    private Compte idcompteD;

    public Operation() {
    }

    public Operation(Integer numOperation) {
        this.numOperation = numOperation;
    }

    public Operation(Integer numOperation, Date dateOperation, String libelle, long montant, String typeOperation) {
        this.numOperation = numOperation;
        this.dateOperation = dateOperation;
        this.libelle = libelle;
        this.montant = montant;
        this.typeOperation = typeOperation;
    }

    public Integer getNumOperation() {
        return numOperation;
    }

    public void setNumOperation(Integer numOperation) {
        this.numOperation = numOperation;
    }

    public Date getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Date dateOperation) {
        this.dateOperation = dateOperation;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public long getMontant() {
        return montant;
    }

    public void setMontant(long montant) {
        this.montant = montant;
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
    }

    public Compte getIdcompteS() {
        return idcompteS;
    }

    public void setIdcompteS(Compte idcompteS) {
        this.idcompteS = idcompteS;
    }

    public Banquier getIdBanquier() {
        return idBanquier;
    }

    public void setIdBanquier(Banquier idBanquier) {
        this.idBanquier = idBanquier;
    }

    public Compte getIdcompteD() {
        return idcompteD;
    }

    public void setIdcompteD(Compte idcompteD) {
        this.idcompteD = idcompteD;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numOperation != null ? numOperation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Operation)) {
            return false;
        }
        Operation other = (Operation) object;
        if ((this.numOperation == null && other.numOperation != null) || (this.numOperation != null && !this.numOperation.equals(other.numOperation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.Operation[ numOperation=" + numOperation + " ]";
    }
    
}
