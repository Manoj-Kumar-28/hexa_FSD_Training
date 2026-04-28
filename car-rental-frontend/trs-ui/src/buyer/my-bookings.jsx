import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import BuyerNavbar from './buyerNavbar';
import BuyerFooter from './buyer-footer';
import './css/buyer-dashboard.css';
import './css/my-bookings.css';

function MyBookings() {
    const navigate = useNavigate();
    const [bookings, setBookings] = useState([]);
    const [errMsg, setErrMsg] = useState('');

    const getBookingsApi = `http://localhost:8080/api/booking/get/buyer/v2`;

    useEffect(() => {
        const fetchBookings = async () => {
            try {
                const config = {
                    headers: {
                        "Authorization": 'Bearer ' + localStorage.getItem('token')
                    }
                };
                const response = await axios.get(getBookingsApi, config);
                setBookings(response.data.data || response.data);
            } catch (err) {
                setErrMsg("Could not load your bookings.");
            }
        };

        fetchBookings();
    }, []);

    const handleCancel = async (bookingId) => {
        if (!window.confirm("Are you sure you want to cancel this booking?")) return;

        const cancelApi = `http://localhost:8080/api/booking/cancel/${bookingId}`
        try {
            const config = {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("token")
                }
            };
            await axios.put(cancelApi, {}, config);
            setBookings(prev =>
                prev.map(booking =>
                    booking.bookingId === bookingId
                        ? { ...booking, bookingStatus: 'CANCELLED' }
                        : booking
                )
            );
        } catch (err) {
            setErrMsg("Error while cancelling the booking")
        }
    }

    return (
        <div className="dashboard-container">
            <BuyerNavbar />

            <div className="my-bookings-wrapper">
                <div className="my-bookings-header">
                    <h2>My Bookings</h2>
                    <p>Track and manage all your car rentals</p>
                </div>

                {errMsg && (
                    <div className="bookings-error">
                        <i className="bi bi-exclamation-circle-fill"></i> {errMsg}
                    </div>
                )}

                {bookings.length === 0 && !errMsg ? (
                    <div className="no-bookings">
                        <i className="bi bi-calendar-x"></i>
                        <h3>No Bookings Yet</h3>
                        <p>You haven't made any bookings. Start exploring our fleet!</p>
                        <button className="browse-cars-btn" onClick={() => navigate('/browse')}>
                            Browse Cars
                        </button>
                    </div>
                ) : (
                    <div className="bookings-list">
                        {bookings.map((booking, index) => (
                            <div className="booking-card-item" key={index}>
                                <div className="booking-card-left">
                                    <div className="booking-car-info">
                                        <h3>{booking.carBrand} <span>{booking.carModel}</span></h3>
                                        <p className="booking-car-number">
                                            <i className="bi bi-hash"></i> {booking.carNumber}
                                        </p>
                                    </div>
                                    <div className="booking-dates">
                                        <div className="date-block">
                                            <span className="date-label">Pickup</span>
                                            <span className="date-value">
                                                <i className="bi bi-calendar-event"></i> {booking.pickupDateTime}
                                            </span>
                                            <span className="location-value">
                                                <i className="bi bi-geo-alt"></i> {booking.pickupLocation}
                                            </span>
                                        </div>
                                        <div className="date-arrow">
                                            <i className="bi bi-arrow-right"></i>
                                        </div>
                                        <div className="date-block">
                                            <span className="date-label">Drop-off</span>
                                            <span className="date-value">
                                                <i className="bi bi-calendar-check"></i> {booking.dropDateTime}
                                            </span>
                                            <span className="location-value">
                                                <i className="bi bi-geo"></i> {booking.dropLocation}
                                            </span>
                                        </div>
                                    </div>
                                </div>
                                <div className="booking-card-right">
                                    <div className={`booking-status ${booking.bookingStatus?.toLowerCase()}`}>
                                        {booking.bookingStatus}
                                    </div>
                                    <div className="booking-amount">
                                        <i className="bi bi-currency-rupee"></i>{booking.totalAmount}
                                    </div>
                                    {booking.bookingStatus !== 'CANCELLED' && (
                                        <button className="btn btn-danger" onClick={() => handleCancel(booking.bookingId)}>Cancel</button>
                                    )}
                                </div>
                            </div>
                        ))}
                    </div>
                )}
            </div>

            <BuyerFooter />
        </div>
    );
}

export default MyBookings;
