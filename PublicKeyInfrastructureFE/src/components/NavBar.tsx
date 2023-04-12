import { Button } from 'react-bootstrap';
import { Link, useNavigate  } from 'react-router-dom';
import { useCurrentCertificate } from '../hooks/getCurrentCertificate';
import { useEffect } from 'react';
  

function Navbar() {

    const navigate = useNavigate()
    const certificate = useCurrentCertificate()

    const handleButtonClick = () => {
      localStorage.setItem("currentCertificate", "")
      navigate("/login")
      window.location.reload()
    }

    const stylesRight = {
        marginLeft: 'auto',
      };

    const stylesLeft  = {
        marginRight: 'auto',
      };

    const navStyle = {
        display: 'flex',
        justifyContent: 'space-between',
    }

  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light" style={navStyle}>
      <ul className="navbar-nav mr-auto" style={stylesLeft}>
        <li className="nav-item active">
          <Link className="nav-link" to="/newSertificate">Create new certificate</Link>
        </li>
        <li className="nav-item active">
          <Link className="nav-link" to="/viewAll">View all certificates</Link>
        </li>
        <li className="nav-item active">
          <Link className="nav-link" to="/viewCertificate">Your certificate</Link>
        </li>
      </ul>
      {certificate != null && 
      <ul className="navbar-nav mr-auto" style={stylesRight}>
      <li className="nav-item active" >
          <button className="btn btn-outline-success my-2 my-sm-0" type="submit" onClick={handleButtonClick}>Logout</button>
      </li>
      
    </ul>}
      
    </nav>
  );
}

export default Navbar;