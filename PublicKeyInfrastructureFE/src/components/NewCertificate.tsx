import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useCurrentCertificate } from "../hooks/getCurrentCertificate";

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

  const [SERVER_AUTH, setSERVER_AUTH] = useState(false);
  const [CLIENT_AUTH, setCLIENT_AUTH] = useState(false);
  const [CODE_SIGNING, setCODE_SIGNING] = useState(false);
  const [EMAIL_PROTECTION, setEMAIL_PROTECTION] = useState(false);
  const [TIME_STAMPING, setTIME_STAMPING] = useState(false);
  const [OCSP_SIGNING, setOCSP_SIGNING] = useState(false);

  const [DIGITAL_SIGNATURE, setDIGITAL_SIGNATURE] = useState(false);
  const [NON_REPUDIATION, setNON_REPUDIATION] = useState(false);
  const [KEY_ENCIPHERMENT, setKEY_ENCIPHERMENT] = useState(false);
  const [DATA_ENCIPHERMENT, setDATA_ENCIPHERMENT] = useState(false);
  const [KEY_AGREEMENT, setKEY_AGREEMENT] = useState(false);
  const [KEY_CERT_SIGN, setKEY_CERT_SIGN] = useState(false);
  const [ENCIPHER_ONLY, setENCIPHER_ONLY] = useState(false);
  const [DECIPHER_ONLY, setDECIPHER_ONLY] = useState(false);
  



  const certificate = useCurrentCertificate();

  const navigate = useNavigate();

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

  const hendleSERVER_AUTH = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSERVER_AUTH(event.target.checked);
    console.log(event.target.checked)
  };

  const hendleCLIENT_AUTH = (event: React.ChangeEvent<HTMLInputElement>) => {
    setCLIENT_AUTH(event.target.checked);
  };

  const hendleCODE_SIGNING = (event: React.ChangeEvent<HTMLInputElement>) => {
    setCODE_SIGNING(event.target.checked);
  };

  const hendleEMAIL_PROTECTION = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEMAIL_PROTECTION(event.target.checked);
  };

  const hendleTIME_STAMPING = (event: React.ChangeEvent<HTMLInputElement>) => {
    setTIME_STAMPING(event.target.checked);
  };

  const hendleOCSP_SIGNING = (event: React.ChangeEvent<HTMLInputElement>) => {
    setOCSP_SIGNING(event.target.checked);
  };

  const hendleDIGITAL_SIGNATURE = (event: React.ChangeEvent<HTMLInputElement>) => {
    setDIGITAL_SIGNATURE(event.target.checked);
  };

  const hendleNON_REPUDIATION = (event: React.ChangeEvent<HTMLInputElement>) => {
    setNON_REPUDIATION(event.target.checked);
  };

  const hendleKEY_ENCIPHERMENT = (event: React.ChangeEvent<HTMLInputElement>) => {
    setKEY_ENCIPHERMENT(event.target.checked);
  };

  const hendleDATA_ENCIPHERMENT = (event: React.ChangeEvent<HTMLInputElement>) => {
    setDATA_ENCIPHERMENT(event.target.checked);
  };

  const hendleKEY_AGREEMENT = (event: React.ChangeEvent<HTMLInputElement>) => {
    setKEY_AGREEMENT(event.target.checked);
  };

  const hendleKEY_CERT_SIGN = (event: React.ChangeEvent<HTMLInputElement>) => {
    setKEY_CERT_SIGN(event.target.checked);
  };

  const hendleENCIPHER_ONLY = (event: React.ChangeEvent<HTMLInputElement>) => {
    setENCIPHER_ONLY(event.target.checked);
  };

  const hendleDECIPHER_ONLY = (event: React.ChangeEvent<HTMLInputElement>) => {
    setDECIPHER_ONLY(event.target.checked);
    console.log(event.target.checked)
  };
  


  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    var cerType;
    if(certificateType === "Selfsigned"){
      cerType = 0;
    }
    else if(certificateType === "Intermediate"){
      cerType = 1;
    }else{
      cerType = 2;
    }

    //ovdje moras da namjestis serial number od issuera
    

    await fetch("http://localhost:8081/api/certificate/save", {
      method: "POST",
      headers: {
        "Content-type": "application/json",
        "Accept": "application/json",
        'Access-Control-Allow-Origin': '*'
      },
      body: JSON.stringify({
        "type": cerType,
        "issuer": certificate?.serialNumber,
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
        "serverAuth": SERVER_AUTH,
        "clientAuth": CLIENT_AUTH,
        "codeSign": CODE_SIGNING,
        "emailProtection": EMAIL_PROTECTION,
        "timeStamping": TIME_STAMPING,
        "ocspSigning": OCSP_SIGNING,
        "digitalSignature": DIGITAL_SIGNATURE,
        "nonRepudiation": NON_REPUDIATION,
        "keyEnciphement": KEY_ENCIPHERMENT,
        "dataEnciphement": DATA_ENCIPHERMENT,
        "keyAgriment": KEY_AGREEMENT,
        "keyCertSign": KEY_CERT_SIGN,
        "enhipterOnly": ENCIPHER_ONLY,
        "decipherOnly": DECIPHER_ONLY,
      }),
    }).then(res => {
      console.log(res.text())
      if(certificate?.certificateType == "SELF_SIGNED"){
        navigate("/viewAll")
      }
    })

  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label htmlFor="certificateType">Certificate Type*</label>
        <select id="certificateType" value={certificateType} onChange={handleCertificateTypeChange} required>
          <option value="">Select Certificate Type</option>
          {certificate?.certificateType !== "INTERMEDIATE" && <option value="Selfsigned">Selfsigned</option>}
          <option value="Intermediate">Intermediate</option>
          <option value="End entity">End Entity</option>
        </select>
      </div>
      <div>
        <table>
          <tr>
            <td>
            <h4>Extended key purpose</h4>
            </td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>
            <h4>Key purpose</h4>
            </td>
          </tr>
          <tr>
      <td>
       <div>
        <table>
          <tr>
            <td><input type="checkbox" id="SERVER_AUTH" checked ={SERVER_AUTH} onChange ={hendleSERVER_AUTH}/></td>
            <td><label htmlFor="validFrom">Server auth</label></td>
            <td>&nbsp;</td>
            <td><input type="checkbox" id="CLIENT_AUTH" checked={CLIENT_AUTH} onChange={hendleCLIENT_AUTH}/></td>
            <td><label htmlFor="validFrom">Client auth</label></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="CODE_SIGNING" checked={CODE_SIGNING} onChange={hendleCODE_SIGNING}/></td>
            <td><label htmlFor="validFrom">Code signing</label></td>
            <td>&nbsp;</td>
            <td><input type="checkbox" id="EMAIL_PROTECTION" checked={EMAIL_PROTECTION} onChange={hendleEMAIL_PROTECTION}/></td>
            <td><label htmlFor="validFrom">Email protection</label></td>
          </tr>
          <tr>
            <td><input type="checkbox" id="TIME_STAMPING" checked={TIME_STAMPING} onChange={hendleTIME_STAMPING}/></td>
            <td><label htmlFor="validFrom">Time stamping</label></td>
            <td>&nbsp;</td>
            <td><input type="checkbox" id="OCSP_SIGNING" checked={OCSP_SIGNING} onChange={hendleOCSP_SIGNING}/></td>
            <td><label htmlFor="validFrom">OCSP signing</label></td>
          </tr>
        </table>
      </div>
    </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>
    <div>
        <table>
        <tr>
          <td><input type="checkbox" id="DIGITAL_SIGNATURE" checked={DIGITAL_SIGNATURE} onChange={hendleDIGITAL_SIGNATURE}/></td>
          <td><label htmlFor="validFrom">Digutal signature</label></td>
          <td>&nbsp;</td>
          <td><input type="checkbox" id="NON_REPUDIATION" checked={NON_REPUDIATION} onChange={hendleNON_REPUDIATION}/></td>
          <td><label htmlFor="validFrom">Non repudiation</label></td>
        </tr>
        <tr>
          <td><input type="checkbox" id="KEY_ENCIPHERMENT" checked={KEY_ENCIPHERMENT} onChange={hendleKEY_ENCIPHERMENT}/></td>
          <td><label htmlFor="validFrom">Key encipherment</label></td>
          <td>&nbsp;</td>
          <td><input type="checkbox" id="DATA_ENCIPHERMENT" checked={DATA_ENCIPHERMENT} onChange={hendleDATA_ENCIPHERMENT}/></td>
          <td><label htmlFor="validFrom">Data encipherment</label></td>
        </tr>
        <tr>
          <td><input type="checkbox" id="KEY_AGREEMENT" checked={KEY_AGREEMENT} onChange={hendleKEY_AGREEMENT}/></td>
          <td><label htmlFor="validFrom">Key agreement</label></td>
          <td>&nbsp;</td>
          <td><input type="checkbox" id="KEY_CERT_SIGN" checked={KEY_CERT_SIGN} onChange={hendleKEY_CERT_SIGN}/></td>
          <td><label htmlFor="validFrom">Key cert sign</label></td>
        </tr>
        <tr>
          <td><input type="checkbox" id="ENCIPHER_ONLY" checked={ENCIPHER_ONLY} onChange={hendleENCIPHER_ONLY}/></td>
          <td><label htmlFor="validFrom">Encipher only</label></td>
          <td>&nbsp;</td>
          <td><input type="checkbox" id="DECIPHER_ONLY" checked={DECIPHER_ONLY} onChange={hendleDECIPHER_ONLY}/></td>
          <td><label htmlFor="validFrom">Decipher only</label></td>
        </tr>
        </table>
      </div>      
    </td>
          </tr>
        </table>
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

