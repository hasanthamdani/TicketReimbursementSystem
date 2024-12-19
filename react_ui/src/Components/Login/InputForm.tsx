import React, { FormEventHandler, MouseEventHandler } from 'react'

// Access State from Management Application, Return Values on Submit

type InputFormTypeProp = {username: string, password: string, setUsername: React.Dispatch<React.SetStateAction<string>>, setPassword: React.Dispatch<React.SetStateAction<string>>, handleForm:MouseEventHandler<HTMLButtonElement>}

function InputForm(prop : InputFormTypeProp) {
  return (
         <form className = "inputForm">
              <label className = "inputMain" htmlFor = "username">Username: </label> <br/>
              <input className = "inputMain" type = "text" value = {prop.username} onChange={(e:any) => prop.setUsername(e.target.value)}>
              </input> <br/>

              <label className = "inputMain" htmlFor="password">Pasword:</label><br/>
              <input className = "inputMain" type = "password" value = {prop.password} onChange = {(e:any) => prop.setPassword(e.target.value)}></input>
                <br/>
                <br/>
            <span>
                <button type="submit" onClick = {prop.handleForm} id =  "login">Login</button>
                <button type="submit" onClick = {prop.handleForm} id = "register">Register</button>
            </span>
              
         </form>
  )
}

export default InputForm