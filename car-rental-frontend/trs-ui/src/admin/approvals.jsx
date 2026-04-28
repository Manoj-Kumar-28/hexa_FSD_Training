import axios from "axios"
import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import TopMenu from "./top-menu"
import AdminSidebar from "./admin-sidebar"
import './css/admin-dashboard.css'

function Approvals() {
    const [pendingOwners, setPendingOwners] = useState([])
    const navigate = useNavigate()
    const pendingApi = "http://localhost:8080/api/admin/owner/pending"

    const fetchPendingOwners = async () => {
        const config = {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        }
        try {
            const response = await axios.get(pendingApi, config)
            setPendingOwners(response.data)
        } catch (err) {
            console.error("Error fetching pending owners:", err)
        }
    }

    useEffect(() => {
        fetchPendingOwners()
    }, [])

    const handleApproval = async (ownerId, action) => {
        const config = {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        }
        const status = action === 'approve' ? 'APPROVED' : 'REJECTED'
        try {
            await axios.put(`http://localhost:8080/api/admin/owner/${ownerId}/status?status=${status}`, null, config)
            fetchPendingOwners()
        } catch (err) {
            console.error(`Error ${action}ing owner:`, err)
            alert(`Failed to ${action} owner. Please try again.`)
        }
    }

    return (
        <div className="admin-dashboard-container">
            <div className="container-fluid p-0">
                <TopMenu />

                <div className="d-flex">
                    <AdminSidebar />

                    <div className="main-content flex-grow-1 p-4">
                        <div className="glass-table-container">
                            <div className="d-flex justify-content-between align-items-center mb-4">
                                <h3 className="text-light mb-0">Pending Owner Approvals</h3>
                                <span className="badge bg-warning text-dark fs-6">
                                    {pendingOwners.length} pending
                                </span>
                            </div>

                            {pendingOwners.length === 0 ? (
                                <div className="text-center py-5">
                                    <i className="bi bi-check-circle-fill text-success" style={{ fontSize: "3rem" }}></i>
                                    <h4 className="text-light mt-3">No Pending Approvals</h4>
                                    <p className="text-muted">All owner registrations have been processed.</p>
                                </div>
                            ) : (
                                <table className="premium-table">
                                    <thead>
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col">Name</th>
                                            <th scope="col">Email</th>
                                            <th scope="col">City</th>
                                            <th scope="col">Status</th>
                                            <th scope="col">Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {pendingOwners.map((owner, index) => (
                                            <tr key={owner.id || index}>
                                                <td className="fw-bold text-white">{owner.id}</td>
                                                <td>{owner.name}</td>
                                                <td>{owner.email}</td>
                                                <td>{owner.city}</td>
                                                <td>
                                                    <span className="badge bg-warning text-dark">
                                                        {owner.status}
                                                    </span>
                                                </td>
                                                <td>
                                                    <div className="d-flex gap-2">
                                                        <button
                                                            className="btn btn-success btn-sm"
                                                            onClick={() => handleApproval(owner.id, 'approve')}
                                                            title="Approve owner"
                                                        >
                                                            <i className="bi bi-check-circle"></i> Approve
                                                        </button>
                                                        <button
                                                            className="btn btn-danger btn-sm"
                                                            onClick={() => handleApproval(owner.id, 'reject')}
                                                            title="Reject owner"
                                                        >
                                                            <i className="bi bi-x-circle"></i> Reject
                                                        </button>
                                                    </div>
                                                </td>
                                            </tr>
                                        ))}
                                    </tbody>
                                </table>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Approvals