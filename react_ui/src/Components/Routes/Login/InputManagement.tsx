import { useState } from 'react'
import InputForm from './InputForm'
import { Employee, useAuth } from '../../Context/EmployeeContext';
import axios from "axios";
import { useNavigate } from 'react-router-dom';




function InputManagement() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();

  //Set State for Consistency inside React Functional Component
  const {login, register, isAuth, isManager} = useAuth();
  
  
  async function handleForm(event : any)
  { 

    event.preventDefault();
    //login
    if(!username || !password)
      {
        setErrorMessage("Your form is empty");
      }
    else
    {
     
      if (event.target === document.getElementById("login"))
      {
        try{
          const response = await axios.get("http://localhost:8080/login/"+username)
          console.log(response.data);
          login(username, password, response.data.manager);
          console.log("login success");
          setErrorMessage("login success");
          // Need to incorporate into routes to navigate to navbar. 
          navigate("/");
          
        }
        catch(error : any)
        {
          if(error.request) // Request made with no Response Back
          {
              console.log(error.status);
          }
          setErrorMessage('Error: Incorrect login information');
        }
      }

      else if(event.target === document.getElementById("register"))
      {
        
        let employeeCheck : Employee = {username: username, password: password, isManager:false};

        try
        {
          const response = await axios.post("http://localhost:8080/register", 
          employeeCheck, {
            headers:{"Content-Type": "application/json"}
            })
          console.log(response.data);
          register(username, password);
          setErrorMessage("registration success");
          navigate("/");

        }
        catch(error)
        {
          setErrorMessage('Error: Incorrect registration information');

        }
    }
  }
  }
  return (
    <>
    <div id = "homediv">
        <InputForm username={username} setUsername={setUsername} password = {password} setPassword = {setPassword} handleForm = {handleForm} errorMessage = {errorMessage}/>
    </div>
      
    </>
  );
}

export default InputManagement