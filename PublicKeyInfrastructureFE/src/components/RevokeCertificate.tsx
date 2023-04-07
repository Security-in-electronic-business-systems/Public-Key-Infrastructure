import { useState } from 'react';

function RevokeCertificate() {
  const [serialNumber, setSerialNumber] = useState('');

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSerialNumber(event.target.value);
  }

  const handleRevokeClick = (event:React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();
    
    fetch("http://localhost:8081/api/certificate/revokeCertificate", {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ serialNumber })
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
  }

  return (
    <div className="container">
      <h1>Revoke Certificate</h1>
      <form>
        <div className="form-group">
          <label htmlFor="serialNumber">Serial Number</label>
          <input type="text" className="form-control" id="serialNumber" value={serialNumber} onChange={handleInputChange} />
        </div>
        <button type="submit" className="btn btn-primary" onClick={handleRevokeClick}>Revoke</button>
      </form>
    </div>
  );
}

export default RevokeCertificate;
