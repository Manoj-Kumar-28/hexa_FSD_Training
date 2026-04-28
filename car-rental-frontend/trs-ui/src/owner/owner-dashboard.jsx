import { useEffect, useState } from "react"
import Navbar from "./nav-bar"
import { Outlet, useNavigate } from "react-router-dom"
import axios from "axios"
import Stats from "./stats"
import Sidebar from "./side-bar"
import './css/owner-dashboard.css'
import '../buyer/css/buyer-dashboard.css' // Reuse banner styles


function OwnerDashboard() {

    const [owner, setOwner] = useState(undefined)
    const navigate = useNavigate()
    const getOneApi = "http://localhost:8080/api/owner/get-one"
    useEffect(() => {
        const fetchOwner = async () => {
            const config = {
                headers: {
                    "Authorization": 'Bearer ' + localStorage.getItem('token')
                }
            }
            try {
                const response = await axios.get(getOneApi, config)
                setOwner(response.data)
            } catch (err) {
                navigate("/login")
            }
        }
        fetchOwner()
    }, [])

    return (
        <div className="owner-dashboard-container">
            <div className="container-fluid p-0">
                <Navbar />
                
                <div className="d-flex">
                    <Sidebar />
                    
                    <div className="main-content flex-grow-1">
                        {owner?.status === 'PENDING' ? (
                            <div className="pending-approval-card">
                                <div className="pending-approval-icon">
                                    <i className="bi bi-hourglass-split"></i>
                                </div>
                                <h2>Waiting for admin approval</h2>
                                <p>Your owner account is currently pending approval. You will see the dashboard once your account is approved.</p>
                            </div>
                        ) : (
                            <>
                                <div className="d-flex justify-content-between align-items-center mb-4">
                                    {owner && (
                                        <div className="welcome-banner m-0">
                                            <h2 className="welcome-text">
                                                Welcome back, <span>{owner.name}</span>!
                                            </h2>
                                            <p className="welcome-subtext">Here's what's happening with your fleet today.</p>
                                        </div>
                                    )}
                                    
                                    <div
                                        className="action-card header-action-card"
                                        onClick={() => navigate("/owner/add-car")}
                                    >
                                        <i className="bi bi-plus-circle"></i>
                                        <h5>Add New Vehicle</h5>
                                    </div>
                                </div>
                                
                                <div className="row g-4">
                                    <div className="col-lg-12">
                                        <Stats />
                                    </div>
                                    
                                    <div className="col-lg-12">
                                        <Outlet />
                                    </div>
                                </div>
                            </>
                        )}
                    </div>
                </div>
            </div>
        </div>
    )
}
export default OwnerDashboard