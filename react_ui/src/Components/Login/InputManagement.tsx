import React, { FormEvent, useContext, useState } from 'react'
import InputForm from './InputForm'
import { EmployeeContext, useAuth } from '../Context/EmployeeContext';
import NavBar from '../NavBar/NavBar';




function LoginManagement() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  //Set State for Consistency inside React Functional Component
  const {employee} = useAuth();
  const {login} = useAuth();
  const {logout} = useAuth();
  const {register} = useAuth();
  const {isAuth} = useAuth();
  
  
  function handleForm(event: FormEvent)
  { 
    event.preventDefault();
    //login
    if(!username || !password)
      {
        alert("Your form is empty");
      }
    else
    {
      
      if (event.target === document.getElementById("login"))
      {
        console.log("login success");
        // Need to incorporate into routes to navigate to navbar.
        
      }
      else if(event.target === document.getElementById("register"))
      {
        console.log("registration success");
      }
    }
    
  }
  return (
    <div id = "homediv">
        <header id="loginTitle"><strong>Ticket Reimbursement</strong></header>
        <InputForm username={username} setUsername={setUsername} password = {password} setPassword = {setPassword} handleForm = {handleForm}/>
    </div>
  )
}

export default LoginManagement