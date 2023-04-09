import React, { useState, useEffect } from "react";

interface Certificate {
  serialNumber: number;
  issuerCN: string;
  subjectCN: string;
  certificateType: string;
  validFrom: Date;
  validTo: Date;
}

function ViewAllCertificates() {
  const [certificates, setCertificates] = useState<Array<Certificate>>([]);

  useEffect(() => {
    fetch("http://localhost:8081/api/certificate/getAll")
      .then((response) => response.json())
      .then((data) => setCertificates(data))
      .catch((error) => console.error(error));
  }, []);

  return (
    <div>
        <h1>All Certificates</h1>
        <table>
        <thead>
            <tr>
            <th>Serial Number</th>
            <th>Issuer CN</th>
            <th>Subject CN</th>
            <th>Certificate Type</th>
            <th>Valid From</th>
            <th>Valid To</th>
            </tr>
        </thead>
        <tbody>
            {certificates.map((certificate) => (
            <tr key={certificate.serialNumber}>
                <td>{certificate.serialNumber}</td>
                <td>{certificate.issuerCN}</td>
                <td>{certificate.subjectCN}</td>
                <td>{certificate.certificateType}</td>
                <td>{new Date(certificate.validFrom).toLocaleDateString()}</td>
                <td>{new Date(certificate.validTo).toLocaleDateString()}</td>
            </tr>
            ))}
        </tbody>
        </table>
    </div>
  );
}

export default ViewAllCertificates;
