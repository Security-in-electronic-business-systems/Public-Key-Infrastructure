import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import NewCertificate from "../components/NewCertificate";
import RevokeCertificate from "../components/RevokeCertificate";

const AppRouter: React.FC = () => {
  return (
    <Router>
      <Routes>
        <Route path="/newSertificate" element={<NewCertificate />} />
        <Route path="/revokeCertificate" element={<RevokeCertificate />} />
      </Routes>
    </Router>
  );
};

export default AppRouter;