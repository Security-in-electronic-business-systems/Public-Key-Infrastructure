import { Route, Routes } from 'react-router-dom'
import './App.css'
import Navbar from './components/NavBar'
import AppRouter from './router/AppRouter'
import Login from './components/Login'
import NewCertificate from './components/NewCertificate'
import RevokeCertificate from './components/RevokeCertificate'
import ViewAllCertificates from './components/ViewAllCerificates'
import ViewCertificate from './components/ViewCertificate'

function App() {
  return (
  <>
    <Navbar />
    <div>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/newSertificate" element={<NewCertificate />} />
        <Route path="/revokeCertificate" element={<RevokeCertificate />} />
        <Route path="/viewAll" element={<ViewAllCertificates />} />
        <Route path="/viewCertificate" element={<ViewCertificate />} />
      </Routes>
    </div>
  </>
  )
  
}

export default App
