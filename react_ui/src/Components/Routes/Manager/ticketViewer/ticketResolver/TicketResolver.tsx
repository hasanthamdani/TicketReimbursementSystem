import React, { useState } from 'react'
import { useParams } from 'react-router-dom'
import { Ticket } from '../../../Employee/ticketCreator/SmartCreator'
import axios from 'axios';

type TicketResolverProp = {
  status: string | undefined,
  changeStatus: (event : any) => void
  
} 
function TicketResolver(prop: TicketResolverProp) {
  const [ticket, setTicket] = useState<Ticket | undefined>(undefined)
  const { id } = useParams();

  async function getTicket ()
    {
        try
        {
          console.log(id);
          const response = await axios.get("http://localhost:8080/ticket/"+id);

          console.log("Ticket");
          console.log(response.data);
          setTicket(response.data);
        }
        catch(error)
        {
          console.error(error);
        }
    };
  

  return (
    <div>
      <button onClick={getTicket}>Render</button>
      <p><strong>Ticket ID:</strong> {id}</p> <br/>
      <p><strong>Employee Username: </strong> {ticket?.employee?.username}</p>
      <p><strong>Issue Description:  </strong> {ticket?.description}</p>
      <p><strong>Ticket Status: </strong> {prop.status}</p>
      <p><strong>Amount for Recompensation </strong> ${ticket?.amount}</p>
      <button id = "acceptButton" onClick = {prop.changeStatus}>Accept Request</button>
      <button id = "denyButton" onClick = {prop.changeStatus}>Deny Request </button>
      <button id = "leaveButton" onClick = {prop.changeStatus}>Leave </button>
    </div>
  )
}

export default TicketResolver