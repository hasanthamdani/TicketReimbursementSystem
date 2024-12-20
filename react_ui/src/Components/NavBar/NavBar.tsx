import { useAuth} from '../Context/EmployeeContext'
import { Link, Navigate} from 'react-router-dom';


function NavBar() {
  const {isAuth, isManager, employee} = useAuth();
  
  return (
    <>
      <nav className="navbar navbar-expand-lg bg-body-tertiary">
        <div className="container-fluid">
          <Link className="navbar-brand" to="">
            {isAuth ? (isManager ? "Manager Dashboard" : "Employee Dashboard") : "Ticket Reimbursement System"}
          </Link>
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarNav"
            aria-controls="navbarNav"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav">
              <li className="nav-item">
                <Link className="nav-link" to="">
                  Home
                </Link>
              </li>
              {
                isAuth===false &&
                <li className="nav-item">
                <Link className="nav-link" to="/login">
                  Login
                </Link>
              </li>
              }

              {isAuth===true && isManager === false &&
                <>
                  <li className="nav-item">
                  <Link className="nav-link" to="/create">
                    Create
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to="/history">
                    History
                  </Link>
                </li>
                </>
              }
                {
                  isAuth===true && isManager &&
                    <>
                    <li className="nav-item">
                      <Link className="nav-link" to="/view">
                        View and Resolve
                      </Link>
                    </li>
                    </>
                  }
                <li className="nav-item">
                <Link className="nav-link" to="/login">
                  Logout
                </Link>
              </li>
            </ul>
          </div>
        </div>
      </nav>
    </>
  );
}

export default NavBar