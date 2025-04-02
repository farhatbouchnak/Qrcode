package com.generate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "PASSGENRESP")
@NoArgsConstructor
public class PassGenResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    @JsonFormat(pattern="yyyy/MM/dd")
    @Column(name = "BIRTH_DATE")
    private Date birthDate;

    @Column(name = "STATUS_VIP")
    private boolean vip_Status;

    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    @Column(name = "REQUEST_LD")
    private Date requestTime;

    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    @Column(name = "GENERATION_LD")
    private Date generationTime;

    @Column(name = "QR_CODE")
    private byte[] qrCode;

    @Column(name = "STATE")
    private String state;


    public PassGenResponse(String firstName, String lastName, Date birthDate, boolean vip_Status, Date requestTime, Date generationTime, byte[] qrCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.vip_Status = vip_Status;
        this.requestTime = requestTime;
        this.generationTime = generationTime;
        this.qrCode = qrCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isVip_Status() {
        return vip_Status;
    }

    public void setVip_Status(boolean vip_Status) {
        this.vip_Status = vip_Status;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public Date getGenerationTime() {
        return generationTime;
    }

    public void setGenerationTime(Date generationTime) {
        this.generationTime = generationTime;
    }

    public byte[] getQrCode() {
        return qrCode;
    }

    public void setQrCode(byte[] qrCode) {
        this.qrCode = qrCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
