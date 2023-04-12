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

  const handleValidate = async (serialNumber: number) => {
  
     fetch(`http://localhost:8081/api/certificate/validate/${serialNumber}`)
     .then((response) => alert(response.text()));

      
  
  };

    const handleRevoke = async (serialNumber: number) => {
    await fetch(`http://localhost:8081/api/certificate/revoke/${serialNumber}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Failed to revoke certificate');
        }
        console.log('Certificate revoked successfully');
      })
      .catch(error => {
        console.error(error);
      });
  };
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
            <th>Validate</th>
            <th>Revoke</th>
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
                <td> <button onClick={() => handleValidate(certificate.serialNumber)}>Validate</button> </td>
                <td> <button onClick={() => handleRevoke(certificate.serialNumber)}>Revoke</button> </td>
            </tr>
            ))}
        </tbody>
        </table>
    </div>
  );
}

export default ViewAllCertificates;
