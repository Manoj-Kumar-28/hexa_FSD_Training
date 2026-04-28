import { useEffect, useState } from "react";
import axios from "axios";
import TopMenu from "./top-menu";
import AdminSidebar from "./admin-sidebar";
import './css/admin-dashboard.css';

function AdminBookings() {
    const [bookings, setBookings] = useState([]);
    const [errMsg, setErrMsg] = useState('');
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const pageSize = 5;

    useEffect(() => {
        const fetchBookings = async () => {
            try {
                const config = {
                    headers: {
                        "Authorization": 'Bearer ' + localStorage.getItem('token')
                    }
                };
                const response = await axios.get(`http://localhost:8080/api/booking/get-all?page=${currentPage}&size=${pageSize}`, config);
                setBookings(response.data.data);
                setTotalPages(response.data.totalPages);
            } catch (err) {
                setErrMsg("Could not load bookings.");
            }
        };

        fetchBookings();
    }, [currentPage]);

    return (
        <div className="admin-dashboard-container">
            <div className="container-fluid p-0">
                <TopMenu />

                <div className="d-flex">
                    <AdminSidebar />

                    <div className="main-content flex-grow-1 p-4">
                        <div className="buyer-list-header">
                            <h3 className="buyer-list-title">All Bookings</h3>
                            <span className="buyer-count-badge">
                                {bookings.length} booking{bookings.length !== 1 ? 's' : ''} on this page
                            </span>
                        </div>

                        {errMsg && (
                            <div className="alert alert-danger" role="alert">
                                <i className="bi bi-exclamation-circle-fill me-2"></i> {errMsg}
                            </div>
                        )}

                        <div className="glass-table-container">
                            {bookings.length === 0 && !errMsg ? (
                                <div className="no-buyers-message">
                                    <i className="bi bi-calendar-x"></i>
                                    <h4>No Bookings Found</h4>
                                    <p>No bookings have been made yet.</p>
                                </div>
                            ) : (
                                <>
                                    <table className="premium-table">
                                        <thead>
                                            <tr>
                                                <th scope="col">ID</th>
                                                <th scope="col">Car Number</th>
                                                <th scope="col">Pickup</th>
                                                <th scope="col">Drop-off</th>
                                                <th scope="col">Days</th>
                                                <th scope="col">Amount</th>
                                                <th scope="col">Status</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {bookings.map((b, index) => (
                                                <tr key={b.bookingId || index}>
                                                    <td className="buyer-id">{b.bookingId}</td>
                                                    <td>{b.carNumber}</td>
                                                    <td>
                                                        <div style={{ fontSize: '0.8rem', color: '#cbd5e1' }}>{b.pickupDateTime}</div>
                                                        <div style={{ fontSize: '0.75rem', color: '#94a3b8' }}>{b.pickupLocation}</div>
                                                    </td>
                                                    <td>
                                                        <div style={{ fontSize: '0.8rem', color: '#cbd5e1' }}>{b.dropDateTime}</div>
                                                        <div style={{ fontSize: '0.75rem', color: '#94a3b8' }}>{b.dropLocation}</div>
                                                    </td>
                                                    <td>
                                                        <span className="badge bg-secondary">
                                                            {b.pickupDateTime && b.dropDateTime ?
                                                                Math.max(1, Math.ceil(Math.abs(new Date(b.dropDateTime) - new Date(b.pickupDateTime)) / (1000 * 60 * 60 * 24))) + ' Days'
                                                                : '-'}
                                                        </span>
                                                    </td>
                                                    <td style={{ color: '#14b8a6', fontWeight: 'bold' }}>₹{b.totalAmount}</td>
                                                    <td>
                                                        <span className={`badge ${b.bookingStatus === 'COMPLETED' ? 'bg-success' :
                                                            b.bookingStatus === 'CANCELLED' ? 'bg-danger' :
                                                                'bg-primary'
                                                            }`}>
                                                            {b.bookingStatus}
                                                        </span>
                                                    </td>
                                                </tr>
                                            ))}
                                        </tbody>
                                    </table>

                                    {/* Pagination Controls */}
                                    {totalPages > 1 && (
                                        <div className="pagination-container">
                                            <button
                                                className="pagination-btn"
                                                disabled={currentPage === 0}
                                                onClick={() => setCurrentPage(p => p - 1)}
                                            >
                                                <i className="bi bi-chevron-left"></i> Prev
                                            </button>

                                            <div className="pagination-numbers">
                                                {Array.from({ length: totalPages }, (_, i) => (
                                                    <button
                                                        key={i}
                                                        className={`page-num-btn ${currentPage === i ? 'active' : ''}`}
                                                        onClick={() => setCurrentPage(i)}
                                                    >
                                                        {i + 1}
                                                    </button>
                                                ))}
                                            </div>

                                            <button
                                                className="pagination-btn"
                                                disabled={currentPage >= totalPages - 1}
                                                onClick={() => setCurrentPage(p => p + 1)}
                                            >
                                                Next <i className="bi bi-chevron-right"></i>
                                            </button>
                                        </div>
                                    )}
                                </>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default AdminBookings;
