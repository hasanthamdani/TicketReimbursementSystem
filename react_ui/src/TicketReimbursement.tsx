import React from 'react';
import './App.css';
import { Route, Routes } from "react-router-dom";
import InputManagement from './Components/Login/InputManagement';
import { EmployeeProvider } from './Components/Context/EmployeeContext';
import RouteManagerGuard from './Components/Routes/RouteManagerGuard/RouteManagerGuard';
import NavBar from './Components/NavBar/NavBar';


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
             <NavBar/>
            {/*<Routes>
              <RouteManagerGuard>
                <Route path="/view"></Route>
                <Route path="/assign"></Route>
              </RouteManagerGuard>
              <Route path = "/create"></Route>
              <Route path = "/past"></Route>
            </Routes> */}
          </EmployeeProvider>
          
    </div>
  );
}

export default TicketReimbursement;
