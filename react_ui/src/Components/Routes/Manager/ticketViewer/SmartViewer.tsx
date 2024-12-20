import React, { useState } from 'react'
import axios from 'axios'
import TicketViewer from './TicketViewer';
import { Ticket } from '../../Employee/ticketCreator/SmartCreator';

function SmartViewer() {
    const [allPendingTickets, setAllPendingTickets] = useState<Array<Ticket> | null>(null);
    

    async function getList ()
    {
        try
        {
          const response = await axios.get("http://localhost:8080/manager/tickets");

          console.log("TicketList");
          console.log(response);
          setAllPendingTickets(response.data.map((object : any) => ({id: object.id, employee: object.employee, description: object.description, status: object.status, amount: object.amount})))

          console.log(allPendingTickets?.length)
        }
        catch(error)
        {
          console.error(error);
        }
    };

  return (
    <>
        <button onClick={getList}>View All Pending Tickets</button>
        <table className="table table-striped-columns">
            <thead>
                <tr>
                    <th>Username</th>
                    <th>Ticket ID</th>
                    <th>Amount</th>
                    <th>Description</th>
                    <th>Status</th>
                    <th>Details</th>
                    
                </tr>
            </thead>
            <tbody>
            {   
            allPendingTickets?.map((Ticket, index) => (
                <tr key = {Ticket.id}>
                    <TicketViewer id = {Ticket.id} description = {Ticket.description} status = {Ticket.status} amount = {Ticket.amount} username = {Ticket.employee?.username}/>
                </tr>
                
            ))}
            </tbody>
        </table>
        </>
  )
}
export default SmartViewer;
