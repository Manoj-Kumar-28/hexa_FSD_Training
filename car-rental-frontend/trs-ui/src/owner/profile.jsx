import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import OwnerNavbar from "./nav-bar";
import Sidebar from "./side-bar";
import './css/owner-dashboard.css';

function OwnerProfile() {
    const navigate = useNavigate();
    const [owner, setOwner] = useState(null);
    const [error, setError] = useState(null);

    const getOneApi = "http://localhost:8080/api/owner/get-one";

    useEffect(() => {
        const fetchOwnerProfile = async () => {
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
                setOwner(response.data);
            } catch (err) {
                setError("Failed to load profile data");
                if (err.response?.status === 401) {
                    navigate('/login');
                }
            }
        };

        fetchOwnerProfile();
    }, [navigate]);



    if (error) {
        return (
            <div className="owner-dashboard-container">
                <OwnerNavbar />
                <div className="d-flex">
                    <Sidebar />
                    <div className="main-content flex-grow-1 d-flex justify-content-center align-items-center" style={{ minHeight: '60vh' }}>
                        <div className="text-center">
                            <i className="bi bi-exclamation-triangle-fill text-warning" style={{ fontSize: '3rem' }}></i>
                            <p className="mt-3 text-muted">{error}</p>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    return (
        <div className="owner-dashboard-container">
            <OwnerNavbar />
            <div className="d-flex">
                <Sidebar />
                <div className="main-content flex-grow-1">
                    <div className="container-fluid p-4">
                        <div className="row justify-content-center">
                            <div className="col-lg-8">
                                {/* Profile Header */}
                                <div className="profile-header-card mb-4">
                                    <div className="profile-header-content">
                                        <div className="profile-avatar">
                                            <div className="avatar-circle">
                                                {owner?.name?.charAt(0).toUpperCase()}
                                            </div>
                                        </div>
                                        <div className="profile-info">
                                            <h2 className="profile-name">{owner?.name}</h2>
                                            <p className="profile-role">Car Owner</p>
                                            <div className="profile-location">
                                                <i className="bi bi-geo-alt-fill me-2"></i>
                                                {owner?.city}
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                {/* Profile Details */}
                                <div className="profile-details-card">
                                    <div className="card-header-custom">
                                        <h4 className="mb-0">
                                            <i className="bi bi-person-circle me-2"></i>
                                            Profile Information
                                        </h4>
                                    </div>
                                    <div className="card-body-custom">
                                        <div className="row g-4">
                                            <div className="col-md-6">
                                                <div className="info-item">
                                                    <label className="info-label">Full Name</label>
                                                    <div className="info-value">{owner?.name}</div>
                                                </div>
                                            </div>
                                            <div className="col-md-6">
                                                <div className="info-item">
                                                    <label className="info-label">Email Address</label>
                                                    <div className="info-value">{owner?.email}</div>
                                                </div>
                                            </div>
                                            <div className="col-md-6">
                                                <div className="info-item">
                                                    <label className="info-label">City</label>
                                                    <div className="info-value">{owner?.city}</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                {/* Account Status */}
                                <div className="profile-status-card mt-4">
                                    <div className="status-content">
                                        <div className="status-icon-wrapper">
                                            <i className="bi bi-check-circle-fill"></i>
                                        </div>
                                        <div className="status-text-wrapper">
                                            <h3 className="status-title">Account Active</h3>
                                            <p className="status-description">
                                                Your account is in good standing. You can manage your cars and view bookings.
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default OwnerProfile;