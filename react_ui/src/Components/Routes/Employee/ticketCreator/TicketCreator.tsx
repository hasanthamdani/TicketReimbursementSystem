import React, { FormEventHandler } from 'react'

type TicketCreatorTypeProp = {amount : number, description: string, setAmount: React.Dispatch<React.SetStateAction<number>>, setDescription: React.Dispatch<React.SetStateAction<string>>, handleForm:FormEventHandler<HTMLFormElement>, ref: React.Ref<HTMLFormElement>}

function TicketCreator(prop : TicketCreatorTypeProp) {
  return (
    /*
      htmlForm htmlFor 
    */
    <div>
      <form ref = {prop.ref} onSubmit={prop.handleForm} id = "createForm">
      <div className="htmlForm-group">
        <label htmlFor="examplehtmlFormControlInput1">amount for Reimbursement</label> <br/>

        <input type="text" inputMode='numeric' className="htmlForm-control" id="examplehtmlFormControlInput1" placeholder="$" onChange = {(e:any) => prop.setAmount(e.target.value as number)} required/>

      </div>

        <label htmlFor="examplehtmlFormControlTextarea1">Description of Issue</label><br/>
        
        <textarea onChange = {(e:any) => prop.setDescription(e.target.value)} className="htmlForm-control" id="examplehtmlFormControlTextarea1" rows={5} placeholder="Discuss reasoning for ticket" required></textarea> <br/>

        <button type = "submit">Submit Ticket</button>  

      </form>
    </div>
  )
}

export default TicketCreator