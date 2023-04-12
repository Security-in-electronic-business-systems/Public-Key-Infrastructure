import React, { useState } from 'react';
import { useCurrentCertificate } from '../hooks/getCurrentCertificate';

interface Certificate {
  serialNumber: number;
  issuerCN: string;
  subjectCN: string;
  certificateType: string;
  validFrom: Date;
  validTo: Date;
}

function ViewCertificate() {
  const [serialNumber, setSerialNumber] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const certificate = useCurrentCertificate()

  const downloadCertificate = async () => {
    try {
      const response = await fetch(`http://localhost:8081/api/certificate/download/${certificate?.serialNumber}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/octet-stream',
        },
        responseType: 'blob',
      } as RequestInit & { responseType: 'blob' });

      if (response.ok) {
        const blob = await response.blob();
        const url = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', `${certificate?.serialNumber}.cer`);
        document.body.appendChild(link);
        link.click();
        link.parentNode?.removeChild(link);
      } else {
        setError(`HTTP error: ${response.status}`);
      }
    } catch (error) {
      setError(`Error: ${error}`);
    }
  };

  const handleSerialNumberChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSerialNumber(event.target.value);
  };

  return (
    <div>
      <h1>View Certificate</h1>
      {error && (
        <div>
          <p style={{ color: 'red' }}>{error}</p>
        </div>
      )}

      {certificate && (
        <div>
          <h2>Certificate Details</h2>

          <div>
            <label htmlFor="issuerCN">Issuer Common Name:</label>
            <span id="issuerCN">{certificate.issuerCN}</span>
          </div>

          <div>
            <label htmlFor="subjectCN">Subject Common Name:</label>
            <span id="subjectCN">{certificate.subjectCN}</span>
          </div>

          <div>
            <label htmlFor="certificateType">Certificate Type:</label>
            <span id="certificateType">{certificate.certificateType}</span>
          </div>

          <div>
            <label htmlFor="validFrom">Valid From:</label>
            <span id="validFrom">{new Date(certificate.validFrom).toLocaleDateString()}</span>
          </div>

          <div>
            <label htmlFor="validTo">Valid To:</label>
            <span id="validTo">{new Date(certificate.validTo).toLocaleDateString()}</span>
          </div>
          <button id="download-btn" onClick={downloadCertificate}>Download Certificate</button>
        </div>
      )}
    </div>
  );
};

export default ViewCertificate;