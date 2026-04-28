import { useEffect, useState } from "react"
import TopMenu from "./top-menu"
import { useNavigate, Outlet } from "react-router-dom"
import axios from "axios"
import AdminSidebar from "./admin-sidebar"
import AdminStats from "./admin-stats"
import './css/admin-dashboard.css'

function AdminDashboard() {
    const [admin, setAdmin] = useState(undefined)
    const [pendingCount, setPendingCount] = useState(0)
    const navigate = useNavigate()
    const getOneApi = "http://localhost:8080/api/admin/get-one"
    const pendingApi = "http://localhost:8080/api/admin/owner/pending"
    useEffect(() => {
        const fetchAdmin = async () => {
            const config = {
                headers: {
                    "Authorization": 'Bearer ' + localStorage.getItem('token')
                }
            }
            try {
                const response = await axios.get(getOneApi, config)
                setAdmin(response.data)

                // Fetch pending approvals count
                const pendingResponse = await axios.get(pendingApi, config)
                setPendingCount(pendingResponse.data.length)
            } catch (err) {
                navigate("/login")
            }
        }
        fetchAdmin()
    }, [])



    return (
        <div className="admin-dashboard-container">
            <div className="container-fluid p-0">
                <TopMenu />

                <div className="d-flex">
                    <AdminSidebar />

                    <div className="main-content flex-grow-1">
                        <div className="d-flex justify-content-between align-items-center mb-4 flex-wrap">
                            {admin && (
                                <div className="welcome-banner m-0">
                                    <h2 className="welcome-text">
                                        Welcome back, <span>{admin.name}</span>!
                                    </h2>
                                    <p className="welcome-subtext">Manage users, cars, bookings, and reports from here.</p>
                                </div>
                            )}

                        </div>

                        {pendingCount > 0 && (
                            <div className="alert alert-warning d-flex align-items-center mb-4" role="alert">
                                <i className="bi bi-exclamation-triangle-fill me-2"></i>
                                <div>
                                    <strong>New pending owner requests!</strong> {pendingCount} owner{pendingCount > 1 ? 's' : ''} waiting for approval.
                                    <button
                                        className="btn btn-sm btn-outline-primary ms-3"
                                        onClick={() => navigate("/admin-approvals")}
                                    >
                                        Review Now
                                    </button>
                                </div>
                            </div>
                        )}

                        <div className="row g-4">
                            <div className="col-lg-12">
                                <AdminStats />
                            </div>
                        </div>

                        {/* Outlet for rendering nested views like filtered bookings */}
                        <Outlet />
                    </div>
                </div>

            </div>
        </div>

    )
}
export default AdminDashboard