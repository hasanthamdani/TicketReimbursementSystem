import React from 'react'
import { useAuth } from '../Context/EmployeeContext';

function Home() {
    const {isAuth, employee, isManager} = useAuth();

  return (
    <div><h1>Ticket Reimbursement Application</h1> <br/>

        {
            !isAuth && <>
                <h5>Register if you are an Employee, and Sign in if you are a Manager</h5>
            </> 
        }
        
        {
            isAuth && <>
             <h5>Welcome, {employee?.username} </h5> <br/>
            </>  
        }
       
        {
            isAuth && !isManager && <p>This application will allow you to create tickets with the <b>create</b> tab and view past tickets with the <b> history </b> tab.</p>
        }
        
        {
            isAuth && isManager && <p>This application will allow you to resolve employee tickets with the <b>resolve</b> tab and view current tickets with the <b> view </b>tab.</p>
        }
    </div>
  )
}

export default Home