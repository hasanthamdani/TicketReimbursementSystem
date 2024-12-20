import React from 'react';
import './App.css';
import { Route, Routes } from "react-router-dom";
import { EmployeeProvider } from './Components/Context/EmployeeContext';
import NavBar from './Components/NavBar/NavBar';
import SmartViewer from './Components/Routes/Manager/ticketViewer/SmartViewer'
import InputManagement from './Components/Routes/Login/InputManagement';
import Home from './Components/Home/Home';
import SmartCreator from './Components/Routes/Employee/ticketCreator/SmartCreator';
import RouteGuard from './Components/Routes/RouteGuard/RouteGuard';
import SmartHistory from './Components/Routes/Employee/ticketHistory/SmartHistory';
import TicketResolver from './Components/Routes/Manager/ticketViewer/ticketResolver/TicketResolver';
import SmartResolver from './Components/Routes/Manager/ticketViewer/ticketResolver/SmartResolver';
import Logout from './Components/Routes/Logout';


function TicketReimbursement() {

  /*
    HOC Auth
      LoginContext Provider
        Username
        Password  
        Register
        Login
  */
  return (
    <div className = "main">
      <EmployeeProvider>
        <NavBar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<InputManagement />} />
          
          {/* Parent Route for SmartViewer */}
          <Route path="/view" element={<RouteGuard><SmartViewer /></RouteGuard>} />

          {/* Separate Route for SmartResolver (with the dynamic ticket ID) */}
          <Route path="/view/:id" element={<RouteGuard><SmartResolver /></RouteGuard>} />
          
          <Route path="/create" element={<RouteGuard><SmartCreator /></RouteGuard>} />
          <Route path="/history" element={<RouteGuard><SmartHistory /></RouteGuard>} />
          <Route path="/logout" element={<Logout />} />
        </Routes>
      </EmployeeProvider>
          
    </div>
  );
}

export default TicketReimbursement;
