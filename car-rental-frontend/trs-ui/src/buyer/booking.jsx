import { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import BuyerNavbar from './buyerNavbar';
import BuyerFooter from './buyer-footer';
import './css/buyer-dashboard.css';
import './css/booking.css';
import axios from 'axios';

function Booking() {
    const location = useLocation();
    const navigate = useNavigate();
    const car = location.state?.car || {};

    const [startDate, setStartDate] = useState(localStorage.getItem("rental_pickupDate") || '');
    const [endDate, setEndDate] = useState(localStorage.getItem("rental_dropDate") || '');
    const [pickupLocation, setPickupLocation] = useState(localStorage.getItem("rental_pickupLocation") || '');
    const [dropLocation, setDropLocation] = useState(localStorage.getItem("rental_dropLocation") || '');
    const [paymentMethod, setPaymentMethod] = useState('CASH');
    const [successMsg, setSuccessMsg] = useState('');
    const [errorMsg, setErrorMsg] = useState('');

    // Calculate number of days and total price
    const calculateDays = () => {
        if (!startDate || !endDate) return 0;
        const start = new Date(startDate);
        const end = new Date(endDate);

        const diff = Math.ceil((end - start) / (1000 * 60 * 60 * 24)) + 1;
        return diff > 0 ? diff : 0;
    };

    const totalDays = calculateDays();
    const subtotal = totalDays * (car.pricePerDay || 0);
    const gstAmount = subtotal * 0.035;
    const totalPrice = subtotal + gstAmount;

    // Today's date for min attribute
    const today = new Date().toISOString().split('T')[0];

    const handleBooking = async () => {
        if (!startDate || !endDate) {
            setErrorMsg('Please select both start and end dates.');
            return;
        }
        if (totalDays <= 0) {
            setErrorMsg('End date must be after start date.');
            return;
        }
        if (!pickupLocation || !dropLocation) {
            setErrorMsg('Please enter both pickup and drop-off locations.');
            return;
        }
        setErrorMsg('');

        const api = `http://localhost:8080/api/booking/add/${car.id}`
        const config = {
            headers: {
                "Authorization": 'Bearer ' + localStorage.getItem('token')
            }
        }
        const bookingData = {
            "pickupDateTime": startDate,
            "dropDateTime": endDate,
            "pickupLocation": pickupLocation,
            "dropLocation": dropLocation,
            "paymentMethod": paymentMethod
        }

        try {
            await axios.post(api, bookingData, config)
            setSuccessMsg("Booking confirmed successfully! Redirecting...")
            setTimeout(() => {
                navigate("/myBookings")
            }, 2000)
        } catch (err) {
            setErrorMsg("Booking failed. Please try again.");
        }
    };

    return (
        <div className="dashboard-container">
            <BuyerNavbar />

            <div className="booking-page-wrapper">
                <button className="back-button" onClick={() => navigate(-1)}>
                    &larr; Back to Car Details
                </button>

                <div className="booking-page-header">
                    <h2>Complete Your Booking</h2>
                    <p>Review the details and select your rental dates</p>
                </div>

                <div className="booking-layout">
                    {/* Car Summary */}
                    <div className="booking-car-summary">
                        <div className="summary-image-box">
                            <img
                                src={
                                    car.carImage
                                        ? `/uploads/${car.carImage}`
                                        : "/car_card_placeholder.png"
                                }
                                alt={`${car.brand} ${car.model}`}
                                className="summary-car-image"
                            />
                        </div>
                        <div className="summary-details">
                            <h3 className="summary-car-name">{car.brand} <span>{car.model}</span></h3>
                            <div className="summary-specs">
                                <div className="summary-spec">
                                    <i className="bi bi-geo-alt-fill"></i>
                                    <span>{car.location}</span>
                                </div>
                                <div className="summary-spec">
                                    <i className="bi bi-fuel-pump-fill"></i>
                                    <span>{car.fuelType}</span>
                                </div>
                                <div className="summary-spec">
                                    <i className="bi bi-people-fill"></i>
                                    <span>{car.seats} Seats</span>
                                </div>
                                <div className="summary-spec">
                                    <i className="bi bi-palette-fill"></i>
                                    <span>{car.color}</span>
                                </div>
                            </div>
                            <div className="summary-price">
                                <i className="bi bi-currency-rupee"></i>{car.pricePerDay}
                                <span className="per-day">/ day</span>
                            </div>
                        </div>
                    </div>

                    {/* Booking Form */}
                    <div className="booking-form-card">
                        <h3 className="form-card-title">Booking Details</h3>

                        {errorMsg && (
                            <div className="booking-alert error">
                                <i className="bi bi-exclamation-circle-fill"></i> {errorMsg}
                            </div>
                        )}
                        {successMsg && (
                            <div className="booking-alert success">
                                <i className="bi bi-check-circle-fill"></i> {successMsg}
                            </div>
                        )}

                        <div className="location-fields">
                            <div className="location-field">
                                <label><i className="bi bi-geo-alt"></i> Pickup Location</label>
                                <input
                                    type="text"
                                    className="booking-text-input"
                                    placeholder="Enter pickup location"
                                    value={pickupLocation}
                                    onChange={(e) => setPickupLocation(e.target.value)}
                                />
                            </div>
                            <div className="location-field">
                                <label><i className="bi bi-geo"></i> Drop-off Location</label>
                                <input
                                    type="text"
                                    className="booking-text-input"
                                    placeholder="Enter drop-off location"
                                    value={dropLocation}
                                    onChange={(e) => setDropLocation(e.target.value)}
                                />
                            </div>
                        </div>

                        <div className="date-fields">
                            <div className="date-field">
                                <label><i className="bi bi-calendar-event"></i> Start Date</label>
                                <input
                                    type="date"
                                    className="booking-date-input"
                                    value={startDate}
                                    min={today}
                                    onChange={(e) => setStartDate(e.target.value)}
                                />
                            </div>
                            <div className="date-field">
                                <label><i className="bi bi-calendar-check"></i> End Date</label>
                                <input
                                    type="date"
                                    className="booking-date-input"
                                    value={endDate}
                                    min={startDate || today}
                                    onChange={(e) => setEndDate(e.target.value)}
                                />
                            </div>
                        </div>

                        <div className="payment-method-field">
                            <label><i className="bi bi-credit-card"></i> Payment Method</label>
                            <select
                                className="booking-text-input"
                                value={paymentMethod}
                                onChange={(e) => setPaymentMethod(e.target.value)}
                            >
                                <option value="CASH">CASH</option>
                                <option value="CARD">CARD</option>
                                <option value="UPI">UPI</option>
                            </select>
                        </div>

                        {/* Price Breakdown */}
                        <div className="price-breakdown">
                            <h4>Price Breakdown</h4>
                            <div className="price-row">
                                <span>Daily Rate</span>
                                <span><i className="bi bi-currency-rupee"></i>{car.pricePerDay || 0}</span>
                            </div>
                            <div className="price-row">
                                <span>Number of Days</span>
                                <span>{totalDays}</span>
                            </div>
                            <div className="price-row">
                                <span>Subtotal</span>
                                <span><i className="bi bi-currency-rupee"></i>{subtotal.toFixed(2)}</span>
                            </div>
                            <div className="price-row">
                                <span>GST (3.5%)</span>
                                <span><i className="bi bi-currency-rupee"></i>{gstAmount.toFixed(2)}</span>
                            </div>
                            <div className="price-row total">
                                <span>Total Amount</span>
                                <span><i className="bi bi-currency-rupee"></i>{totalPrice.toFixed(2)}</span>
                            </div>
                        </div>

                        <button
                            className="confirm-booking-btn"
                            onClick={handleBooking}
                            disabled={!!successMsg}
                        >
                            {successMsg ? 'Booking Confirmed ✓' : 'Confirm Booking'}
                        </button>
                    </div>
                </div>
            </div>

            <BuyerFooter />
        </div>
    );
}

export default Booking;
