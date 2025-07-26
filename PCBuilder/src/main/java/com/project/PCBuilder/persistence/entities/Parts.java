package com.project.PCBuilder.persistence.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="Parts", schema="dbo", catalog="PCBuilder")
public class Parts implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- PRIMARY KEY
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PartID", nullable = false)
    private Integer partid;

    //--- OTHER FIELDS
    @Column(name = "PartName", nullable = false, length = 256)
    private String partname;

    @Column(name = "PartPrice")
    private BigDecimal partprice;
    
    
    //--- RELATIONSHIPS

    // Many-to-One: Parts -> Components
    @ManyToOne
    @JoinColumn(name = "ComponentID", nullable = false)
    private Components components;

    // One-to-One: Parts -> Each specific part entity
    @OneToOne(mappedBy = "parts", cascade = CascadeType.ALL, optional = true)
    private Cpu cpu;

    @OneToOne(mappedBy = "parts", cascade = CascadeType.ALL, optional = true)
    private Cpucooler cpucooler;

    @OneToOne(mappedBy = "parts", cascade = CascadeType.ALL, optional = true)
    private Motherboard motherboard;

    @OneToOne(mappedBy = "parts", cascade = CascadeType.ALL, optional = true)
    private Memory memory;

    @OneToOne(mappedBy = "parts", cascade = CascadeType.ALL, optional = true)
    private Internalharddrive internalharddrive;

    @OneToOne(mappedBy = "parts", cascade = CascadeType.ALL, optional = true)
    private Videocard videocard;

    @OneToOne(mappedBy = "parts", cascade = CascadeType.ALL, optional = true)
    private Cases cases;

    @OneToOne(mappedBy = "parts", cascade = CascadeType.ALL, optional = true)
    private Powersupply powersupply;

    @OneToOne(mappedBy = "parts", cascade = CascadeType.ALL, optional = true)
    private Os os;

    @OneToOne(mappedBy = "parts", cascade = CascadeType.ALL, optional = true)
    private Monitor monitor;

    @OneToOne(mappedBy = "parts", cascade = CascadeType.ALL, optional = true)
    private Soundcard soundcard;

    @OneToOne(mappedBy = "parts", cascade = CascadeType.ALL, optional = true)
    private Wirednetworkcard wirednetworkcard;

    @OneToOne(mappedBy = "parts", cascade = CascadeType.ALL, optional = true)
    private Wirelessnetworkcard wirelessnetworkcard;

    @OneToOne(mappedBy = "parts", cascade = CascadeType.ALL, optional = true)
    private Headphones headphones;

    @OneToOne(mappedBy = "parts", cascade = CascadeType.ALL, optional = true)
    private Keyboard keyboard;

    @OneToOne(mappedBy = "parts", cascade = CascadeType.ALL, optional = true)
    private Mouse mouse;

    @OneToOne(mappedBy = "parts", cascade = CascadeType.ALL, optional = true)
    private Speakers speakers;

    @OneToOne(mappedBy = "parts", cascade = CascadeType.ALL, optional = true)
    private Webcam webcam;

    @OneToOne(mappedBy = "parts", cascade = CascadeType.ALL, optional = true)
    private Caseaccessory caseaccessory;

    @OneToOne(mappedBy = "parts", cascade = CascadeType.ALL, optional = true)
    private Casefan casefan;

    @OneToOne(mappedBy = "parts", cascade = CascadeType.ALL, optional = true)
    private Fancontroller fancontroller;

    @OneToOne(mappedBy = "parts", cascade = CascadeType.ALL, optional = true)
    private Thermalpaste thermalpaste;

    @OneToOne(mappedBy = "parts", cascade = CascadeType.ALL, optional = true)
    private Externalharddrive externalharddrive;

    @OneToOne(mappedBy = "parts", cascade = CascadeType.ALL, optional = true)
    private Opticaldrive opticaldrive;

    @OneToOne(mappedBy = "parts", cascade = CascadeType.ALL, optional = true)
    private Ups ups;
}
