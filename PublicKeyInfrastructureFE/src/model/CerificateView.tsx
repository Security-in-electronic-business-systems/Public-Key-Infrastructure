class Certificate {
    public serialNumber: number;
    public issuerCN: string;
    public subjectCN: string;
    public certificateType: string;
    public validFrom: Date;
    public validTo: Date;
    public extendedKeyUsage: String[] | undefined;
    public keyUsage: any[] | undefined;
  
    constructor(
      serialNumber: number,
      issuerCN: string,
      subjectCN: string,
      certificateType: string,
      validFrom: Date,
      validTo: Date,
      extendedKeyUsage:String[] | undefined,
      keyUsage : boolean[] | undefined
    ) {
      this.serialNumber = serialNumber;
      this.issuerCN = issuerCN;
      this.subjectCN = subjectCN;
      this.certificateType = certificateType;
      this.validFrom = validFrom;
      this.validTo = validTo;
      this.extendedKeyUsage = extendedKeyUsage;
      this.keyUsage=keyUsage;
    }
  }
  