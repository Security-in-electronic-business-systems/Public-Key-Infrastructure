import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import NewCertificate from "../components/NewCertificate";
import RevokeCertificate from "../components/RevokeCertificate";
import ViewAllCertificates from "../components/ViewAllCerificates";
import ViewCertificate from "../components/ViewCertificate";

const AppRouter: React.FC = () => {
  return (
    <Router>
      <Routes>
        <Route path="/newSertificate" element={<NewCertificate />} />
        <Route path="/revokeCertificate" element={<RevokeCertificate />} />
        <Route path="/viewAll" element={<ViewAllCertificates />} />
        <Route path="/viewCertificate" element={<ViewCertificate />} />
      </Routes>
    </Router>
  );
};

export default AppRouter;