import React, { useState } from "react";
import { CertificateData } from "../model/CertificateData";

const NewCertificate = () => {
  const [certificateType, setCertificateType] = useState("");
  const [issuer, setIssuer] = useState("");
  const [validFrom, setValidFrom] = useState("");
  const [validTo, setValidTo] = useState("");
  const [cn, setCn] = useState("");
  const [on, setOn] = useState("");
  const [name, setName] = useState("");
  const [surname, setSurname] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [email, setEmail] = useState("");

  const handleCertificateTypeChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setCertificateType(event.target.value);
  };

  const handleIssuerChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setIssuer(event.target.value);
  };

  const handleValidFromChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setValidFrom(event.target.value);
  };

  const handleValidToChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setValidTo(event.target.value);
  };

  const handleCnChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setCn(event.target.value);
  };

  const handleOnChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setOn(event.target.value);
  };

  const handleNameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setName(event.target.value);
  };

  const handleSurnameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSurname(event.target.value);
  };

  const handlePhoneNumberChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPhoneNumber(event.target.value);
  };

  const handleEmailChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(event.target.value);
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    var cerType;

    if(certificateType === "Intermediate"){
      cerType = 1;
    }else{
      cerType = 2;
    }

    //ovdje moras da namjestis serial number od issuera
    const certificateData = new CertificateData(cerType, issuer, new Date(validFrom), new Date(validTo), cn, on, name, surname, phoneNumber, email);

    await fetch("http://localhost:8081/api/certificate", {
      method: "POST",
      headers: {
        "Content-type": "application/json",
      },
      credentials: "include",
      body: JSON.stringify({
        certificateData
      }),
    }).then(res => res.json())
      .then(data => {})

  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label htmlFor="certificateType">Certificate Type*</label>
        <select id="certificateType" value={certificateType} onChange={handleCertificateTypeChange} required>
          <option value="">Select Certificate Type</option>
          <option value="Intermediate">Intermediate</option>
          <option value="End entity">End Entity</option>
        </select>
      </div>
      <div>
        <label htmlFor="issuer">Issuer*</label>
        <select id="issuer" value={issuer} onChange={handleIssuerChange} required>
          <option value="">Select Issuer</option>
          <option value="Issuer A">Issuer A</option>
          <option value="Issuer B">Issuer B</option>
          <option value="Issuer C">Issuer C</option>
        </select>
      </div>
      <div>
        <label htmlFor="validFrom">Valid From*</label>
        <input type="date" id="validFrom" value={validFrom} onChange={handleValidFromChange} required />
      </div>
      <div>
        <label htmlFor="validTo">Valid To*</label>
        <input type="date" id="validTo" value={validTo} onChange={handleValidToChange} required />
      </div>
      <div>
        <label htmlFor="cn">CN*</label>
        <input type="text" id="cn" value={cn} onChange={handleCnChange} required />
      </div>
      <div>
        <label htmlFor="on">ON*</label>
        <input type="text" id="on" value={on} onChange={handleOnChange} required />
      </div>
      <div>
        <label htmlFor="name">Name*</label>
        <input type="text" id="name" value={name} onChange={handleNameChange} required />
      </div>
      <div>
        <label htmlFor="surname">Surname*</label>
        <input type="text" id="surname" value={surname} onChange={handleSurnameChange} required />
      </div>
      <div>
        <label htmlFor="phoneNumber">Phone Number*</label>
        <input type="text" id="phoneNumber" value={phoneNumber} onChange={handlePhoneNumberChange} required />
      </div>
      <div>
        <label htmlFor="email">Email*</label>
        <input type="email" id="email" value={email} onChange={handleEmailChange} required />
      </div>
      <button type="submit">Submit</button>
    </form>
  );
};

export default NewCertificate;

