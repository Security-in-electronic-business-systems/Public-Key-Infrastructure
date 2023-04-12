import { Link, useNavigate  } from 'react-router-dom';
  

function Navbar() {

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
    </nav>
  );
}

export default Navbar;