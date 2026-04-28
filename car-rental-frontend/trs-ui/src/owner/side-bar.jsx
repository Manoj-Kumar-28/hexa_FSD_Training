import { Link } from "react-router-dom";

const Sidebar = () => {
    

    return (
        <div className="sidebar-glass">
            <ul className="nav flex-column">
                <li className="nav-item mb-4 text-center">
                    <Link className="nav-link text-white active" to="/owner-dashboard" style={{padding: '0.5rem'}}>
                        <h5 className="m-0">Owner Dashboard</h5>
                    </Link>
                </li>

                <li className="nav-item">
                    <Link className="nav-link" to="/owner-dashboard">
                        <i className="bi bi-house-door me-2"></i> Home
                    </Link>
                </li>

                <li className="nav-item">
                    <Link className="nav-link" to="/owner/my-cars">
                        <i className="bi bi-car-front me-2"></i> My Cars
                    </Link>
                </li>

                <li className="nav-item">
                    <Link className="nav-link" to="/owner/my-bookings">
                        <i className="bi bi-calendar-check me-2"></i> Bookings
                    </Link>
                </li>

                <li className="nav-item">
                    <Link className="nav-link" to="/owner/profile">
                        <i className="bi bi-person me-2"></i> Profile
                    </Link>
                </li>
            </ul>
        </div>
    );
};

export default Sidebar;