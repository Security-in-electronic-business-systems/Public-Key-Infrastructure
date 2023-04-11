import React, { useState } from 'react';
import { Container, Form, Button } from 'react-bootstrap';
import { useNavigate } from "react-router-dom";
import { CertificateType } from '../model/CertificateData';

const Login = () => {
  const [serialNumber, setSerialNumber] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const response = await fetch(`http://localhost:8081/api/certificate/getBySerialNumber`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ serialNumber })
      }).then(res => res.json())
        .then(data => {
      
            var cert: Certificate = data
            localStorage.setItem('currentCertificate', JSON.stringify(cert));

            if(cert.certificateType == "SELF_SIGNED" || cert.certificateType =="INTERMEDIATE"){
              navigate("/newSertificate")
            }else{
              navigate("/viewCertificate")
            }

            
          })
    };

  return (
    <Container className="d-flex justify-content-center align-items-center" style={{ height: '100vh' }}>
      <Form onSubmit={handleSubmit} className="text-center">
        <Form.Group controlId="formSerialNumber" className="mb-3">
          <Form.Label className="mb-0">Serijski broj</Form.Label>
          <Form.Control
            type="text"
            value={serialNumber}
            onChange={(event) => setSerialNumber(event.target.value)}
          />
        </Form.Group>
        <Button variant="primary" type="submit">Prijavi se</Button>
      </Form>
    </Container>
  );
};

export default Login;
