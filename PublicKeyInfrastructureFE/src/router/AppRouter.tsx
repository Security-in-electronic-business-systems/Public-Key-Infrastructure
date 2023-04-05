import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import NewCertificate from "../components/NewCertificate";

const AppRouter: React.FC = () => {
  return (
    <Router>
      <Routes>
        <Route path="/newSertificate" element={<NewCertificate />} />
      </Routes>
    </Router>
  );
};

export default AppRouter;