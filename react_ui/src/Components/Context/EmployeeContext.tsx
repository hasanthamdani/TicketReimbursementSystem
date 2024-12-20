import React, { createContext, ReactNode, useContext, useState } from 'react'

export interface Employee {
    username : string;
    password : string;
    isManager: boolean;
}

export interface EmployeeContextType {
    employee: Employee | null;
    isAuth: boolean;
    isManager: boolean;
    login: (username: string, password: string, isManager: boolean) => void;
    logout: () => void;
    register: (username: string, password: string) => void;
}

export const EmployeeContext = createContext<EmployeeContextType | undefined>(undefined); // Defines Context that can be accessed by any child component for Employee characteristics and authorization

// Context Needs Provider with Hook to useState

interface EmployeeProviderProps {
    children: ReactNode;
}

export const EmployeeProvider: React.FC<EmployeeProviderProps> = ({children}) => {
//The functional component will provide context for each of the Children
    const [employee, setEmployee] = useState<Employee | null>(null);
    const [isAuth, setIsAuth] = useState(false);
    const [isManager, setIsManager] = useState(false);

    // Indicates the State has to be an Employee otherwise there is nothing to match

    const login = (username:string, password:string, isManager: boolean) => {
        setIsAuth(true);
    setEmployee({username, password, isManager});  
    setIsManager(isManager)};

    const logout = () => {setEmployee(null); setIsAuth(false);};

    const register = (username:string, password:string) => {
        setIsAuth(true);
        setIsManager(false);
        setEmployee({username, password, isManager}); 
         };

    return (
        <EmployeeContext.Provider value={{employee, isAuth, isManager, login, logout, register}}>
            {children}
        </EmployeeContext.Provider>
    )
}
export const useAuth = () => {
    const context = useContext(EmployeeContext);
    if(!context){
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
}