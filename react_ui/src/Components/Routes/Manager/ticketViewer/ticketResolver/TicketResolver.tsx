import React, { useEffect, useState } from 'react';
import { Ticket } from '../../../Employee/ticketCreator/SmartCreator';
import axios from 'axios';
import { useParams } from 'react-router-dom';

type TicketResolverProps = {
  changeStatus: (event: React.MouseEvent<HTMLButtonElement>) => void,
};

function TicketResolver(prop: TicketResolverProps) {
  const [ticket, setTicket] = useState<Ticket | undefined>(undefined);
  const {ticketId} = useParams();

  useEffect(() => {
        const response = axios.get(`http://localhost:8080/ticket/${ticketId}`);

        response.then( (response) => {
          console.log(response.data);
          console.log("Ticket data fetched", response.data);
          setTicket(response.data);
      }
      ).catch( (error)=>{
        console.error("Error fetching ticket:", error);
      })
    }, [])
    console.log(ticket);

  return (
    <div>
      <p><strong>Ticket ID:</strong> {ticket?.id}</p>
      <p><strong>Employee Username: </strong> {ticket?.employee?.username}</p>
      <p><strong>Issue Description: </strong> {ticket?.description}</p>
      <p><strong>Ticket Status: </strong> {ticket?.status}</p>
      <p><strong>Amount for Recompensation: </strong> ${ticket?.amount}</p>

      {/* Buttons to change the ticket status */}
      <button id="acceptButton" onClick={prop.changeStatus}>Accept Request</button>
      <button id="denyButton" onClick={prop.changeStatus}>Deny Request</button>
      <button id="leaveButton" onClick={prop.changeStatus}>Leave</button>
    </div>
  );
}

export default TicketResolver;
