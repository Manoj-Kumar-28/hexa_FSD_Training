import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import '../buyer/css/my-bookings.css';

function AdminBuyerBookings() {
    const { buyerId } = useParams();
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
                const response = await axios.get(`http://localhost:8080/api/booking/get/buyer/${buyerId}/v1`, config);
                // ensure we are getting array
                setBookings(response.data);
            } catch (err) {
                setErrMsg("Could not load bookings.");
            }
        };

        if (buyerId) {
            fetchBookings();
        }
    }, [buyerId]);

    return (
        <div className="my-bookings-wrapper mt-4">
            <div className="my-bookings-header">
                <h3>Bookings for Buyer ID: {buyerId}</h3>
            </div>

            {errMsg && (
                <div className="bookings-error">
                    <i className="bi bi-exclamation-circle-fill"></i> {errMsg}
                </div>
            )}

            {bookings.length === 0 && !errMsg ? (
                <div className="no-bookings">
                    <i className="bi bi-calendar-x"></i>
                    <h4>No Bookings Found</h4>
                    <p>This buyer hasn't made any bookings.</p>
                </div>
            ) : (
                <div className="bookings-list">
                    {bookings.map((booking, index) => (
                        <div className="booking-card-item" key={index}>
                            <div className="booking-card-left">
                                <div className="booking-car-info">
                                    <h4>{booking.carBrand} <span>{booking.carModel}</span></h4>
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
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}

export default AdminBuyerBookings;
