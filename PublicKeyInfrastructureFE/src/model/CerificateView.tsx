class Certificate {
    public serialNumber: number;
    public issuerCN: string;
    public subjectCN: string;
    public certificateType: string;
    public validFrom: Date;
    public validTo: Date;
  
    constructor(
      serialNumber: number,
      issuerCN: string,
      subjectCN: string,
      certificateType: string,
      validFrom: Date,
      validTo: Date
    ) {
      this.serialNumber = serialNumber;
      this.issuerCN = issuerCN;
      this.subjectCN = subjectCN;
      this.certificateType = certificateType;
      this.validFrom = validFrom;
      this.validTo = validTo;
    }
  }
  