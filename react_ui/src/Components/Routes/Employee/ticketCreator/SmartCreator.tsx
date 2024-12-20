import React, { useRef, useState } from 'react'
import TicketCreator from './TicketCreator'
import { Employee, useAuth } from '../../../Context/EmployeeContext';
import axios from 'axios';

export interface Ticket {
    id? : number;
    employee: Employee | null;
    status: "pending" | "approved" | "denied";
    amount: number;
    description: string;
}


function SmartCreator() {
    const [amount, setAmount] = useState(0);
    const [description, setDescription] = useState("");
    const [sent, setSent] = useState(false);
    const {employee} =  useAuth();
    const [ticket, setTicket] = useState<Ticket | undefined>(undefined);
    const [errorMessage, setErrorMessage] = useState("");
    const form = useRef<HTMLFormElement>(null); //useRef 

    async function handleForm(event : React.FormEvent)
    {
            event.preventDefault();
            // Create Ticket and Return ID
            if(!amount || !description || amount <= 0)
            {
                setErrorMessage("Form is incomplete, all info is needed");
            }
            else{
                console.log(amount);
                let ticket : Ticket = {employee: employee, status: "pending", amount: amount, description: description}
            try
            {
              const response = await axios.post("http://localhost:8080/create", ticket, {
                headers:{"Content-Type": "application/json"}
                })

              console.log(response.data);
              console.log(amount);
              setErrorMessage("Ticket Submission");

              setSent(true);
              
              setTicket({id: response.data.id, employee: response.data.employee, status: response.data.status, amount: response.data.amount, description: response.data.description});  
              form.current?.reset();
                
            }
            catch(error)
            {
              setErrorMessage("Error: Ticket is invalid");
            }
          }
            
            // Produce ID and Show the Ticket has been Created.

    }
    
    return (
        <>
        <div id = "createContanier">
            <TicketCreator ref = {form} amount = {amount} setAmount = {setAmount} description = {description} setDescription = {setDescription} handleForm = {handleForm} errorMessage = {errorMessage}/>
            {sent && <p>Your ticket ID # is {ticket?.id}</p>}
        </div>
          
        </>
      );
}

export default SmartCreator