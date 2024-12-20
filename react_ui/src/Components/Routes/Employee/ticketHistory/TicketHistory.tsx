import React from 'react'
import { Employee } from '../../../Context/EmployeeContext';

type ticketHistoryProp = {
  id: number | undefined,
  description: string,
  status: "pending" | "approved" | "denied";
  amount: number,
}

function TicketHistory(prop : ticketHistoryProp) {
  return (
      <>
        <td>{prop.id}</td>
        <td>{prop.description}</td>
        <td>{prop.status}</td>
        <td>{prop.amount}</td>
      </>
  )
}

export default TicketHistory