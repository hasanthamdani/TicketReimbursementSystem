import React, { useEffect, useState } from 'react'
import axios from 'axios'
import TicketViewer from './TicketViewer';
import { Ticket } from '../../Employee/ticketCreator/SmartCreator';

function SmartViewer() {
    const [allPendingTickets, setAllPendingTickets] = useState<Array<Ticket> | null>(null);
    
    useEffect(() => {
            try
            {
              const response = axios.get("http://localhost:8080/manager/tickets");
              console.log("TicketList");
              console.log(response);
              response.then((response) => {
                setAllPendingTickets(response.data.map((object : any) => ({id: object.id, employee: object.employee, description: object.description, status: object.status, amount: object.amount})))
              }
              )
              console.log(allPendingTickets?.length)
            }
            catch(error)
            {
              console.error(error);
            }
      }, []);
    

  return (
    <>
        {/* <button className="btn btn-primary btn-lg btn-block" onClick={getList}>View All Pending Tickets</button> */}
        <table className="table table-hover">
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
