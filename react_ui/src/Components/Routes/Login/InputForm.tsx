import React, { FormEventHandler, MouseEventHandler } from 'react'

// Access State from Management Application, Return Values on Submit

type InputFormTypeProp = {username: string, password: string, setUsername: React.Dispatch<React.SetStateAction<string>>, setPassword: React.Dispatch<React.SetStateAction<string>>, handleForm:MouseEventHandler<HTMLButtonElement>,errorMessage: string | null}

function InputForm(prop : InputFormTypeProp) {
  return (
        //  <form className = "inputForm">
        //       <label className = "inputMain" htmlFor = "username">Username: </label> <br/>
        //       <input className = "inputMain" type = "text" value = {prop.username} onChange={(e:any) => prop.setUsername(e.target.value)} required>
        //       </input> <br/>

        //       <label className = "inputMain" htmlFor="password">Pasword:</label><br/>
        //       <input className = "inputMain" type = "password" value = {prop.password} onChange = {(e:any) => prop.setPassword(e.target.value)}required></input>
        //         <br/>
        //         <br/>
        //     <span>
        //         <button type="submit" onClick = {prop.handleForm} id =  "login">Login</button>
        //         <button type="submit" onClick = {prop.handleForm} id = "register">Register</button>
        //     </span>
        //   </form>
        <div className = "form">
            <form className="inputForm">
        <h1 className="h3 mb-3 font-weight-normal">Ticket Reimbursement System</h1>
        <label htmlFor="username" className="sr-only">Username</label>
        <input type="text" id="username" className="form-control" placeholder="Username" value = {prop.username} onChange={(e:any) => prop.setUsername(e.target.value)} required/>
        <label htmlFor="password" className="sr-only">Password</label>
        <input type="password" id="password" className="form-control" placeholder="Password" required value = {prop.password} onChange = {(e:any) => prop.setPassword(e.target.value)}/> <br/>

        <button className="btn btn-lg btn-primary btn-block" type="submit" id = "login" onClick = {prop.handleForm}>Sign in</button>
        <button className="btn btn-lg btn-primary btn-block" type="submit" id = "register" onClick = {prop.handleForm}>Register</button>
      <div style={{all: "initial"}} className="quick-apply-extension-root"></div>
      </form>
        <p>{prop.errorMessage}</p>
        </div>
      

  )
}

export default InputForm