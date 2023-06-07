import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import NewCertificate from "../components/NewCertificate";
import RevokeCertificate from "../components/RevokeCertificate";
import ViewAllCertificates from "../components/ViewAllCerificates";
import ViewCertificate from "../components/ViewCertificate";
import Login from "../components/Login";
import Navbar from "../components/NavBar";

const AppRouter: React.FC = () => {
  return (
    <>
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/newSertificate" element={<NewCertificate />} />
        <Route path="/revokeCertificate" element={<RevokeCertificate />} />
        <Route path="/viewAll" element={<ViewAllCertificates />} />
        <Route path="/viewCertificate" element={<ViewCertificate />} />
      </Routes>
    </Router>
    </>
  );
};

export default AppRouter;