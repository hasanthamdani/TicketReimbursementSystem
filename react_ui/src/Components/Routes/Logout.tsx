import React from 'react'
import { useAuth } from '../Context/EmployeeContext';
import { Navigate } from 'react-router-dom';

function Logout() {

    const {logout} = useAuth();
    logout();
  return (
    <div>
        <Navigate to="/"></Navigate>
    </div>
  )
}

export default Logout