import React, { ReactNode } from 'react'
import { Navigate } from 'react-router-dom';
import { useAuth } from '../../Context/EmployeeContext';

interface RouteGuardProp {
    children: ReactNode;
}
function RouteGuard(prop : RouteGuardProp) {
    const{isAuth} = useAuth();
    
    if(!isAuth)
    {
        return <Navigate to="/login"></Navigate>
    }
  return ( // for any react node that isn't authorized, they can only log in
    <>{prop.children}</>
  );
}

export default RouteGuard