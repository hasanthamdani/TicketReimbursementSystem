import React, { MouseEventHandler, useEffect } from 'react'
import { Link, Navigate } from 'react-router-dom';
import axios from 'axios'

type ticketViewerProp = {
  id: number | undefined,
  description: string,
  status: "pending" | "approved" | "denied";
  amount: number,
  username: string | undefined,
}


function TicketViewer(prop : ticketViewerProp) {
  return (
      <>
        <td>{prop.username}</td>
        <td>{prop.id}</td>
        <td>{prop.amount}</td>
        <td>{prop.description}</td>
        <td>{prop.status}</td>
        <td><Link to={`/view/${prop.id}`}>Resolve</Link></td>
      </>
  );
}

export default TicketViewer

