import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import TopMenu from "./top-menu";
import AdminSidebar from "./admin-sidebar";
import './css/admin-dashboard.css';

function AdminProfile() {
    const navigate = useNavigate();
    const [admin, setAdmin] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const getOneApi = "http://localhost:8080/api/admin/get-one";

    useEffect(() => {
        const fetchAdminProfile = async () => {
            const token = localStorage.getItem('token');
            if (!token) {
                navigate('/login');
                return;
            }

            try {
                const config = {
                    headers: {
                        "Authorization": "Bearer " + localStorage.getItem("token")
                    }
                };
                const response = await axios.get(getOneApi, config);
                setAdmin(response.data);
                setLoading(false);
            } catch (err) {
                console.error("Error fetching admin profile:", err);
                setError("Failed to load profile data");
                setLoading(false);
                if (err.response?.status === 401) {
                    navigate('/login');
                }
            }
        };

        fetchAdminProfile();
    }, [navigate]);

    if (loading) {
        return (
            <div className="admin-dashboard-container">
                <TopMenu />
                <div className="container-fluid">
                    <div className="row">
                        <div className="col-md-3">
                            <AdminSidebar />
                        </div>
                        <div className="col-lg-9 admin-main-content d-flex justify-content-center align-items-center" style={{ minHeight: '60vh' }}>
                            <div className="text-center">
                                <div className="spinner-border text-primary" role="status">
                                    <span className="visually-hidden">Loading...</span>
                                </div>
                                <p className="mt-3 text-muted">Loading profile...</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    if (error) {
        return (
            <div className="admin-dashboard-container">
                <TopMenu />
                <div className="container-fluid">
                    <div className="row">
                        <div className="col-md-3">
                            <AdminSidebar />
                        </div>
                        <div className="col-lg-9 admin-main-content d-flex justify-content-center align-items-center" style={{ minHeight: '60vh' }}>
                            <div className="text-center">
                                <i className="bi bi-exclamation-triangle-fill text-warning" style={{ fontSize: '3rem' }}></i>
                                <p className="mt-3 text-muted">{error}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    return (
        <div className="admin-dashboard-container">
            <TopMenu />
            <div className="container-fluid">
                <div className="row">
                    <div className="col-md-3">
                        <AdminSidebar />
                    </div>
                    <div className="col-lg-9 admin-main-content">
                        <div className="row justify-content-center">
                            <div className="col-lg-8">
                                {/* Profile Header */}
                                <div className="profile-header-card mb-4">
                                    <div className="profile-header-content">
                                        <div className="profile-avatar">
                                            <div className="avatar-circle">
                                                {admin?.name?.charAt(0).toUpperCase()}
                                            </div>
                                        </div>
                                        <div className="profile-info">
                                            <h2 className="profile-name">{admin?.name}</h2>
                                            <p className="profile-role">Administrator</p>
                                            <div className="profile-location">
                                                <i className="bi bi-shield-check me-2"></i>
                                                Admin Account
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
                                                    <div className="info-value">{admin?.name}</div>
                                                </div>
                                            </div>
                                            <div className="col-md-6">
                                                <div className="info-item">
                                                    <label className="info-label">Email Address</label>
                                                    <div className="info-value">{admin?.email}</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                {/* Account Status */}
                                <div className="profile-status-card">
                                    <div className="status-content">
                                        <div className="status-icon-wrapper">
                                            <i className="bi bi-check-circle-fill"></i>
                                        </div>
                                        <div className="status-text-wrapper">
                                            <h3 className="status-title">Account Active</h3>
                                            <p className="status-description">
                                                Your admin account is active. You can manage users, cars, and view bookings.
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

export default AdminProfile;
