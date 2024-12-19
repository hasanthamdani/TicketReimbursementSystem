import React, { ReactNode, useContext } from 'react'
import { Navigate } from 'react-router-dom';
import { EmployeeContext, useAuth } from '../../Context/EmployeeContext';


interface RouteGuardProps {
    children: ReactNode;
}

function RouteManagerGuard({children}: {children: ReactNode}) {
    const {isManager} = useAuth();

    if(!isManager){
        return <Navigate to="/create" replace/>;
    }

    return <>{children}</>;
}

export default RouteManagerGuard