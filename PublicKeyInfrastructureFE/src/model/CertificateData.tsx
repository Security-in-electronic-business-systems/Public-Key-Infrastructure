export class CertificateData {
    type: CertificateType = CertificateType.INTERMEDIATE;
    issuerSerialNumber: String = "";
    validFrom: Date = new Date();
    ValidTo: Date = new Date();
    CN: String = "";
    ON: String = "";
    name: String = "";
    surname: String = "";
    phoneNumber: String = "";
    emial: String = "";
  
    constructor(type: CertificateType, issuerSerialNumber: String, validFrom: Date, validTo: Date, CN: String,
                 ON: String, name: String, surname: String, phoneNumber: String, email: String) {
            
            this.type = type;
            this.issuerSerialNumber = issuerSerialNumber;
            this.validFrom = validFrom;
            this.ValidTo = validTo;
            this.CN = CN;
            this.ON = ON;
            this.name = name;
            this.surname = surname;
            this.phoneNumber = phoneNumber;
            this.emial = email;

    }
}

export enum CertificateType{
    SELF_SIGNED,
    INTERMEDIATE,
    END_ENTITY
}