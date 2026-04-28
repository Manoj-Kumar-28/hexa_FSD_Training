import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import BuyerNavbar from "./buyerNavbar";
import BuyerFooter from "./buyer-footer";
import '../owner/css/owner-dashboard.css'; // Reusing owner dashboard styles for profile card
import './css/buyer-dashboard.css';

function BuyerProfile() {
    const navigate = useNavigate();
    const [buyer, setBuyer] = useState(null);
    const [error, setError] = useState(null);

    const getOneApi = "http://localhost:8080/api/buyer/get-one";

    useEffect(() => {
        const fetchBuyerProfile = async () => {
            const token = localStorage.getItem('token');
            if (!token) {
                navigate('/login');
                return;
            }

            try {
                const config = {
                    headers: {
                        "Authorization": 'Bearer ' + token
                    }
                };
                const response = await axios.get(getOneApi, config);
                setBuyer(response.data);
            } catch (err) {
                setError("Failed to load profile data");
                navigate('/login');
            }
        };

        fetchBuyerProfile();
    }, [navigate]);



    if (error) {
        return (
            <div className="dashboard-container">
                <BuyerNavbar />
                <div className="container d-flex justify-content-center align-items-center" style={{ minHeight: '60vh' }}>
                    <div className="text-center">
                        <i className="bi bi-exclamation-triangle-fill text-warning" style={{ fontSize: '3rem' }}></i>
                        <p className="mt-3 text-muted">{error}</p>
                    </div>
                </div>
                <BuyerFooter />
            </div>
        );
    }

    return (
        <div className="dashboard-container">
            <BuyerNavbar />

            <div className="container py-5">
                <div className="row justify-content-center">
                    <div className="col-lg-8">
                        {/* Profile Header */}
                        <div className="profile-header-card mb-4" style={{ backgroundColor: '#1e293b', borderRadius: '16px', padding: '2rem' }}>
                            <div className="profile-header-content d-flex align-items-center gap-4">
                                <div className="profile-avatar">
                                    <div className="avatar-circle d-flex align-items-center justify-content-center text-white" style={{ width: '80px', height: '80px', borderRadius: '50%', backgroundColor: '#3b82f6', fontSize: '2rem', fontWeight: 'bold' }}>
                                        {buyer?.name?.charAt(0).toUpperCase()}
                                    </div>
                                </div>
                                <div className="profile-info text-white">
                                    <h2 className="profile-name text-white mb-1">{buyer?.name.toUpperCase()}</h2>
                                    <p className="profile-role text-primary mb-2">Car Renter</p>
                                    <div className="profile-location text-light mt-1" style={{ fontSize: '1.1rem' }}>
                                        <i className="bi bi-geo-alt-fill me-2 text-primary"></i>
                                        {buyer?.city}
                                    </div>
                                </div>
                            </div>
                        </div>

                        {/* Profile Details */}
                        <div className="profile-details-card" style={{ backgroundColor: '#1e293b', borderRadius: '16px', padding: '2rem' }}>
                            <div className="card-header-custom border-bottom border-secondary pb-3 mb-4 text-white">
                                <h4 className="mb-0">
                                    <i className="bi bi-person-circle me-2 text-primary"></i>
                                    Profile Information
                                </h4>
                            </div>
                            <div className="card-body-custom">
                                <div className="row g-4">
                                    <div className="col-md-6">
                                        <div className="info-item">
                                            <label className="info-label text-light d-block mb-2" style={{ fontSize: '0.95rem' }}>Full Name</label>
                                            <div className="info-value text-white fw-bold fs-5">{buyer?.name}</div>
                                        </div>
                                    </div>
                                    <div className="col-md-6">
                                        <div className="info-item">
                                            <label className="info-label text-light d-block mb-2" style={{ fontSize: '0.95rem' }}>Email Address</label>
                                            <div className="info-value text-white fw-bold fs-5">{buyer?.email}</div>
                                        </div>
                                    </div>
                                    <div className="col-md-6">
                                        <div className="info-item">
                                            <label className="info-label text-light d-block mb-2" style={{ fontSize: '0.95rem' }}>City</label>
                                            <div className="info-value text-white fw-bold fs-5">{buyer?.city}</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        {/* Account Status */}
                        <div className="profile-status-card mt-4" style={{ backgroundColor: '#1e293b', borderRadius: '16px', padding: '2rem' }}>
                            <div className="status-content d-flex align-items-center gap-4">
                                <div className="status-icon-wrapper text-success" style={{ fontSize: '3rem' }}>
                                    <i className="bi bi-check-circle-fill"></i>
                                </div>
                                <div className="status-text-wrapper text-white">
                                    <h3 className="status-title text-white mb-1">Account Active</h3>
                                    <p className="status-description text-light mb-0" style={{ fontSize: '1rem' }}>
                                        Your account is in good standing. You can browse and book cars anytime.
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <BuyerFooter />
        </div>
    );
}

export default BuyerProfile;
