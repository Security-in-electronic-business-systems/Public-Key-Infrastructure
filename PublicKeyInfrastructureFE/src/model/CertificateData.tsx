export class CertificateData {
    type: CertificateType = CertificateType.INTERMEDIATE;
    issuerSerialNumber: String = "";
    validFrom: Date = new Date();
    ValidTo: Date = new Date();
    CN: String = "";
    O: String = "";
    ON: String = "";
    C: String = "";
    name: String = "";
    surname: String = "";
    phoneNumber: String = "";
    emial: String = "";

    serverAuth: boolean = false;
    clientAuth: boolean = false;
    codeSign: boolean = false;
    emailProtection: boolean = false;
    timeStamping: boolean = false;
    ocspSigning: boolean = false;

    digitalSignature: boolean = false;
    nonRepudiation: boolean = false;
    keyEnciphement: boolean = false;
    dataEnciphement: boolean = false;
    keyAgriment: boolean = false;
    keyCertSign: boolean = false;
    enhipterOnly: boolean = false;
    decipherOnly: boolean = false;
  
    criticalExtended: boolean = false;
    critical: boolean = false;

    constructor(
      type: CertificateType,
      issuerSerialNumber: string,
      validFrom: Date,
      validTo: Date,
      CN: string,
      O: string,
      ON: string,
      C: string,
      name: string,
      surname: string,
      phoneNumber: string,
      email: string,
      serverAuth: boolean,
      clientAuth: boolean,
      codeSign: boolean,
      emailProtection: boolean,
      timeStamping: boolean,
      ocspSigning: boolean,
      digitalSignature: boolean,
      nonRepudiation: boolean,
      keyEnciphement: boolean,
      dataEnciphement: boolean,
      keyAgreement: boolean,
      keyCertSign: boolean,
      enhipterOnly: boolean,
      decipherOnly: boolean,
      criticalExtended: boolean,
      critical: boolean

    ) {
      this.type = type;
      this.issuerSerialNumber = issuerSerialNumber;
      this.validFrom = validFrom;
      this.ValidTo = validTo;
      this.CN = CN;
      this.O = O;
      this.ON = ON;
      this.C = C;
      this.name = name;
      this.surname = surname;
      this.phoneNumber = phoneNumber;
      this.emial = email;
      this.serverAuth = serverAuth;
      this.clientAuth = clientAuth;
      this.codeSign = codeSign;
      this.emailProtection = emailProtection;
      this.timeStamping = timeStamping;
      this.ocspSigning = ocspSigning;
      this.digitalSignature = digitalSignature;
      this.nonRepudiation = nonRepudiation;
      this.keyEnciphement = keyEnciphement;
      this.dataEnciphement = dataEnciphement;
      this.keyAgriment = keyAgreement;
      this.keyCertSign = keyCertSign;
      this.enhipterOnly = enhipterOnly;
      this.decipherOnly = decipherOnly;
      this.criticalExtended = criticalExtended;
      this.critical = critical;
    }
  }
export enum CertificateType{
    SELF_SIGNED,
    INTERMEDIATE,
    END_ENTITY
}