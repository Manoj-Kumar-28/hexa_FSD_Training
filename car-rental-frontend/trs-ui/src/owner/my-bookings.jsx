import axios from "axios"
import { useEffect, useState } from "react"
import Navbar from "./nav-bar"
import Sidebar from "./side-bar"
import './css/owner-dashboard.css'

function Bookings() {
    const [bookings, setBookings] = useState([])

    const getApi = "http://localhost:8080/api/owner/my-bookings"

    useEffect(() => {
        const fetchBookings = async () => {
            const config = {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("token")
                }
            }
            const response = await axios.get(getApi, config)
            setBookings(response.data)
        }
        fetchBookings()
    }, [])

    return (
        <div className="owner-dashboard-container">
            <div className="container-fluid p-0">
                <Navbar />

                <div className="d-flex">
                    <Sidebar />

                    <div className="main-content flex-grow-1">
                        <div className="section-header mb-4">
                            <h2 className="section-title">Manage Bookings</h2>
                        </div>

                        <div className="glass-table-container mt-0">
                            {bookings.length === 0 ? (
                                <div className="text-center py-5">
                                    <i className="bi bi-calendar-x text-white" style={{ fontSize: '3rem', opacity: 0.8 }}></i>
                                    <h4 className="mt-3 text-white">No bookings for now</h4>
                                    <p className="text-white">When customers book your cars, they will appear here.</p>
                                </div>
                            ) : (
                                <table className="premium-table">
                                    <thead>
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col">Customer</th>
                                            <th scope="col">Car Number</th>
                                            <th scope="col">Model</th>
                                            <th scope="col">Status</th>
                                            <th scope="col">Amount</th>
                                            <th scope="col">Drop Date/Time</th>
                                            <th scope="col">Location</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {
                                            bookings.map((book, index) => (
                                                <tr key={index}>
                                                    <td className="text-white opacity-50">{index + 1}</td>
                                                    <td className="fw-bold">{book.firstName}</td>
                                                    <td>{book.carNumber}</td>
                                                    <td>{book.model}</td>
                                                    <td>
                                                        <span className={`status-badge ${book.bookingStatus.toLowerCase()}`}>
                                                            {book.bookingStatus}
                                                        </span>
                                                    </td>
                                                    <td className="text-white">₹{book.totalAmount}</td>
                                                    <td>{book.dropDateTime}</td>
                                                    <td>{book.dropLocation}</td>
                                                </tr>
                                            ))
                                        }
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

export default Bookings