import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import BuyerNavbar from './buyerNavbar';
import BuyerFooter from './buyer-footer';
import './css/buyer-dashboard.css';
import './css/car-details.css';

function CarDetails() {
    const { id } = useParams();
    const navigate = useNavigate();
    const getApi = `http://localhost:8080/api/owner/get/${id}`
    const [car, setCar] = useState({})

    const currentAmenities = ["Air Conditioning", "Music System", "Bluetooth", "GPS",
        "Reverse Camera", "Spare Tyre", "Tool Kit", "2 Front Airbags", "Power Steering"
    ];


    useEffect(() => {

        const fetchDetails = async () => {
            const config = {
                headers: {
                    "Authorization": `Bearer ` + localStorage.getItem("token")
                }
            }
            const response = await axios.get(getApi, config)
            setCar(response.data)
            console.log(response.data)
        }
        fetchDetails()

    }, [id])


    return (
        <div className="dashboard-container">
            <BuyerNavbar />

            <div className="car-details-wrapper">
                <button className="back-button" onClick={() => navigate(-1)}>
                    &larr; Back to Browse
                </button>

                {
                    <div className="car-details-card">
                        <div className="car-image-section">
                            <div className="image-container-inner">
                                <img
                                    src={
                                        car.carImage
                                            ? `/uploads/${car.carImage}`
                                            : "/car_card_placeholder.png"
                                    }
                                    alt={`${car.brand} ${car.model}`}
                                    className="main-car-image"
                                />
                                <div className="car-status-badge">
                                    {car.carStatus === 'AVAILABLE' ? 'Available Now' : 'Currently Rented'}
                                </div>
                            </div>

                            <div className="amenities-section mt-4">
                                <h3>Amenities</h3>
                                <div className="amenities-grid">
                                    {currentAmenities.map((item, index) => (
                                        <div key={index} className="amenity-tag">
                                            <i className="bi bi-check2-circle"></i>
                                            <span>{item}</span>
                                        </div>
                                    ))}
                                </div>
                            </div>

                            <div className="policies-section mt-4">
                                <h3>Rental Policies</h3>
                                <div className="policy-item">
                                    <i className="bi bi-fuel-pump-fill"></i>
                                    <span><strong>Fuel Policy:</strong> Full to Full</span>
                                </div>
                                <div className="policy-item">
                                    <i className="bi bi-speedometer2"></i>
                                    <span><strong>Mileage Limit:</strong> Unlimited Kilometers</span>
                                </div>
                            </div>
                        </div>

                        <div className="car-info-section">
                            <div className="car-header">
                                <h1 className="car-title">{car.brand}
                                    <span className="car-model"> {car.model}
                                    </span>
                                </h1>
                                <p className="car-location">
                                    <i className="location-icon">📍</i>
                                    {car.location}
                                </p>
                            </div>

                            <div className="car-price-box">
                                <span className="price-amount">
                                    <i className="bi bi-currency-rupee"></i>
                                    {car.pricePerDay}
                                </span>
                                <span className="price-unit">/ day</span>
                            </div>

                            <div className="car-specs-grid">
                                <div className="spec-item">
                                    <span className="spec-label">Fuel Type</span>
                                    <span className="spec-value">{car.fuelType}</span>
                                </div>
                                <div className="spec-item">
                                    <span className="spec-label">Seats</span>
                                    <span className="spec-value">{car.seats}</span>
                                </div>
                                <div className="spec-item">
                                    <span className="spec-label">Color</span>
                                    <span className="spec-value">{car.color}</span>
                                </div>
                                <div className="spec-item">
                                    <span className="spec-label">Car Number</span>
                                    <span className="spec-value">{car.carNumber}</span>
                                </div>
                                <div className="spec-item">
                                    <span className="spec-label">Transmission</span>
                                    <span className="spec-value">{'Manual'}</span>
                                </div>
                            </div>

                            <div className="owner-info">
                                <h3>Owner Information</h3>
                                <div className="owner-badge-container">
                                    <p><strong>Name:</strong> {car.ownerName}</p>
                                    <span className="verified-badge"><i className="bi bi-patch-check-fill"></i> Verified Owner</span>
                                </div>

                            </div>

                            <div className="action-buttons">
                                <button
                                    className="book-now-btn"
                                    disabled={car.carStatus !== 'AVAILABLE'}
                                    onClick={() => navigate(`/booking/${car.id}`, { state: { car } })}
                                >
                                    {car.carStatus === 'AVAILABLE' ? 'Proceed to Booking' : 'Not Available'}
                                </button>
                            </div>
                        </div>
                    </div>
                }
            </div>

            <BuyerFooter />
        </div>
    );
}

export default CarDetails;
