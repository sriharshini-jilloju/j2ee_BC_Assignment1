package org.digital.evidence.ejb.entity;

import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "evidences")
public class Evidence {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Acquisition date is required")
    @PastOrPresent(message = "Acquisition date cannot be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "acquisitionDate")
    private LocalDate acquisitionDate;
    
    @NotNull(message = "Custody status is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "custodyStatus")
    private CustodyStatus custodyStatus;
    
    @NotBlank(message = "Description is required")
    @Column(name = "description")
    private String description;
    
    @NotNull(message = "Encrypted field is required")
    @Column(name = "encrypted", nullable = false)
    private Boolean encrypted = false;
    
    @NotBlank(message = "Last custodian name is required")
    @Column(name = "lastCustodianName")
    private String lastCustodianName;
    
    @NotBlank(message = "Source officer is required")
    @Column(name = "sourceOfficer")
    private String sourceOfficer;
    
    // Default constructor
    public Evidence() {}
    
    // Constructor with required fields
    public Evidence(Boolean encrypted) {
        this.encrypted = encrypted;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDate getAcquisitionDate() {
        return acquisitionDate;
    }
    
    public void setAcquisitionDate(LocalDate acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }
    
    public CustodyStatus getCustodyStatus() {
        return custodyStatus;
    }
    
    public void setCustodyStatus(CustodyStatus custodyStatus) {
        this.custodyStatus = custodyStatus;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Boolean getEncrypted() {
        return encrypted;
    }
    
    public void setEncrypted(Boolean encrypted) {
        this.encrypted = encrypted;
    }
    
    public String getLastCustodianName() {
        return lastCustodianName;
    }
    
    public void setLastCustodianName(String lastCustodianName) {
        this.lastCustodianName = lastCustodianName;
    }
    
    public String getSourceOfficer() {
        return sourceOfficer;
    }
    
    public void setSourceOfficer(String sourceOfficer) {
        this.sourceOfficer = sourceOfficer;
    }
    
    // Enum for custody status
    public enum CustodyStatus {
        IN_CUSTODY,
        RELEASED
    }
    
    @Override
    public String toString() {
        return "Evidence{" +
                "id=" + id +
                ", acquisitionDate=" + acquisitionDate +
                ", custodyStatus=" + custodyStatus +
                ", description='" + description + '\'' +
                ", encrypted=" + encrypted +
                ", lastCustodianName='" + lastCustodianName + '\'' +
                ", sourceOfficer='" + sourceOfficer + '\'' +
                '}';
    }
}