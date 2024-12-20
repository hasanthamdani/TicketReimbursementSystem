import React, { ReactNode, useEffect, useState } from 'react'
import { Ticket } from '../ticketCreator/SmartCreator';
import TicketHistory from './TicketHistory';
import { useAuth } from '../../../Context/EmployeeContext';
import axis from 'axios';
import axios from 'axios';


function SmartHistory() {
    const {employee} = useAuth();
    const [ticket, setTicket] = useState<Ticket | undefined>(undefined)
    const [employeeTickets, setEmployeeTicket] = useState<Array<Ticket> | null>(null);
    
    //IIFE
    async function getList ()
    {
        try
        {
          const response = await axios.get("http://localhost:8080/tickets/"+employee?.username);

          console.log("TicketList");
          console.log(response);
          setEmployeeTicket(response.data.map((object : any) => ({id: object.id, employee: object.employee, description: object.description, status: object.status, amount: object.amount})))

          console.log(employeeTickets?.length)
        }
        catch(error)
        {
          console.error(error);
        }
    };
    

    return (
        <>
        <button className="btn btn-primary btn-lg btn-block" onClick={getList}>View Previous Tickets</button>
        <table className="table table-hover">
            <thead>
                <tr>
                    <th>Ticket ID</th>
                    <th>Description</th>
                    <th>Status</th>
                    <th>Amount</th>
                </tr>
            </thead>
            <tbody>
            {   
            employeeTickets?.map((Ticket, index) => (
                <tr key={Ticket.id}>
                    <TicketHistory  id = {Ticket.id} description = {Ticket.description} status = {Ticket.status} amount = {Ticket.amount} />
                </tr>
                
            ))}
            </tbody>
        </table>
        </>
      );
}

export default SmartHistory