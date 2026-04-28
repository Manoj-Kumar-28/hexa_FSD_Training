import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux"
import { useNavigate, Outlet } from "react-router-dom";
import { getAllOwners } from "../redux/action/getAllOwnersAction";
import TopMenu from "./top-menu";
import AdminSidebar from "./admin-sidebar";
import './css/admin-dashboard.css';

function OwnerList() {
    const [page, setPage] = useState(0);
    const size = 4;

    const dispatch = useDispatch();
    const navigate = useNavigate();
    //getting owners from store which are saved in state
    const { owners = [], totalPages, totalRecords } = useSelector(state => state.Owner);

    useEffect(() => {
        //getAllOwners is an action and we are calling it
        dispatch(getAllOwners(page, size))
    }, [dispatch, page])


    return (
        <div className="admin-dashboard-container">
            <div className="container-fluid p-0">
                <TopMenu />

                <div className="d-flex">
                    <AdminSidebar />

                    <div className="main-content flex-grow-1 p-4">
                        <div className="buyer-list-header">
                            <h3 className="buyer-list-title">All Owners</h3>
                            <span className="buyer-count-badge">
                                {totalRecords} owners
                            </span>
                        </div>

                        <div className="glass-table-container">
                            {owners.length === 0 ? (
                                <div className="no-buyers-message">
                                    <i className="bi bi-people"></i>
                                    <h4>No Owners Found</h4>
                                    <p>No owner accounts have been registered yet.</p>
                                </div>
                            ) : (
                                <>
                                    <table className="premium-table">
                                        <thead>
                                            <tr>
                                                <th scope="col">ID</th>
                                                <th scope="col">Name</th>
                                                <th scope="col">Email</th>
                                                <th scope="col">Phone</th>
                                                <th scope="col">City</th>
                                                <th scope="col">Status</th>
                                                <th scope="col">Cars</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {owners.map((o) => (
                                                <tr key={o.id}>
                                                    <td className="buyer-id">{o.id}</td>
                                                    <td className="buyer-name">{o.name}</td>
                                                    <td className="buyer-email">{o.email}</td>
                                                    <td className="buyer-city">{o.phoneNumber || o.phone}</td>
                                                    <td className="buyer-city">{o.city}</td>
                                                    <td>
                                                        <span className={`badge ${o.status === 'APPROVED' ? 'bg-success' : o.status === 'PENDING' ? 'bg-warning text-dark' : 'bg-danger'}`}>
                                                            {o.status}
                                                        </span>
                                                    </td>
                                                    <td>
                                                        <button 
                                                            className="btn btn-success"
                                                            onClick={() => navigate(`/admin/owners/${o.id}/cars`)}
                                                        >
                                                            Cars
                                                        </button>
                                                    </td>
                                                </tr>
                                            ))}
                                        </tbody>
                                    </table>

                                    <div className="d-flex justify-content-center mt-3">

                                        <button
                                            className="btn btn-outline-primary me-2"
                                            disabled={page === 0}
                                            onClick={() => setPage(page - 1)}
                                        >
                                            Prev
                                        </button>

                                        <span className="align-self-center">
                                            Page {page + 1} of {totalPages}
                                        </span>

                                        <button
                                            className="btn btn-outline-primary ms-2"
                                            disabled={page === totalPages - 1}
                                            onClick={() => setPage(page + 1)}
                                        >
                                            Next
                                        </button>

                                    </div>
                                </>
                            )}
                        </div>
                        
                        {/* Outlet for displaying nested routes like OwnerCars */}
                        <Outlet />
                    </div>
                </div>
            </div>
        </div>
    )
}
export default OwnerList