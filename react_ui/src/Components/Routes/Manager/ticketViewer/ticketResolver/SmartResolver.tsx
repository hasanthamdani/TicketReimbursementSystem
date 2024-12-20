import React, { useState } from 'react';
import TicketResolver from './TicketResolver';
import axios from 'axios';
import { Navigate, useNavigate, useParams } from 'react-router-dom';
import { Ticket } from '../../../Employee/ticketCreator/SmartCreator';

function SmartResolver() {
  const [ticket, setTicket] = useState<Ticket | undefined>(undefined);
  const { id } = useParams();
  const [redirect, setRedirect] = useState(false);  // To handle redirection
  const navigate = useNavigate();  // useNavigate for programmatic navigation

  // Function to change ticket status
  async function changeStatus(e: any) {
    let newStatus: "accept" | "deny" | "pending" = "pending";  // Default to "pending"

    // Determine status based on button clicked
    if ("acceptButton" === e.target.id) {
      newStatus = "accept";
    } else if ("denyButton" === e.target.id) {
      newStatus = "deny";
    } else if ("leaveButton" === e.target.id) {
      newStatus = "pending";
    }

    try {
      // Send PATCH request to update the status on the backend
      const response = await axios.patch(
        `http://localhost:8080/manager/tickets?ticketId=${id}&status=${newStatus}`,
        {},  // Empty body, we only need the query parameters
        { headers: { "Content-Type": "application/json" } }
      );

      console.log("Updated ticket:", response.data);  // Log the response for debugging

      // Check if the backend returned an updated ticket
      if (response.data) {
        setTicket(response.data);  // Update the state with the updated ticket
      }

      // If the status is set to 'pending' or 'leave' button was clicked, redirect to /view
      if (newStatus === "pending" || e.target.id === "leaveButton") {
        setRedirect(true);  // Trigger redirection when "leaveButton" is clicked or status is pending
      } else {
        // If the status was updated successfully, navigate back to the tickets view
        navigate("/view");
      }
    } catch (error: any) {
      if (error.response) {
        console.error("Response error:", error.response.data);  // Server responded with an error
      } else if (error.request) {
        console.error("Request error:", error.request);  // No response received
      } else {
        console.error("Error:", error.message);  // Something went wrong setting up the request
      }
    }
  }

  // If redirect is triggered, render the Navigate component
  if (redirect) {
    return <Navigate to="/view" />;
  }

  return (
    <div>
      <TicketResolver
        status={ticket?.status || "pending"}  // If ticket exists, use its status
        changeStatus={changeStatus}
      />
    </div>
  );
}

export default SmartResolver;