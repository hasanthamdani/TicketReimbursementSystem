import React, { ReactNode, useEffect, useState } from 'react'
import { Ticket } from '../ticketCreator/SmartCreator';
import TicketHistory from './TicketHistory';
import { useAuth } from '../../../Context/EmployeeContext';
import axios from 'axios';


function SmartHistory() {
    const {employee} = useAuth();
    const [ticket, setTicket] = useState<Ticket | undefined>(undefined)
    const [employeeTickets, setEmployeeTickets] = useState<Array<Ticket> | null>(null);
    
    //IIFE

    useEffect(() => {
          const response = axios.get("http://localhost:8080/tickets/"+employee?.username);

          console.log("TicketList");
          console.log(response);

          response.then((response) => {setEmployeeTickets(
            response.data.map((ticket : Ticket) => ({id: ticket.id, employee: ticket.employee, description: ticket.description, status: ticket.status, amount: ticket.amount}))
        )
    }

        ).catch((error) => {console.error(error)})
        }, [])
    

    return (
        <>
        {/* <button className="btn btn-primary btn-lg btn-block" onClick={getList}>View Previous Tickets</button> */}
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
                
            ))
            }
            </tbody>
        </table>
        </>
    );
}

export default SmartHistory