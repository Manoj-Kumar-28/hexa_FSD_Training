import { Link } from "react-router-dom";

const AdminSidebar = () => {
  return (
    <div className="sidebar">

      <h4 className="text-center">⚙️ Admin</h4>

      <Link to="/admin-dashboard">
        <i className="bi bi-speedometer2"></i> Dashboard
      </Link>

      <div className="menu-item">
        <div className="menu-header">
          <i className="bi bi-people"></i> Users
        </div>
        <div className="sub-menu">
          <Link to="/admin/buyers">
            <i className="bi bi-person"></i> Buyers
          </Link>
          <Link to="/admin/owners">
            <i className="bi bi-person-badge"></i> Owners
          </Link>
        </div>
      </div>

      <Link to="/admin/cars">
        <i className="bi bi-car-front"></i> Cars
      </Link>

      <Link to="/admin-bookings">
        <i className="bi bi-calendar-check"></i> Bookings
      </Link>

      <Link to="/admin-approvals">
        <i className="bi bi-check-circle"></i> Approvals
      </Link>

    </div>
  );
};

export default AdminSidebar;