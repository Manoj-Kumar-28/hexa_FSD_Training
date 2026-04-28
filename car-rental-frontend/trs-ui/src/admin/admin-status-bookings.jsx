import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import './css/admin-dashboard.css';

function AdminStatusBookings() {
    const { status } = useParams();
    const [bookings, setBookings] = useState([]);
    const [errMsg, setErrMsg] = useState('');

    useEffect(() => {
        const fetchBookings = async () => {
            try {
                const config = {
                    headers: {
                        "Authorization": 'Bearer ' + localStorage.getItem('token')
                    }
                };
                const response = await axios.get(`http://localhost:8080/api/booking/get-all`, config);

                const allBookings = response.data.data;

                // Filter bookings by status 
                const filtered = allBookings.filter(b => b.bookingStatus && b.bookingStatus.toUpperCase() === status.toUpperCase());
                setBookings(filtered);
            } catch (err) {
                setErrMsg("Could not load bookings.");
            }
        };

        if (status) {
            fetchBookings();
        }
    }, [status]);

    return (
        <div className="mt-4">
            <div className="buyer-list-header">
                <h3 className="buyer-list-title">{status} Bookings</h3>
                <span className="buyer-count-badge">
                    {bookings.length} booking{bookings.length !== 1 ? 's' : ''}
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
                        <p>No bookings found with status '{status}'.</p>
                    </div>
                ) : (
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
                                    <td>1
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
                )}
            </div>
        </div>
    );
}

export default AdminStatusBookings;
