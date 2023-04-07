import React, { useState } from "react";
import { CertificateData } from "../model/CertificateData";

const NewCertificate = () => {
  const [certificateType, setCertificateType] = useState("");
  const [issuer, setIssuer] = useState("");
  const [validFrom, setValidFrom] = useState("");
  const [validTo, setValidTo] = useState("");
  const [cn, setCn] = useState("");
  const [on, setOn] = useState("");
  const [o, setO] = useState("");
  const [c, setC] = useState("");
  const [name, setName] = useState("");
  const [surname, setSurname] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [email, setEmail] = useState("");
  const [isIssuerDisabled, setIsIssuerDisabled] = useState(false);

  const handleCertificateTypeChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const value = event.target.value;
    setCertificateType(value);

    if (value === 'Selfsigned') {
      setIssuer('');
      setIsIssuerDisabled(true); // onemogućava polje za odabir issuera kada se odabere "Selfsigned" sertifikat
    } else {
      setIsIssuerDisabled(false); // ponovo omogućava polje za odabir issuera kada se odabere neki drugi sertifikat
    }
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

  const handleOChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setO(event.target.value);
  };

  const handleCChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setC(event.target.value);
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
    const certificateData = new CertificateData(cerType, issuer, new Date(validFrom), new Date(validTo), cn, o, on, c, name, surname, phoneNumber, email);

    await fetch("http://localhost:8081/api/certificate/save", {
      method: "POST",
      headers: {
        "Content-type": "application/json",
        "Accept": "application/json",
        'Access-Control-Allow-Origin': '*'
      },
      body: JSON.stringify({
        "type": cerType,
        "issuer": issuer,
        "validFrom": validFrom,
        "validTo": validTo,
        "cn": cn,
        "o":o,
        "on":on,
        "c":c,
        "name":name,
        "surname":surname,
        "phoneNumber":phoneNumber,
        "email": email,
      }),
    }).then(res => {
      console.log(res.text())
    })

  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label htmlFor="certificateType">Certificate Type*</label>
        <select id="certificateType" value={certificateType} onChange={handleCertificateTypeChange} required>
          <option value="">Select Certificate Type</option>
          <option value="Selfsigned">Selfsigned</option>
          <option value="Intermediate">Intermediate</option>
          <option value="End entity">End Entity</option>
        </select>
      </div>
      {!isIssuerDisabled && (<div>
        <label htmlFor="issuer">Issuer*</label>
        <select id="issuer" value={issuer} onChange={handleIssuerChange} required>
          <option value="">Select Issuer</option>
          <option value="Issuer A">Issuer A</option>
          <option value="Issuer B">Issuer B</option>
          <option value="Issuer C">Issuer C</option>
        </select>
      </div>)}
      
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
        <label htmlFor="on">O*</label>
        <input type="text" id="o" value={o} onChange={handleOChange} required />
      </div>
      <div>
        <label htmlFor="on">ON</label>
        <input type="text" id="on" value={on} onChange={handleOnChange} />
      </div>
      <div>
        <label htmlFor="on">C</label>
        <input type="text" id="c" value={c} onChange={handleCChange} />
      </div>
      <div>
        <label htmlFor="name">Name</label>
        <input type="text" id="name" value={name} onChange={handleNameChange} />
      </div>
      <div>
        <label htmlFor="surname">Surname</label>
        <input type="text" id="surname" value={surname} onChange={handleSurnameChange} />
      </div>
      <div>
        <label htmlFor="phoneNumber">Phone Number</label>
        <input type="text" id="phoneNumber" value={phoneNumber} onChange={handlePhoneNumberChange} />
      </div>
      <div>
        <label htmlFor="email">Email</label>
        <input type="email" id="email" value={email} onChange={handleEmailChange} />
      </div>
      <button type="submit">Submit</button>
    </form>
  );
};

export default NewCertificate;

