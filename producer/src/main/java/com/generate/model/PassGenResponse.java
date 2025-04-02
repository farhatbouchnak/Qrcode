package com.generate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
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
}
