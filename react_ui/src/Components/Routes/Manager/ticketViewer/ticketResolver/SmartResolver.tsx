import React, { useState, useEffect } from 'react';
import { useParams, useNavigate, Navigate } from 'react-router-dom';
import TicketResolver from './TicketResolver';
import axios from 'axios';
import { Ticket } from '../../../Employee/ticketCreator/SmartCreator';

function SmartResolver() {
  const { ticketId } = useParams();
  const [redirect, setRedirect] = useState(false);
  const navigate = useNavigate();

  // Need to redo all of the work for the resolver

  async function changeStatus(e: React.MouseEvent<HTMLButtonElement>) {
    let newStatus: "accept" | "deny" | "pending" = "pending";
    if(e.target === document.getElementById("acceptButton"))
    {
        newStatus = "accept";
        console.log(newStatus);

        try {
          const response = await axios.patch(
            `http://localhost:8080/manager/tickets?ticketId=${ticketId}&status=${newStatus}`,
            {},
            { headers: { "Content-Type": "application/json" } }
          );
          console.log(response.data);
    
    
        } catch (error: any) {
          console.error("Error changing status:", error);
        }
        setRedirect(true);
    }
    else if(e.target === document.getElementById("denyButton"))
    {
        newStatus = "deny"
        console.log(newStatus);

        try {
          const response = await axios.patch(
            `http://localhost:8080/manager/tickets?ticketId=${ticketId}&status=${newStatus}`,
            {},
            { headers: { "Content-Type": "application/json" } }
          );
          console.log(response.data);
    
    
        } catch (error: any) {
          console.error("Error changing status:", error);
        }
        setRedirect(true);
    }
    else if(e.target === document.getElementById("leaveButton"))
    {
      setRedirect(true);
    }

  }

  if(redirect)
  {
    navigate('/view');
  }

  return (
    <div>
       (
        <TicketResolver
          changeStatus={changeStatus}
        />
      )
    </div>
  );
}

export default SmartResolver;
