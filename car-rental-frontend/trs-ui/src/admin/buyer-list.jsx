import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux"
import { useNavigate, Outlet } from "react-router-dom";
import { getAllBuyers } from "../redux/action/getAllBuyersAction";
import TopMenu from "./top-menu";
import AdminSidebar from "./admin-sidebar";
import './css/admin-dashboard.css';

function BuyerList() {
    const [page, setPage] = useState(0);
    const size = 4;

    const dispatch = useDispatch();
    const navigate = useNavigate();
    //getting buyers from store which are saved in state
    const { buyers=[], totalPages, totalRecords } = useSelector(state => state.Buyer);

    useEffect(() => {
        dispatch(getAllBuyers(page, size));
    }, [dispatch, page]);


    return (
        <div className="admin-dashboard-container">
            <div className="container-fluid p-0">
                <TopMenu />

                <div className="d-flex">
                    <AdminSidebar />

                    <div className="main-content flex-grow-1 p-4">
                        <div className="buyer-list-header">
                            <h3 className="buyer-list-title">All Buyers</h3>
                            <span className="buyer-count-badge">
                                {totalRecords} buyers
                            </span>
                        </div>

                        <div className="glass-table-container">
                            {buyers.length === 0 ? (
                                <div className="no-buyers-message">
                                    <i className="bi bi-people"></i>
                                    <h4>No Buyers Found</h4>
                                    <p>No buyer accounts have been registered yet.</p>
                                </div>
                            ) : (
                                <>
                                    <table className="premium-table">
                                        <thead>
                                            <tr>
                                                <th scope="col">ID</th>
                                                <th scope="col">Name</th>
                                                <th scope="col">Email</th>
                                                <th scope="col">City</th>
                                                <th scope="col">Bookings</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {buyers.map((b) => (
                                                <tr key={b.id}>
                                                    <td className="buyer-id">{b.id}</td>
                                                    <td className="buyer-name">{b.name}</td>
                                                    <td className="buyer-email">{b.email}</td>
                                                    <td className="buyer-city">{b.city}</td>

                                                    <td>
                                                        <button 
                                                            className="btn btn-success" 
                                                            onClick={() => navigate(`/admin/buyers/${b.id}/bookings`)}
                                                        >
                                                            Bookings
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

                        {/* Outlet for displaying nested routes like BuyerBookings */}
                        <Outlet />
                    </div>
                </div>
            </div>
        </div>
    )
}
export default BuyerList