import React, { FormEventHandler } from 'react'

type TicketCreatorTypeProp = {amount : number, description: string, setAmount: React.Dispatch<React.SetStateAction<number>>, setDescription: React.Dispatch<React.SetStateAction<string>>, handleForm:FormEventHandler<HTMLFormElement>, ref: React.Ref<HTMLFormElement>, errorMessage: String}

function TicketCreator(prop : TicketCreatorTypeProp) {
  return (
    /*
      htmlForm htmlFor 
    */
    <form ref={prop.ref} onSubmit={prop.handleForm} id="createForm">
      <div className="form-group">
        <h1>Submit Your Ticket</h1> {/* Heading placed on top */}
        <div className="form-group">
          <label htmlFor="examplehtmlFormControlInput1">Amount for Reimbursement</label><br />
          <input
            type="text"
            inputMode="numeric"
            className="htmlForm-control"
            id="examplehtmlFormControlInput1"
            placeholder="$"
            onChange={(e) => prop.setAmount(Number(e.target.value))}
            required/>
        </div>
        <div className="form-group">
          <label htmlFor="examplehtmlFormControlTextarea1">Description of Issue</label><br />
          <textarea
            onChange={(e) => prop.setDescription(e.target.value)}
            className="htmlForm-control"
            id="examplehtmlFormControlTextarea1"
            rows={5}
            placeholder="Discuss reasoning for ticket"
            required
          ></textarea><br />
          <button type="submit">Submit Ticket</button>
        </div>
        <p>{prop.errorMessage}</p>
      </div>
    </form>
  )
}

export default TicketCreator