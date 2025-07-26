package com.project.PCBuilder.rest.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO class for "Parts"
 */
@Data
@NoArgsConstructor
public class PartsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- PRIMARY KEY
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer partid;

    //--- DATA FIELDS
    private String partname;
    private BigDecimal partprice;
    
    private Integer   componentid;

    //--- RELATIONSHIPS

    private ComponentsDTO components;

    @JsonIgnore private CpuDTO cpu;
    @JsonIgnore private CpucoolerDTO cpucooler;
    @JsonIgnore private MotherboardDTO motherboard;
    @JsonIgnore private MemoryDTO memory;
    @JsonIgnore private InternalharddriveDTO internalharddrive;
    @JsonIgnore private VideocardDTO videocard;
    @JsonIgnore private CasesDTO cases;
    @JsonIgnore private PowersupplyDTO powersupply;
    @JsonIgnore private OsDTO os;
    @JsonIgnore private MonitorDTO monitor;
    @JsonIgnore private SoundcardDTO soundcard;
    @JsonIgnore private WirednetworkcardDTO wirednetworkcard;
    @JsonIgnore private WirelessnetworkcardDTO wirelessnetworkcard;
    @JsonIgnore private HeadphonesDTO headphones;
    @JsonIgnore private KeyboardDTO keyboard;
    @JsonIgnore private MouseDTO mouse;
    @JsonIgnore private SpeakersDTO speakers;
    @JsonIgnore private WebcamDTO webcam;
    @JsonIgnore private CaseaccessoryDTO caseaccessory;
    @JsonIgnore private CasefanDTO casefan;
    @JsonIgnore private FancontrollerDTO fancontroller;
    @JsonIgnore private ThermalpasteDTO thermalpaste;
    @JsonIgnore private ExternalharddriveDTO externalharddrive;
    @JsonIgnore private OpticaldriveDTO opticaldrive;
    @JsonIgnore private UpsDTO ups;
}
