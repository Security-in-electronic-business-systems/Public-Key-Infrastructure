import React, { useState } from 'react';

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
  const [certificate, setCertificate] = useState<Certificate | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleSerialNumberChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSerialNumber(event.target.value);
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setLoading(true);
    setError('');

    try {
      const response = await fetch(`http://localhost:8081/api/certificate/getBySerialNumber`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ serialNumber })
      });

      if (response.ok) {
        const data = await response.json();
        setCertificate(data);
      } else {
        setError(`HTTP error: ${response.status}`);
      }
    } catch (error) {
      setError(`Error: ${error}`);
    }

    setLoading(false);
  };

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


  return (
    <div>
      <h1>View Certificate</h1>

      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="serialNumber">Serial Number:</label>
          <input
            type="text"
            id="serialNumber"
            name="serialNumber"
            value={serialNumber}
            onChange={handleSerialNumberChange}
            required
          />
        </div>

        <button type="submit" disabled={loading}>
          {loading ? 'Loading...' : 'Submit'}
        </button>
      </form>

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
